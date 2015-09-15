package org.loran.gwt.client.config;

import java.util.Date;


public class ServerConfig {

	private String user;
	private String password;
	private String protocol;
	private String dtHost;
	private String dtPort;

	public ServerConfig(String user, String password, String protocol,	String dtHost, String dtPort) {
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
	
	public String getDashletsURL(String dashBoard, Date from , Date to) {
		String URL= getProxiedURLPrefix() 
				+ "/rest/management/dashboard/"
				+ com.google.gwt.http.client.URL.encodeQueryString(
						dashBoard
				);
		// add datetime range if both values are not null
		if(! (from==null) && ! (to==null)){
			URL+= "?filter=tf:CustomTimeframe?"+from.getTime()+":"+to.getTime();
		}
		
		return URL;
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

	public String getLicenseURL() {

		return getProxiedURLPrefix() + "/rest/management/server/license/information";
	}
}
