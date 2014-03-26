package org.loran.gwt.client.portal;

import org.loran.gwt.client.charts.ResizeableChartCanvas;
import org.loran.gwt.client.charts.TimeChart;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.Portlet;

public class MyPortlet extends Portlet {
	TimeChart chart;

	public MyPortlet() {


		chart = new TimeChart();
		chart.setTitle("Measurements");

		ResizeableChartCanvas chartCanvas = new ResizeableChartCanvas(chart);
		addItem(chartCanvas);

		// add a gear on the header, giving access to portlet settings
		HeaderControl settings = new HeaderControl(HeaderControl.SETTINGS,
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						final Window winModal = new Window();
						winModal.setWidth(360);
						winModal.setHeight(115);
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
						DynamicForm form = new DynamicForm();
						form.setHeight100();
						form.setWidth100();
						form.setPadding(5);
						form.setLayoutAlign(VerticalAlignment.BOTTOM);
						TextItem textItem = new TextItem();
						textItem.setTitle("Text");
						DateItem dateItem = new DateItem();
						dateItem.setTitle("Date");
						DateItem dateItem2 = new DateItem();
						dateItem2.setTitle("Date");
						dateItem2.setUseTextField(true);
						form.setFields(textItem, dateItem, dateItem2);
						winModal.addItem(form);
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
