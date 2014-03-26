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

public class ConfigurationsDataSource extends DataSource {
 
	public ConfigurationsDataSource(ServerConfig serverConfig,String profile) {
		setDataURL(serverConfig.getConfigurationsURL(profile));
		setClientOnly(true);
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//configuration");
				
		DataSourceTextField id = new DataSourceTextField("id", "Configuration");  
		DataSourceBooleanField isactive = new DataSourceBooleanField("isactive", "Active");  
		DataSourceLinkField href = new DataSourceLinkField("href", "HREF");
		href.setDetail(true);

		setFields(id,isactive, href); 
		
		addHandleErrorHandler(new HandleErrorHandler(){

			@Override
			public void onHandleError(ErrorEvent event) {
				SC.say("Error getting configurations list");				
			}});
	}

}
