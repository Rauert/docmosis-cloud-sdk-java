/*
 *   Copyright 2019 Docmosis.com or its affiliates.  All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *   or in the LICENSE file accompanying this file.
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.docmosis.sdk.environment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Stores settings for communicating with Docmosis End Points.
 * 
 * Typically this class is used to set the default environment:
 * 
 * <pre>
 *   Environment.setDefaults(Endpoint.DWS_VERSION_2_USA, ACCESS_KEY);
 * </pre>
 * 
 * To build a custom environment use the EnvironmentBuilder class.
 *
 */
public class Environment {
	
	public static final Endpoint DEFAULT_ENDPOINT = Endpoint.DWS_VERSION_2_USA; 
	public static final int      DEFAULT_MAX_TRIES = 3;
	public static final long     DEFAULT_RETRY_DELAY = 1000L;
	public static final long     DEFAULT_CONNECT_TIMEOUT = -1L;
	public static final long     DEFAULT_READ_TIMEOUT = -1L;

	protected String accessKey;
	protected String baseUrl = DEFAULT_ENDPOINT.getBaseUrl();
	protected int maxTries = DEFAULT_MAX_TRIES;
	protected long retryDelayMS = DEFAULT_RETRY_DELAY;
	protected Proxy proxy;
	protected long connectTimeoutMS = DEFAULT_CONNECT_TIMEOUT;
	protected long readTimeoutMS = DEFAULT_READ_TIMEOUT;
	protected String sdkVersion = getVersion();
	protected String osVersion = getOSVersion();

	private static Environment DEFAULT_ENVIRONMENT = null;

	
	protected Environment() {
	}
	
	public Environment(Environment other)
	{
		if (other != null) {
			this.accessKey = other.accessKey;
			this.baseUrl = other.baseUrl;
			this.maxTries = other.maxTries;
			this.retryDelayMS = other.retryDelayMS;
			this.proxy = other.proxy;
			this.connectTimeoutMS = other.connectTimeoutMS;
			this.readTimeoutMS = other.connectTimeoutMS;
		}
	}
	
	/**
	 * Create a new Environment using the given access key and defaults
	 * for everything else.
	 *  
	 * @param accessKey your access key for the service
	 */
	public Environment(String accessKey)
	{
		this.accessKey = accessKey;
	}
	
	/**
	 * Create a new Environment instance to access the specific end point url with
	 * the given access key.
	 * 
	 * @param baseUrl   the base url to the service.
	 * @param accessKey your access key for the service
	 */
	public Environment(String baseUrl, String accessKey) {
		this.baseUrl = baseUrl;
		this.accessKey = accessKey;
	}

	/**
	 * Create a new Environment instance to access the specific end point url with
	 * the given access key.
	 * 
	 * @param endpoint  the service endpoint to access
	 * @param accessKey your access key for the service
	 */
	public Environment(Endpoint endpoint, String accessKey) {
		this(endpoint.getBaseUrl(), accessKey);
	}

	/**
	 * Set the default environment with the given settings to be used by default.
	 * 
	 * @param accessKey your access key for the service
	 * @see Endpoint
	 */
	public static void setDefaults(String accessKey) {
		DEFAULT_ENVIRONMENT = new Environment(accessKey);
	}
	
	/**
	 * Set the default environment with the given settings to be used by default.
	 * 
	 * @param baseUrl   the base url ({@link Endpoint}) to the service.
	 * @param accessKey your access key for the service
	 * @see Endpoint
	 */
	public static void setDefaults(String baseUrl, String accessKey) {
		DEFAULT_ENVIRONMENT = new Environment(baseUrl, accessKey);
	}

	/**
	 * Set the default environment with the given settings to be used by default.
	 * 
	 * @param endpoint {@link Endpoint} to the service.
	 * @param accessKey your access key for the service
	 * @see Endpoint
	 */
	public static void setDefaults(Endpoint endpoint, String accessKey) {
		DEFAULT_ENVIRONMENT = new Environment(endpoint.getBaseUrl(), accessKey);
	}
	
	/**
	 * Set the default environment with the given settings to be used by default.
	 * 
	 * @param envBuilder the set of properties to use as defaults
	 */
	public static void setDefaults(EnvironmentBuilder envBuilder) {
		DEFAULT_ENVIRONMENT = new Environment(envBuilder.build());
	}

	/**
	 * Get the default environment configured by an earlier call to initialise().
	 * 
	 * @return null if it has not been initialised.
	 */
	public static Environment getDefaultEnvironment() {
		return DEFAULT_ENVIRONMENT;
	}

	/**
     * The base URL for the web service end point.
     * 
     * @return base url of docmosis cloud or tornado endpoint.
     */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * The access key for using the end point.
	 * 
	 * @return access key of docmosis cloud or tornado endpoint.
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * Get the maximum number of tries that should be attempted to service
	 * this request when a server/network error occurs.
	 * 
	 * @return the maximum number of tries configured.
	 */
	public int getMaxTries() {
		return maxTries;
	}

