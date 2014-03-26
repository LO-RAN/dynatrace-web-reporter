package org.loran.gwt.client.charts;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import org.moxieapps.gwt.highcharts.client.BaseChart;

/**
 * A SmartGWT container widget that will contain a GWT Highchart and automatically
 * handle growing/shrinking the chart as the SmartGWT container changes in size.
 *
 * @author squinn@moxiegroup.com (Shawn Quinn)
 * @since 1.0
 */
public class ResizeableChartCanvas extends WidgetCanvas {

    public ResizeableChartCanvas(final BaseChart chart) {
        super(chart);
        chart.setReflow(false);
        final WidgetCanvas wc = this;
        this.addResizedHandler(new ResizedHandler() {
            public void onResized(ResizedEvent event) {
                chart.setSize(wc.getWidth(), wc.getHeight(), false);
            }
        });
        this.addDrawHandler(new DrawHandler() {
            public void onDraw(DrawEvent event) {
                chart.setSize(wc.getWidth(), wc.getHeight(), false);
            }
        });
        wc.setOverflow(Overflow.HIDDEN);
    }
}
