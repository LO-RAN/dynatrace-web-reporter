package org.loran.gwt.client.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ServerConfig {

	private String user;
	private String password;
	private String protocol;
	private String dtHost;
	private String dtPort;

	public ServerConfig(String user, String password, String protocol,
			String dtHost, String dtPort) {
		this.user = user;
		this.password = password;
		this.protocol = protocol;
		this.dtHost = dtHost;
		this.dtPort = dtPort;
	}

	public String getVersionURL() {

		return getProxiedURLPrefix() + "/rest/management/version";
	}

	public String getDashboardsURL() {
		return getProxiedURLPrefix() + "/rest/management/dashboards";
	}
	
	public String getDashletsURL(String dashBoardURL) {
		return getProxiedURLPrefix() 
				+ com.google.gwt.http.client.URL.encodeQueryString(dashBoardURL)
				+ "?type=xml";
	}

	public String getProfilesURL() {
		return getProxiedURLPrefix() + "/rest/management/profiles";
	}
	
	public String getConfigurationsURL(String profileURL) {
		return getProxiedURLPrefix() 
				+ "/rest/management/profiles/"
				+ com.google.gwt.http.client.URL.encodeQueryString(profileURL)
				+"/configurations";
	}
	
	public String getRawURLPrefix(){
		return protocol + "://" + dtHost + ":" + dtPort;
		
	}
	public String getProxiedURLPrefix(){
		return "proxy?" 
				+ "user="+ com.google.gwt.http.client.URL.encodeQueryString(user) 
				+ "&pwd="+ com.google.gwt.http.client.URL.encodeQueryString(password) 
				+ "&url="+ getRawURLPrefix();
	}
}
