package org.loran.gwt.client.datasources;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.events.ErrorEvent;
import com.smartgwt.client.data.events.HandleErrorHandler;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.SC;

public class LicenseDataSource extends DataSource {
 
	public LicenseDataSource(ServerConfig serverConfig) {
		setDataURL(serverConfig.getLicenseURL());
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//licenseinformation");
		
		DataSourceTextField licensedto = new DataSourceTextField("licensedto", "Licensed to");
		licensedto.setPrimaryKey(true);

		DataSourceTextField licensenumber = new DataSourceTextField("licensenumber", "License number");
		DataSourceTextField licenseedition = new DataSourceTextField("licenseedition", "License edition");
		DataSourceFloatField usedvolumepercentage = new DataSourceFloatField("usedvolumepercentage", "Used volume percentage");
		usedvolumepercentage.setDecimalPrecision(2);
		usedvolumepercentage.setDecimalPad(2);
		DataSourceDateField nextvolumerenewaldate = new DataSourceDateField("nextvolumerenewaldate", "Next volume renewal date");
		DataSourceDateField validfrom = new DataSourceDateField("validfrom", "Valid from");
		DataSourceDateField expiredate = new DataSourceDateField("expiredate", "Expire date");
		DataSourceIntegerField currentuemtransactions = new DataSourceIntegerField("currentuemtransactions", "Remaining UEM transactions");
		DataSourceIntegerField maximaluemtransactions = new DataSourceIntegerField("maximaluemtransactions", "Maximal UEM transactions");

		DataSourceField licensedagents = new DataSourceField();
		licensedagents.setName("licensedagents");

		licensedagents.setTypeAsDataSource(new LicensedAgentsDataSource());
		licensedagents.setMultiple(true);
		licensedagents.setHidden(true);
		licensedagents.setValueXPath("licensedagents/agent");

		setFields(licensedto,licensenumber,licenseedition,usedvolumepercentage,nextvolumerenewaldate,validfrom,expiredate,currentuemtransactions,maximaluemtransactions,licensedagents); 
		
		addHandleErrorHandler(new HandleErrorHandler(){

			@Override
			public void onHandleError(ErrorEvent event) {
				SC.warn("Error getting license info");
			}});
	}



}
