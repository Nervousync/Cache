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
package org.nervousync.cache.config;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.*;

import org.nervousync.beans.core.BeanObject;
import org.nervousync.cache.commons.CacheGlobals;
import org.nervousync.commons.core.Globals;

/**
 * Cache server configure
 *
 * @author Steven Wee	<a href="mailto:wmkm0113@Hotmail.com">wmkm0113@Hotmail.com</a>
 * @version $Revision : 1.0 $ $Date: Apr 25, 2017 3:09:14 PM $
 */
@XmlType(name = "cache_config", namespace = "https://cache.nervousync.org/configure")
@XmlRootElement(name = "cache_config", namespace = "https://cache.nervousync.org/configure")
@XmlAccessorType(XmlAccessType.NONE)
public final class CacheConfig extends BeanObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6700233652090948759L;

	/**
	 * <span class="en">Cache provider name</span>
	 * <span class="zhs">缓存适配器名称</span>
	 */
	@XmlElement(name = "provider_name")
	private String providerName                    = Globals.DEFAULT_VALUE_STRING;
	/**
	 * <span class="en">Secure name</span>
	 * <span class="zhs">安全配置名称</span>
	 */
	@XmlElement(name = "secure_name")
	private String secureName					   = Globals.DEFAULT_VALUE_STRING;
	/**
	 * <span class="en">Connect timeout</span>
	 * <span class="zhs">连接超时时间</span>
	 */
	@XmlElement(name = "connect_timeout")
	private int connectTimeout						= CacheGlobals.DEFAULT_CONNECTION_TIMEOUT;
	/**
	 * <span class="en">Connect retry count</span>
	 * <span class="zhs">连接超时重试次数</span>
	 */
	@XmlElement(name = "retry_count")
	private int retryCount                          = CacheGlobals.DEFAULT_RETRY_COUNT;
	/**
	 * <span class="en">Default expire time</span>
	 * <span class="zhs">默认过期时间</span>
	 */
	@XmlElement(name = "expire_time")
	private int expireTime							= CacheGlobals.DEFAULT_EXPIRE_TIME;
	/**
	 * <span class="en">Client pool size</span>
	 * <span class="zhs">连接池大小</span>
	 */
	@XmlElement(name = "client_pool_size")
	private int clientPoolSize						= CacheGlobals.DEFAULT_CLIENT_POOL_SIZE;
	/**
	 * <span class="en">Limit size of generated client instance</span>
	 * <span class="zhs">客户端实例阈值</span>
	 */
	@XmlElement(name = "maximum_client")
	private int maximumClient						= CacheGlobals.DEFAULT_MAXIMUM_CLIENT;
	/**
	 * Authenticate username
	 */
	@XmlElement(name = "username")
	private String userName = Globals.DEFAULT_VALUE_STRING;
	/**
	 * Authenticate password
	 */
	@XmlElement(name = "password")
	private String passWord = Globals.DEFAULT_VALUE_STRING;
	/**
	 * <span class="en">Cache server list</span>
	 * <span class="en">缓存服务器列表</span>
	 */
	@XmlElementWrapper(name = "server_config_list")
	@XmlElement(name = "server_config")
	private List<CacheServer> serverConfigList;

	/**
	 * Instantiates a new Cache config.
	 */
	public CacheConfig() {
		this.serverConfigList = new ArrayList<>();
	}

	/**
	 * <h3 class="en">Retrieve cache provider name</h3>
	 * <h3 class="zhs">读取缓存适配器名称</h3>
	 *
	 * @return  <span class="en">Cache provider name</span>
	 *          <span class="zhs">缓存适配器名称</span>
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * <h3 class="en">Configure cache provider</h3>
	 * <h3 class="zhs">设置缓存适配器</h3>
	 *
	 * @param providerName  <span class="en">Cache provider name</span>
	 *                      <span class="zhs">缓存适配器名称</span>
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * <h3 class="en">Retrieve secure name</h3>
	 * <h3 class="zhs">读取用于保护密码的安全配置名称</h3>
	 *
	 * @return  <span class="en">Secure name</span>
	 *          <span class="zhs">安全配置名称</span>
	 */
	public String getSecureName() {
		return secureName;
	}

	/**
	 * <h3 class="en">Configure secure name for protect password</h3>
	 * <h3 class="zhs">设置用于保护密码的安全配置名称</h3>
	 *
	 * @param secureName    <span class="en">Secure name</span>
	 *                      <span class="zhs">安全配置名称</span>
	 */
	public void setSecureName(String secureName) {
		this.secureName = secureName;
	}

	/**
	 * <h3 class="en">Retrieve cache server authorization username</h3>
	 * <h3 class="zhs">获取缓存服务器的用户名</h3>
	 *
	 * @return  <span class="en">Cache server username</span>
	 *          <span class="zhs">缓存服务器用户名</span>
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * <h3 class="en">Configure cache server authorization username</h3>
	 * <h3 class="zhs">设置缓存服务器的用户名</h3>
	 *
	 * @param userName  <span class="en">Cache server username</span>
	 *                  <span class="zhs">缓存服务器用户名</span>
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * <h3 class="en">Retrieve cache server authorization password</h3>
	 * <h3 class="zhs">获取缓存服务器的密码</h3>
	 *
	 * @return  <span class="en">Cache server password</span>
	 *          <span class="zhs">缓存服务器密码</span>
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * <h3 class="en">Configure cache server authorization password</h3>
	 * <h3 class="zhs">设置缓存服务器的密码</h3>
	 *
	 * @param passWord  <span class="en">Cache server password</span>
	 *                  <span class="zhs">缓存服务器密码</span>
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * <h3 class="en">Retrieve cache server config list</h3>
	 * <h3 class="zhs">读取缓存服务器列表</h3>
	 *
	 * @return  <span class="en">Cache server list</span>
	 *          <span class="en">缓存服务器列表</span>
	 */
	public List<CacheServer> getServerConfigList() {
		return serverConfigList;
	}

	/**
	 * <h3 class="en">Configure cache server config list</h3>
	 * <h3 class="zhs">设置缓存服务器列表</h3>
	 *
	 * @param serverConfigList  <span class="en">Cache server list</span>
	 *                          <span class="zhs">缓存服务器列表</span>
	 */
	public void setServerConfigList(List<CacheServer> serverConfigList) {
		this.serverConfigList = serverConfigList;
	}

	/**
	 * <h3 class="en">Retrieve server connect timeout</h3>
	 * <h3 class="zhs">读取缓存服务器的连接超时时间</h3>
	 *
	 * @return  <span class="en">Connect timeout</span>
	 *          <span class="zhs">连接超时时间</span>
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * <h3 class="en">Configure server connect timeout</h3>
	 * <h3 class="zhs">设置缓存服务器的连接超时时间</h3>
	 *
	 * @param connectTimeout     <span class="en">Connect timeout</span>
	 *                           <span class="zhs">连接超时时间</span>
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * <h3 class="en">Retrieve server connect retry count</h3>
	 * <h3 class="zhs">读取缓存服务器的连接超时重试次数</h3>
	 *
	 * @return  <span class="en">Connect retry count</span>
	 *          <span class="zhs">连接超时重试次数</span>
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * <h3 class="en">Configure server connect retry count</h3>
	 * <h3 class="zhs">设置缓存服务器的连接超时时间</h3>
	 *
	 * @param retryCount     <span class="en">Connect retry count</span>
	 *                       <span class="zhs">连接超时重试次数</span>
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * <h3 class="en">Retrieve default expire time</h3>
	 * <h3 class="zhs">读取缓存的默认过期时间</h3>
	 *
	 * @return  <span class="en">Default expire time</span>
	 *          <span class="zhs">默认过期时间</span>
	 */
	public int getExpireTime() {
		return expireTime;
	}

	/**
	 * <h3 class="en">Configure default expire time, setting -1 for never expire</h3>
	 * <h3 class="zhs">设置缓存的默认过期时间，设置为-1则永不过期</h3>
	 *
	 * @param expireTime    <span class="en">Default expire time</span>
	 *                      <span class="zhs">默认过期时间</span>
	 */
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * <h3 class="en">Retrieve connect client pool size</h3>
	 * <h3 class="zhs">读取客户端连接池的大小</h3>
	 *
	 * @return  <span class="en">Client pool size</span>
	 *          <span class="zhs">连接池大小</span>
	 */
	public int getClientPoolSize() {
		return clientPoolSize;
	}

	/**
	 * <h3 class="en">Configure connect client pool size</h3>
	 * <h3 class="zhs">设置客户端连接池的大小</h3>
	 *
	 * @param clientPoolSize    <span class="en">Client pool size</span>
	 *                          <span class="zhs">连接池大小</span>
	 */
	public void setClientPoolSize(int clientPoolSize) {
		this.clientPoolSize = clientPoolSize;
	}

	/**
	 * <h3 class="en">Retrieve limit size of generated client instance</h3>
	 * <h3 class="zhs">读取允许创建的客户端实例阈值</h3>
	 *
	 * @return  <span class="en">Limit size of generated client instance</span>
	 *          <span class="zhs">客户端实例阈值</span>
	 */
	public int getMaximumClient() {
		return maximumClient;
	}

	/**
	 * <h3 class="en">Configure limit size of generated client instance</h3>
	 * <h3 class="zhs">设置允许创建的客户端实例阈值</h3>
	 *
	 * @param maximumClient     <span class="en">Limit size of generated client instance</span>
	 *                          <span class="zhs">客户端实例阈值</span>
	 */
	public void setMaximumClient(int maximumClient) {
		this.maximumClient = maximumClient;
	}

	@XmlType(name = "cache_server")
	@XmlRootElement(name = "cache_server")
	@XmlAccessorType(XmlAccessType.NONE)
	public static final class CacheServer extends BeanObject {

		/**
		 *
		 */
		private static final long serialVersionUID = -9179968915973853412L;

		/**
		 * Server address
		 */
		@XmlElement(name = "server_address")
		private String serverAddress;
		/**
		 * Server port number
		 */
		@XmlElement(name = "server_port")
		private int serverPort;
		/**
		 * Server weight
		 */
		@XmlElement(name = "server_weight")
		private int serverWeight;
		/**
		 * Is read only status
		 */
		@XmlElement(name = "read_only")
		private boolean readOnly = Boolean.FALSE;

		/**
		 * <h3 class="en">Default constructor</h3>
		 * <h3 class="zhs">默认构造方法</h3>
		 */
		public CacheServer() {
			this.serverAddress = Globals.DEFAULT_VALUE_STRING;
			this.serverPort = Globals.DEFAULT_VALUE_INT;
			this.serverWeight = CacheGlobals.DEFAULT_CACHE_SERVER_WEIGHT;
		}

		/**
		 * <h3 class="en">Match given server address/port is same as current config information</h3>
		 * <h3 class="zhs">比对指定的服务器地址/端口是否与当前配置信息一致</h3>
		 *
		 * @param serverAddress     <span class="en">Cache server address</span>
		 *                          <span class="zhs">缓存服务器地址</span>
		 * @param serverPort        <span class="en">Cache server port</span>
		 *                          <span class="zhs">缓存服务器端口号</span>
		 * @return  <span class="en">Match result</span>
		 *          <span class="en">比对结果</span>
		 */
		public boolean match(String serverAddress, int serverPort) {
			return (this.serverAddress.equalsIgnoreCase(serverAddress) && this.serverPort == serverPort);
		}

		/**
		 * <h3 class="en">Retrieve cache server address</h3>
		 * <h3 class="zhs">读取缓存服务器地址</h3>
		 *
		 * @return  <span class="en">Cache server address</span>
		 *          <span class="zhs">缓存服务器地址</span>
		 */
		public String getServerAddress() {
			return serverAddress;
		}

		/**
		 * <h3 class="en">Configure cache server address</h3>
		 * <h3 class="zhs">设置缓存服务器地址</h3>
		 *
		 * @param serverAddress     <span class="en">Cache server address</span>
		 *                          <span class="zhs">缓存服务器地址</span>
		 */
		public void setServerAddress(String serverAddress) {
			this.serverAddress = serverAddress;
		}

		/**
		 * <h3 class="en">Retrieve cache server port</h3>
		 * <h3 class="zhs">读取缓存服务器端口号</h3>
		 *
		 * @return  <span class="en">Cache server port</span>
		 *          <span class="zhs">缓存服务器端口号</span>
		 */
		public int getServerPort() {
			return serverPort;
		}

		/**
		 * <h3 class="en">Configure cache server port</h3>
		 * <h3 class="zhs">设置缓存服务器端口</h3>
		 *
		 * @param serverPort        <span class="en">Cache server port</span>
		 *                          <span class="zhs">缓存服务器端口号</span>
		 */
		public void setServerPort(int serverPort) {
			this.serverPort = serverPort;
		}

		/**
		 * <h3 class="en">Retrieve cache server weight</h3>
		 * <h3 class="zhs">获取缓存服务器权重值</h3>
		 *
		 * @return  <span class="en">Cache server weight</span>
		 *          <span class="zhs">缓存服务器权重值</span>
		 */
		public int getServerWeight() {
			return serverWeight;
		}

		/**
		 * <h3 class="en">Configure cache server weight</h3>
		 * <h3 class="zhs">设置缓存服务器权重值</h3>
		 *
		 * @param serverWeight  <span class="en">Cache server weight</span>
		 *                      <span class="zhs">缓存服务器权重值</span>
		 */
		public void setServerWeight(int serverWeight) {
			this.serverWeight = serverWeight;
		}

		/**
		 * <h3 class="en">Retrieve cache server read only status</h3>
		 * <h3 class="zhs">获取缓存服务器只读状态</h3>
		 *
		 * @return  <span class="en">Cache server read only status</span>
		 *          <span class="zhs">缓存服务器只读状态</span>
		 */
		public boolean isReadOnly() {
			return readOnly;
		}

		/**
		 * <h3 class="en">Configure cache server read only status</h3>
		 * <h3 class="zhs">设置缓存服务器只读状态</h3>
		 *
		 * @param readOnly  <span class="en">Cache server read only status</span>
		 *                  <span class="zhs">缓存服务器只读状态</span>
		 */
		public void setReadOnly(boolean readOnly) {
			this.readOnly = readOnly;
		}
	}
}
