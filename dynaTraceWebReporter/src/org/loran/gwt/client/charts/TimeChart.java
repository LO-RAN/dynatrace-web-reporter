package org.loran.gwt.client.charts;

import java.util.Date;

import org.moxieapps.gwt.highcharts.client.Axis;
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

public class TimeChart extends Chart {
	String title="";
	
	public TimeChart() {

		
		setZoomType(Chart.ZoomType.X);
		//setSpacingRight(20);
		setTitle(title);

		setLinePlotOptions(new LinePlotOptions()
				.setLineWidth(1)
				.setMarker(
						new Marker().setEnabled(false).setHoverState(
								new Marker().setEnabled(true).setRadius(5)))
				.setShadow(false).setHoverStateLineWidth(1));

		getXAxis().setType(Axis.Type.DATE_TIME);
		getXAxis().setEndOnTick(true);
		
		setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
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
		removeAllSeries();
	}

	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title=title;
		setTitle( new ChartTitle().setText(title)
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
			
			Type type=Type.LINE;
			
			if("line".equals(chartType)){
				type=Type.LINE;
			}else if ("spline".equals(chartType)){
				type=Type.SPLINE;
			}else if ("area".equals(chartType)){
				type=Type.AREA;
			}else if ("column".equals(chartType)){
				type=Type.COLUMN;
		}
		
			Series series = createSeries();

			series
					.setName(label+" ("+aggregation+")")
					.setType(type)
					.setPlotOptions(new LinePlotOptions()  
						.setPointInterval(30 * 1000) /* 30 seconds */
						.setPointStart(records[0].getAttributeAsLong("timestamp"))
						.setColor(color)
						)
					;  

			//for (int i = 0; i < rl.getLength(); i++) {
			for (Record r : records) {
				series.addPoint(new Point(r.getAttributeAsDouble(aggreg)));
			}
			addSeries(series);
			

		
		getYAxis().setAxisTitleText(unit);
	
		}
		
		
	}
}
