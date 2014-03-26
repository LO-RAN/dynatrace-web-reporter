package org.loran.gwt.client.datasources;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.events.ErrorEvent;
import com.smartgwt.client.data.events.HandleErrorHandler;
import com.smartgwt.client.data.fields.DataSourceLinkField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.SC;

public class DashboardsDataSource extends DataSource {
 
	public DashboardsDataSource(ServerConfig serverConfig) {
		setDataURL(serverConfig.getDashboardsURL());
		setClientOnly(true);
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//dashboard");
				
		DataSourceTextField id = new DataSourceTextField("id", "Dashboard");  
		DataSourceTextField desc = new DataSourceTextField("description", "Description");
		desc.setDetail(true);
		DataSourceLinkField href = new DataSourceLinkField("hrefrel", "Report"); 
		//href.setDetail(true);

		setFields(id, desc, href); 
		
		addHandleErrorHandler(new HandleErrorHandler(){

			@Override
			public void onHandleError(ErrorEvent event) {
				SC.say("Error getting dashboards list");				
			}});
	}
}
