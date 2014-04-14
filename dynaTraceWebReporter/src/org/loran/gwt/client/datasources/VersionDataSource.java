package org.loran.gwt.client.datasources;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.events.ErrorEvent;
import com.smartgwt.client.data.events.HandleErrorHandler;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.SC;

public class VersionDataSource extends DataSource {
 
	public VersionDataSource(ServerConfig serverConfig) {
		setDataURL(serverConfig.getVersionURL());
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//result");
		
		DataSourceTextField version = new DataSourceTextField("value", "Version");
		version.setCanEdit(false);

		setFields(version); 
		
		addHandleErrorHandler(new HandleErrorHandler(){

			@Override
			public void onHandleError(ErrorEvent event) {
				SC.warn("Error getting version");
			}});
	}
}
