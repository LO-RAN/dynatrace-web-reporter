package org.loran.gwt.client.datasources;

import java.util.Date;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.events.ErrorEvent;
import com.smartgwt.client.data.events.HandleErrorHandler;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceLinkField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.MiniDateRangeItem;
import com.smartgwt.client.widgets.form.validator.DateRangeValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

public class DashboardsDataSource extends DataSource {
 
	public DashboardsDataSource(ServerConfig serverConfig) {
		setDataURL(serverConfig.getDashboardsURL());
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//dashboard");
		setClientOnly(true);
				
		DataSourceTextField id = new DataSourceTextField("id", "Dashboard");  
		id.setPrimaryKey(true);
		
		DataSourceTextField session = new DataSourceTextField("session", "Source");  
		session.setDetail(true);;
		
		DataSourceTextField desc = new DataSourceTextField("description", "Description");
		desc.setDetail(true);
		
		DataSourceLinkField href = new DataSourceLinkField("hrefrel", "Report"); 
		//href.setDetail(true);

		DateRangeValidator nowDateValidator = new DateRangeValidator();

		final DataSourceDateTimeField to = new DataSourceDateTimeField("to", "To");
		to.setCanEdit(true);
		to.setValidators(nowDateValidator);

		DataSourceDateTimeField from = new DataSourceDateTimeField("from", "From");
		from.setCanEdit(true);
		nowDateValidator.setMax(new Date());
		from.setValidators(nowDateValidator);
		
		setFields(id, session, desc, href, from, to); 
		
		addHandleErrorHandler(new HandleErrorHandler(){

			@Override
			public void onHandleError(ErrorEvent event) {
				SC.warn("Error getting dashboards list");				
			}});
	}
}
