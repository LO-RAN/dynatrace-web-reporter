package org.loran.gwt.client.datasources;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

public class WorldMapDataSource extends DataSource {

	public WorldMapDataSource(ServerConfig serverConfig, String dashboard, String measureName) {
		//setDataURL(serverConfig.getDashletsURL(dashboard));
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//worldmapdashlet/worldmap/datapoint");

		DataSourceTextField country = new DataSourceTextField("country", "country");  
		
		DataSourceIntegerField total = new DataSourceIntegerField("total", "total");  
		DataSourceIntegerField satisfied = new DataSourceIntegerField("satisfied", "satisfied");  
		DataSourceIntegerField tolerating = new DataSourceIntegerField("tolerating", "tolerating");  
		DataSourceIntegerField frustrated = new DataSourceIntegerField("frustrated", "frustrated");  

		setFields(country, total, satisfied, tolerating, frustrated);
		
		setCacheAllData(true);
	}
}
