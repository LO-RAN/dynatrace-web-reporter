package org.loran.gwt.client.charts;

import java.util.Date;

import org.moxieapps.gwt.highcharts.client.Axis;
import org.moxieapps.gwt.highcharts.client.BaseChart;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Series.Type;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.plotOptions.LinePlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;

public class TimeChart {
	Chart chart;
	String title="";
	
	
	public TimeChart() {		
		chart=new Chart();
		//setBackgroundColor("#2B2B2B");

		chart.setZoomType(BaseChart.ZoomType.X);
		//setSpacingRight(20);
		//setTitle(title);

		chart.setLinePlotOptions(new LinePlotOptions()
					.setLineWidth(1)
					.setMarker(new Marker()
					.setEnabled(false)
					.setHoverState(new Marker()
							.setEnabled(true)
							.setRadius(5)
							)
					)
				.setShadow(true)
				.setHoverStateLineWidth(1)
				);

		chart.getXAxis().setType(Axis.Type.DATE_TIME);
		chart.getXAxis().setEndOnTick(true);
		
		chart.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
			public String format(ToolTipData toolTipData) {
				return "<b>"
						+ toolTipData.getSeriesName()
						+ "</b><br/>"
						+ DateTimeFormat.getFormat("dd MMMM YYYY HH:mm:ss").format(
								new Date(toolTipData.getXAsLong())) + "<br/>"
						+ toolTipData.getYAsDouble();
			}
		}));

	}
	
	public void clear(){		
		chart.removeAllSeries();
	}

	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title=title;
		chart.setTitle( new ChartTitle().setText(title)
				, new ChartSubtitle().setText("(Click and drag in the plot area to zoom in)"));	
	}

	public void addSeries(Record[] records, String label, String unit, String color, String aggregation, String chartType) {

		
		if (null != records && records.length>0) {
			
			String aggreg="";
			if("Average".equals(aggregation)){
				aggreg="avg";
			}else if ("Maximum".equals(aggregation)){
				aggreg="max";
			}else if ("Minimum".equals(aggregation)){
				aggreg="min";
			}else if ("Sum".equals(aggregation)){
				aggreg="sum";
			}else if ("Count".equals(aggregation)){
				aggreg="count";
		}
			
//			Type type=Type.LINE;
//			
//			if("line".equals(chartType)){
//				type=Type.LINE;
//			}else if ("spline".equals(chartType)){
//				type=Type.SPLINE;
//			}else if ("area".equals(chartType)){
//				type=Type.AREA;
//			}else if ("column".equals(chartType)){
//				type=Type.COLUMN;
//		}
//		
			Series series = chart.createSeries();

			series.setName(label+" ("+aggregation+")")
				  .setOption("type", chartType)
				  .setPlotOptions(new LinePlotOptions()  						
				  .setColor(color)
				  .setAllowPointSelect(true)	
				  );
			
			//  	.setPointStart(records[0].getAttributeAsLong("timestamp"))
			//	  .setPointInterval(30 * 1000) /* 30 seconds */


			//for (int i = 0; i < rl.getLength(); i++) {
			for (Record r : records) {
				series.addPoint(new Point(r.getAttributeAsLong("timestamp"),r.getAttributeAsDouble(aggreg)));
			}
			chart.addSeries(series);
			

		
			chart.getYAxis().setAxisTitleText(unit);
	
		}
		
		
	}


	
	public Chart getChart() {
		return chart;
		
	}

}
