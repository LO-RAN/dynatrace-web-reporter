package org.loran.gwt.client.datasources;

import java.util.Date;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

public class MapDataSource extends DataSource {

	public MapDataSource(ServerConfig serverConfig, String strURL, String measureName, Date from, Date to) {
		setDataURL(serverConfig.getDashletsURL(strURL, from, to));
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//worldmapdashlet/worldmap/datapoint");

		DataSourceTextField country = new DataSourceTextField("country", "country");  
		country.setPrimaryKey(true);
		
		DataSourceIntegerField avg = new DataSourceIntegerField("total", "total");  
		DataSourceIntegerField min = new DataSourceIntegerField("satisfied", "satisfied");  
		DataSourceIntegerField max = new DataSourceIntegerField("tolerating", "tolerating");  
		DataSourceIntegerField sum = new DataSourceIntegerField("frustrated", "frustrated");  

		setFields(country, avg, min, max, sum);
		
		setCacheAllData(true);
	}
}
