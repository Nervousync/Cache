/*
 * Licensed to the Nervousync Studio (NSYC) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nervousync.cache.provider.impl.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.nervousync.cache.annotation.CacheProvider;
import org.nervousync.cache.config.CacheServer;
import org.nervousync.cache.exceptions.CacheException;
import org.nervousync.cache.provider.impl.AbstractProvider;
import org.nervousync.commons.core.Globals;
import org.nervousync.utils.ConvertUtils;
import org.nervousync.utils.StringUtils;
import redis.clients.jedis.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Cache provider implement by Redis using Jedis
 *
 * @author Steven Wee	<a href="mailto:wmkm0113@Hotmail.com">wmkm0113@Hotmail.com</a>
 * @version $Revision : 1.0 $ $Date: Apr 25, 2017 4:36:52 PM $
 */
@CacheProvider(name = "JedisProvider", defaultPort = 6379)
public final class JedisProviderImpl extends AbstractProvider {

	/**
	 * Is single server mode
	 */
	private boolean singleMode = Boolean.FALSE;
	/**
	 * Jedis pool object
	 */
	private JedisPool jedisPool = null;
	/**
	 * Write jedis cluster
	 */
	private JedisCluster writeCluster = null;
	/**
	 * Read jedis cluster
	 */
	private JedisCluster readCluster = null;

