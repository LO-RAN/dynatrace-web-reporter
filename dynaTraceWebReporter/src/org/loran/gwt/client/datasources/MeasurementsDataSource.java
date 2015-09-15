package org.loran.gwt.client.datasources;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.types.DSDataFormat;

public class MeasurementsDataSource extends DataSource{
	
	public MeasurementsDataSource() {
		
				
		setDataFormat(DSDataFormat.XML);

		DataSourceIntegerField timestamp = new DataSourceIntegerField("timestamp", "timestamp");  
		
		DataSourceFloatField avg = new DataSourceFloatField("avg", "avg");  
		DataSourceFloatField min = new DataSourceFloatField("min", "min");  
		DataSourceFloatField max = new DataSourceFloatField("max", "max");  
		DataSourceFloatField sum = new DataSourceFloatField("sum", "sum");  
		DataSourceIntegerField count = new DataSourceIntegerField("count", "count");  
		
		setFields(timestamp, avg, min, max, sum, count);
		
		setCacheAllData(true);
	}
}
