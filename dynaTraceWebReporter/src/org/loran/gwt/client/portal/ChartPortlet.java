package org.loran.gwt.client.portal;

import org.loran.gwt.client.charts.ResizeableChartCanvas;
import org.loran.gwt.client.charts.TimeChart;
import org.loran.gwt.client.forms.ChartForm;
import org.moxieapps.gwt.highcharts.client.Series;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.Portlet;

public class ChartPortlet extends Portlet {
	TimeChart chart;
	ChartForm form;
	Record record;

//    public interface MetaFactory extends BeanFactory.MetaFactory {  
//        BeanFactory<ChartPortlet> getChartPortletBeanFactory();  
//    }  
//    
//    // This method is called just-in-time when serializing an EditContext,  
//    // so that you can update any properties that you want to  
//    // automatically persist.  
//    public void updateEditNode(EditContext editContext, EditNode editNode) {  
//        super.updateEditNode(editContext, editNode);  
//  
//        Canvas properties = new Canvas();  
//        properties.setBackgroundColor(getBackgroundColor());  
//  
//        editContext.setNodeProperties(editNode, properties, true);  
//    }  
//
    
    
	public ChartPortlet(ListGridRecord record, String type, Boolean isInverted) {

		setTitle(record.getAttributeAsString("name"));
		

		Record[] series = record.getAttributeAsRecordArray("measures"); //$NON-NLS-1$

		chart = new TimeChart();
		chart.setTitle(record.getAttributeAsString("name"));
		chart.setInverted(isInverted);
		
		for (Record serie : series) {
			addSeries(serie.getAttributeAsRecordArray("measurements"), //$NON-NLS-1$
					serie.getAttributeAsString("measure"), //$NON-NLS-1$
					serie.getAttributeAsString("unit"), //$NON-NLS-1$
					serie.getAttributeAsString("color"), //$NON-NLS-1$
					serie.getAttributeAsString("aggregation"), //$NON-NLS-1$
					type);
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
						winModal.setShadowDepth(10);
						winModal.setShowShadow(true);


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
						//form.setIsInverted(false);
						//form.setBackgroundColor(chart.getOptions().get("/chart/backgroundColor").toString());
						

						IButton okButton = new IButton("Ok");
						okButton.setWidth(100);
						okButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								chart.setTitle(form.getTitle());
								chart.setInverted(form.getIsInverted());

								
								for(Series series : chart.getSeries()){
									chart.removeSeries(series);
									
									series.setOption("type",form.getType());
									
									series.setOption("color",form.getChartColor());
									
									chart.setBackgroundColor(form.getBackgroundColor());
									
									chart.addSeries(series);
								}
								
								winModal.destroy();
							};
						});
						
						Label portletInfo = new Label();
						portletInfo.setHeight(10);

					portletInfo.setContents(
								 " Column:"+getPortalPosition().getColNum()
								+" Row:"+getPortalPosition().getRowNum()
								+" Position:"+getPortalPosition().getPosition()
								+" Width:"+getWidthAsString()
								+" Height:"+getHeightAsString()
								);
						

						winModal.addItem(form);
						winModal.addItem(portletInfo);
						winModal.addItem(okButton);
						winModal.show();
					}
				});


		setHeaderControls(settings, HeaderControls.HEADER_LABEL,
				HeaderControls.MINIMIZE_BUTTON, HeaderControls.MAXIMIZE_BUTTON,
				HeaderControls.CLOSE_BUTTON);
		
		
	}

	public void addSeries(Record[] records, String label, String unit,
			String color, String aggregation, String chartType) {
		chart.addSeries(records, label, unit, color, aggregation, chartType);

	}

}
