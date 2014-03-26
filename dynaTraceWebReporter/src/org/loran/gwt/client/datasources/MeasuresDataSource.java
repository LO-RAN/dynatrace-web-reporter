package org.loran.gwt.client.datasources;

import org.loran.gwt.client.config.ServerConfig;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

public class MeasuresDataSource extends DataSource {

	public MeasuresDataSource(ServerConfig serverConfig, String strURL) {
		setDataURL(serverConfig.getChartsURL(strURL));
		// setClientOnly(true);
		setDataFormat(DSDataFormat.XML);
		setRecordXPath("//chartdashlet/measures/measure");

		DataSourceTextField measure = new DataSourceTextField("measure",
				"Measure");
		measure.setPrimaryKey(true);

		DataSourceField measurements = new DataSourceField();
		measurements.setName("measurements");

		measurements.setTypeAsDataSource(new MeasurementsDataSource());
		measurements.setMultiple(true);
		measurements.setHidden(true);

		measurements.setValueXPath("measurement");

		DataSourceTextField unit = new DataSourceTextField("unit", "unit");
		DataSourceTextField aggregation = new DataSourceTextField(
				"aggregation", "Aggregation");

		DataSourceTextField color = new DataSourceTextField("color", "color");
		color.setDetail(true);

		DataSourceTextField agenthost = new DataSourceTextField("agenthost",
				"Agent Host");
		agenthost.setDetail(true);

		DataSourceTextField agentgroup = new DataSourceTextField("agentgroup",
				"Agent Group");
		agentgroup.setDetail(true);

		DataSourceTextField systemprofile = new DataSourceTextField(
				"systemprofile", "System Profile");
		systemprofile.setDetail(true);

		DataSourceFloatField avg = new DataSourceFloatField("avg", "avg");
		avg.setDetail(true);

		DataSourceFloatField min = new DataSourceFloatField("min", "min");
		min.setDetail(true);

		DataSourceFloatField max = new DataSourceFloatField("max", "max");
		max.setDetail(true);

		DataSourceFloatField sum = new DataSourceFloatField("sum", "sum");
		sum.setDetail(true);

		DataSourceFloatField lastvalue = new DataSourceFloatField("lastvalue",
				"lastvalue");
		lastvalue.setDetail(true);

		DataSourceIntegerField count = new DataSourceIntegerField("count",
				"count");
		count.setDetail(true);

		setFields(measure, unit, aggregation, color, measurements, agenthost,
				agentgroup, systemprofile, avg, min, max, sum, lastvalue, count);

		setCacheAllData(true);
	}
}