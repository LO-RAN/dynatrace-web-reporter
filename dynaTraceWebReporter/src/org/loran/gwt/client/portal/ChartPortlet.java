package org.loran.gwt.client.portal;

import org.loran.gwt.client.charts.ResizeableChartCanvas;
import org.loran.gwt.client.charts.TimeChart;
import org.loran.gwt.client.forms.ChartForm;
import org.moxieapps.gwt.highcharts.client.Series;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ButtonClickEvent;
import com.smartgwt.client.widgets.events.ButtonClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.Portlet;

public class ChartPortlet extends Portlet {
	TimeChart chart;
	ChartForm form;
	Record record;

	public ChartPortlet(final Record record) {

		this.record=record;
		
		setTitle(record.getAttributeAsString("name"));
		
		Record[] series=record.getAttributeAsRecordArray("measures");  //$NON-NLS-1$

		
		chart = new TimeChart();
		chart.setTitle(record.getAttributeAsString("name"));

		for(Record serie:series){
			addSeries(
				serie.getAttributeAsRecordArray("measurements"),  //$NON-NLS-1$
				serie.getAttributeAsString("measure"), //$NON-NLS-1$
				serie.getAttributeAsString("unit"), //$NON-NLS-1$
				serie.getAttributeAsString("color"),  //$NON-NLS-1$
				serie.getAttributeAsString("aggregation"),  //$NON-NLS-1$
				"line");
		}

		ResizeableChartCanvas chartCanvas = new ResizeableChartCanvas(chart);
		addItem(chartCanvas);

		// add a gear on the header, giving access to portlet settings
		HeaderControl settings = new HeaderControl(HeaderControl.SETTINGS,
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						final Window winModal = new Window();
						winModal.setWidth(360);
						winModal.setHeight(200);
						winModal.setTitle("Modal Window");
						winModal.setShowMinimizeButton(false);
						winModal.setIsModal(true);
						winModal.setShowModalMask(true);
						winModal.centerInPage();
						

						winModal.addCloseClickHandler(new CloseClickHandler() {
							public void onCloseClick(CloseClickEvent event) {
								winModal.destroy();
							}
						});
						
						
						form = new ChartForm();
						form.setHeight100();
						form.setWidth100();
						form.setPadding(5);
						form.setLayoutAlign(VerticalAlignment.BOTTOM);
						
						form.setTitle(chart.getTitle());
						
						
						for(Series series:chart.getSeries()){
							//series.get
						}
						
						IButton okButton = new IButton("Ok");
						okButton.setWidth(100);
						okButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								chart.setTitle(form.getTitle());
								
								winModal.destroy();
							};
						});

						winModal.addItem(form);
						winModal.addItem(okButton);
						winModal.show();
					}
				});

		HeaderControl refresh = new HeaderControl(HeaderControl.REFRESH,
				new ClickHandler() {
					public void onClick(ClickEvent event) {

					}
				});

		setHeaderControls(refresh, settings, HeaderControls.HEADER_LABEL,
				HeaderControls.MINIMIZE_BUTTON, HeaderControls.MAXIMIZE_BUTTON,
				HeaderControls.CLOSE_BUTTON);
	}

	public void addSeries(Record[] records, String label, String unit,
			String color, String aggregation, String chartType) {
		chart.addSeries(records, label, unit, color, aggregation, chartType);

	}
	
	
	
	
}
