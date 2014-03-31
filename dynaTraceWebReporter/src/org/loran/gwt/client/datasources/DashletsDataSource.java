package org.loran.gwt.client.datasources;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.events.ErrorEvent;
import com.smartgwt.client.data.events.HandleErrorHandler;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceLinkField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.SC;

public class DashletsDataSource extends DataSource {
 
		
	public DashletsDataSource(ServerConfig serverConfig, String strURL) {
		setDataURL(serverConfig.getDashletsURL(strURL));

		setClientOnly(true);
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//chartdashlet");
				
		DataSourceTextField name = new DataSourceTextField("name", "Name");  
		DataSourceTextField desc = new DataSourceTextField("description", "Description");
		desc.setDetail(true);
		DataSourceBooleanField showabsolutevalues = new DataSourceBooleanField("showabsolutevalues", "Absolute"); 
		showabsolutevalues.setDetail(true);

		DataSourceField measures = new DataSourceField();
		measures.setName("measures");

		measures.setTypeAsDataSource(new MeasuresDataSource());
		measures.setMultiple(true);
		measures.setHidden(true);

		measures.setValueXPath("measures/measure");

		setFields(name, desc, showabsolutevalues, measures); 
		setCacheAllData(true);
		
		addHandleErrorHandler(new HandleErrorHandler(){

			@Override
			public void onHandleError(ErrorEvent event) {
				SC.say("Error getting dashlets list");				
			}});
	}
}
