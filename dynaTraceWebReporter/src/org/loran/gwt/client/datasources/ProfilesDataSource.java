package org.loran.gwt.client.datasources;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.events.ErrorEvent;
import com.smartgwt.client.data.events.HandleErrorHandler;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceLinkField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.SC;

public class ProfilesDataSource extends DataSource {
 
	public ProfilesDataSource(ServerConfig serverConfig) {
		setDataURL(serverConfig.getProfilesURL());
		setClientOnly(true);
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//systemprofile");
				
		DataSourceTextField id = new DataSourceTextField("id", "System Profile");  
		DataSourceBooleanField enabled = new DataSourceBooleanField("isrecording", "enabled");  
		DataSourceLinkField href = new DataSourceLinkField("href", "HREF");  
		href.setDetail(true);
		
		setFields(id, enabled, href); 
		
		addHandleErrorHandler(new HandleErrorHandler(){

			@Override
			public void onHandleError(ErrorEvent event) {
				SC.warn("Error getting profiles list");				
			}});
	}
}
