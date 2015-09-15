package org.loran.gwt.client.datasources;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

public class LicensedAgentsDataSource extends DataSource {
 
		
		public LicensedAgentsDataSource() {
								
			setDataFormat(DSDataFormat.XML);
			setClientOnly(true);

			DataSourceTextField name = new DataSourceTextField("name", "Name");
			name.setValueXPath("@name");
			name.setPrimaryKey(true);
			
			DataSourceIntegerField count = new DataSourceIntegerField("agent", "Count");  
			count.setValueXPath(".");
			setFields(name, count);
			
			setCacheAllData(true);
		}


}