	/**
	 * Get the retry delay (milliseconds) to apply when
	 * a retry is required.
	 * 
	 * @return the configured retry delay
	 */
	public long getRetryDelayMS() {
		return retryDelayMS;
	}

	/**
	 * Get the client connection timeout (milliseconds) to establish 
	 * the connection with the remote host.
	 * 
	 * @return the configured client connection timeout
	 */
	public long getConnectTimeoutMS() {
		return connectTimeoutMS;
	}

	/**
	 * Get the read timeout (milliseconds) between packets.
	 * 
	 * @return the configured read timeout
	 */
	public long getReadTimeoutMS() {
		return readTimeoutMS;
	}
	
	/**
	 * Get a full url by combining the current environment url with the given relative path.
	 * @param relativePath the relative path of the url.
	 * 
	 * @return a combined url string.
	 */
	public String getUrl(String relativePath) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl);
		if (baseUrl.endsWith("/")) {
			while (relativePath.startsWith("/") && !relativePath.isEmpty()) {
				relativePath = relativePath.substring(1);
			}
			sb.append(relativePath);
		} else {
			if (!relativePath.startsWith("/")) {
				sb.append('/');
			}
			sb.append(relativePath);
		}
		
		return sb.toString();
	}

	/**
	 * Get the Proxy object containing proxy host, port and user credentials.
	 * 
	 * @return EnvironmentConfiguration.Proxy object
	 */
	public Proxy getProxy()
	{
		return proxy;
	}
	
	public String getSdkVersion()
	{
		return sdkVersion;
	}
	
	public String getOS()
	{
		return osVersion;
	}

	@Override
	public String toString() {
		return "Environment [accessKey=" + accessKey + ", baseUrl=" + baseUrl + ", maxTries=" + maxTries
				+ ", retryDelayMS=" + retryDelayMS + "]";
	}


	/**
	 * Determine if this is a valid-looking environment making sure the minimum fields have
	 * been configured via one of the initialise() methods.
	 * 
	 * @param accessKeyMandatory if true the access key is validated against null
	 * @throws InvalidEnvironmentException if the mandatory minimum settings (base url and 
	 * optionally the access key) are not set. 
	 */
	public void validate(boolean accessKeyMandatory) throws InvalidEnvironmentException
	{
		if (baseUrl == null) {
			throw new InvalidEnvironmentException("Environment does not have a Base Url configured");
		}
		
		if (accessKeyMandatory && accessKey == null) {
			throw new InvalidEnvironmentException("Environment does not have an Access Key configured");
		}
	}
	

	/**
	 * Determine if there is a valid-looking environment to work with either via the 
	 * given parameter or the defaults have already been set by the initialise() methods.
	 *  
	 * @param env an overriding environment to use (may be null).
	 * @param accessKeyMandatory if true the access key is validated against null
	 * @throws InvalidEnvironmentException if the given environment or default environment
	 * do not provide a base url and access key. 
	 */
	public static void validate(Environment env, boolean accessKeyMandatory) throws InvalidEnvironmentException
	{
		if (env == null) {
			if (DEFAULT_ENVIRONMENT == null) {
				throw new InvalidEnvironmentException("Environment not initialised");
			} else {
				DEFAULT_ENVIRONMENT.validate(accessKeyMandatory);
			}
		} else {
			env.validate(accessKeyMandatory);
		}
	}
	
	private synchronized String getVersion()
	{
        String version = null;

        // try to load from maven properties first
        try {
            Properties p = new Properties();
            InputStream is = getClass().getResourceAsStream("/META-INF/maven/com.docmosis/docmosis-cloud-sdk-java/pom.properties");
            if (is != null) {
                p.load(is);
                version = p.getProperty("version", "");
            }
        } catch (Exception e) {
            // ignore
        }

        // fallback to using Java API
        if (version == null) {
            Package aPackage = getClass().getPackage();
            if (aPackage != null) {
                version = aPackage.getImplementationVersion();
                if (version == null) {
                    version = aPackage.getSpecificationVersion();
                }
            }
        }

        if (version == null) {
            // we could not compute the version so use a blank.
            version = "";
        }

        return version;
    }
	
	private synchronized String getOSVersion()
	{
		String os = System.getProperty("os.name") + " " + System.getProperty("os.version");
    	
		if (os.toLowerCase().contains("windows")) {
			//System.getProperty("os.name") & System.getProperty("os.version") returns incorrect for Windows 10, so try to get Windows version from cmd.
			try {
				String line = null;
				os = "";
		    	Runtime rt = Runtime.getRuntime();
		    	Process pr = rt.exec("cmd.exe /c ver");
		    	BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		    	while((line=in.readLine()) != null) {
		    		os+=line;
		    	}
		    	if (!os.toLowerCase().contains("windows")) {
		    		//Unexpected output.
		    		os = "Windows";
		    	}
			} catch (Exception e) {
		        // ignore
		    }
		}
		
		if (os.isEmpty()) {
			// we could not determine the os version so use a blank.
			os = "";
		}
		return os;
	}
}