	/**
	 * Instantiates a new Jedis provider.
	 *
	 * @throws CacheException the cache exception
	 */
	public JedisProviderImpl() throws CacheException {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#initializeConnection(java.util.List)
	 */
	@Override
	protected void initializeConnection(final List<CacheServer> serverConfigList,
	                                    final String userName, final String passWord) {
		if (serverConfigList.isEmpty()) {
			return;
		}
		if (serverConfigList.size() == 1) {
			GenericObjectPoolConfig<Jedis> jedisPoolConfig = new GenericObjectPoolConfig<>();

			jedisPoolConfig.setMaxTotal(this.getMaximumClient());
			jedisPoolConfig.setMaxIdle(this.getClientPoolSize());
			jedisPoolConfig.setMaxWait(Duration.ofMillis(this.getConnectTimeout() * 1000L));
			jedisPoolConfig.setTestOnBorrow(Boolean.TRUE);
			jedisPoolConfig.setTestWhileIdle(Boolean.TRUE);

			CacheServer cachedServer = serverConfigList.get(0);

			int serverPort = cachedServer.getServerPort();
			if (serverPort == Globals.DEFAULT_VALUE_INT) {
				serverPort = this.getDefaultPort();
			}

			if (StringUtils.isEmpty(passWord)) {
				this.jedisPool = new JedisPool(jedisPoolConfig, cachedServer.getServerAddress(), serverPort,
						this.getConnectTimeout() * 1000);
			} else {
				if (StringUtils.isEmpty(userName)) {
					this.jedisPool = new JedisPool(jedisPoolConfig, cachedServer.getServerAddress(), serverPort,
							this.getConnectTimeout() * 1000, passWord);
				} else {
					this.jedisPool = new JedisPool(jedisPoolConfig, cachedServer.getServerAddress(), serverPort,
							this.getConnectTimeout() * 1000, userName, passWord);
				}
			}
			this.singleMode = true;
		} else {
			GenericObjectPoolConfig<Connection> jedisPoolConfig = new GenericObjectPoolConfig<>();

			jedisPoolConfig.setMaxTotal(this.getMaximumClient());
			jedisPoolConfig.setMaxIdle(this.getClientPoolSize());
			jedisPoolConfig.setMaxWait(Duration.ofMillis(this.getConnectTimeout() * 1000L));
			jedisPoolConfig.setTestOnBorrow(Boolean.TRUE);
			jedisPoolConfig.setTestWhileIdle(Boolean.TRUE);

			Set<HostAndPort> readServers = serverConfigList
					.stream()
					.filter(CacheServer::isReadOnly)
					.map(serverConfig ->
							new HostAndPort(serverConfig.getServerAddress(), serverConfig.getServerPort()))
					.collect(Collectors.toSet());
			Set<HostAndPort> writeServers = serverConfigList
					.stream()
					.map(serverConfig ->
							new HostAndPort(serverConfig.getServerAddress(), serverConfig.getServerPort()))
					.collect(Collectors.toSet());

			if (StringUtils.notBlank(passWord)) {
				JedisClientConfig jedisClientConfig;
				if (StringUtils.isEmpty(userName)) {
					jedisClientConfig =
							DefaultJedisClientConfig.builder().clientName(userName).password(passWord).build();
				} else {
					jedisClientConfig =
							DefaultJedisClientConfig.builder().password(passWord).build();
				}
				this.readCluster = new JedisCluster(readServers, jedisClientConfig, 1, jedisPoolConfig);
				this.writeCluster = new JedisCluster(writeServers, jedisClientConfig, 1, jedisPoolConfig);
			} else {
				this.readCluster = new JedisCluster(readServers, jedisPoolConfig);
				this.writeCluster = new JedisCluster(writeServers, jedisPoolConfig);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#set(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void set(String key, String value, int expiry) {
		this.process(key, value, expiry);
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#add(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void add(String key, String value, int expiry) {
		this.process(key, value, expiry);
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#replace(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void replace(String key, String value, int expiry) {
		this.process(key, value, expiry);
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#expire(java.lang.String, int)
	 */
	@Override
	public void expire(String key, int expire) {
		if (this.singleMode) {
			Jedis jedis = this.singleClient();
			jedis.expire(key, expire);
			jedis.close();
		} else {
			this.writeCluster.expire(key, expire);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#touch(java.lang.String)
	 */
	@Override
	public void touch(String... keys) {
		if (this.singleMode) {
			Jedis jedis = this.singleClient();
			jedis.touch(keys);
			jedis.close();
		} else {
			this.writeCluster.touch(keys);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#delete(java.lang.String)
	 */
	@Override
	public void delete(String key) {
		if (this.singleMode) {
			Jedis jedis = this.singleClient();
			jedis.del(key);
			jedis.close();
		} else {
			this.writeCluster.del(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#get(java.lang.String)
	 */
	@Override
	public String get(String key) {
		byte[] objectData;
		if (this.singleMode) {
			Jedis jedis = this.singleClient();
			objectData = jedis.get(key.getBytes());
			jedis.close();
		} else {
			objectData = this.readCluster.get(key.getBytes());
		}
		return objectData == null ? Globals.DEFAULT_VALUE_STRING : ConvertUtils.convertToString(objectData);
	}

	@Override
	public long incr(String key, long step) {
		long result;
		if (this.singleMode) {
			Jedis jedis = this.singleClient();
			result = jedis.incrBy(key, step);
			jedis.close();
		} else {
			result = this.readCluster.incrBy(key, step);
		}
		return result;
	}

	@Override
	public long decr(String key, long step) {
		long result;
		if (this.singleMode) {
			Jedis jedis = this.singleClient();
			result = jedis.decrBy(key, step);
			jedis.close();
		} else {
			result = this.readCluster.decrBy(key, step);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nervousync.cache.provider.CacheProvider#destroy()
	 */
	@Override
	public void destroy() {
		if (this.jedisPool != null && !this.jedisPool.isClosed()) {
			this.jedisPool.close();
		}
		
		if (this.readCluster != null) {
			this.readCluster.close();
		}
		
		if (this.writeCluster != null) {
			this.writeCluster.close();
		}
	}
	
	private Jedis singleClient() {
		return this.jedisPool.getResource();
	}

	private void process(String key, String value, int expiry) {
		if (this.singleMode) {
			Jedis jedis = this.singleClient();
			jedis.setex(key.getBytes(), expiry, ConvertUtils.convertToByteArray(value));
			jedis.close();
		} else {
			this.writeCluster.setex(key.getBytes(), expiry, ConvertUtils.convertToByteArray(value));
		}
	}
}
