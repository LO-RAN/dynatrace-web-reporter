package org.loran.gwt.client.forms;

import java.util.LinkedHashMap;

import org.moxieapps.gwt.highcharts.client.Series.Type;

import com.smartgwt.client.widgets.form.PropertySheet;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ColorPickerItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class ChartForm extends PropertySheet {
	SelectItem chartType;
	ColorPickerItem chartColor;
	ColorPickerItem backgroundColor;
	TextItem chartTitle;
	CheckboxItem isInverted;
	
	public ChartForm() {
		
		chartTitle = new TextItem();
		chartTitle.setTitle("Title");
		chartType = new SelectItem();
		chartType.setTitle("Chart Type");
		chartType.setTooltip("select visualisation kind");

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put(Type.LINE.toString(), "Lines"); //$NON-NLS-1$ 
		valueMap.put(Type.SPLINE.toString(), "Spline"); //$NON-NLS-1$ 
		valueMap.put(Type.AREA.toString(), "Area"); //$NON-NLS-1$ 
		valueMap.put(Type.COLUMN.toString(), "Columns"); //$NON-NLS-1$ 
		valueMap.put(Type.AREA_SPLINE.toString(), "Area spline"); //$NON-NLS-1$ 
		valueMap.put(Type.BAR.toString(), "Bar"); //$NON-NLS-1$ 
		valueMap.put(Type.PIE.toString(), "Pie"); //$NON-NLS-1$ 
		valueMap.put(Type.SCATTER.toString(), "Scatter"); //$NON-NLS-1$ 

		chartType.setValueMap(valueMap);

		chartType.setImageURLPrefix("/images/icons/16/"); //$NON-NLS-1$
		chartType.setImageURLSuffix(".png"); //$NON-NLS-1$

		LinkedHashMap<String, String> iconMap = new LinkedHashMap<String, String>();
		iconMap.put(Type.LINE.toString(), "line_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put(Type.SPLINE.toString(), "spline_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put(Type.AREA.toString(), "area_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put(Type.COLUMN.toString(), "column_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put(Type.AREA_SPLINE.toString(), "area_spline_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put(Type.BAR.toString(), "bar_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put(Type.PIE.toString(), "pie_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put(Type.SCATTER.toString(), "scatter_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		chartType.setValueIcons(iconMap);

		chartType.setDefaultToFirstOption(true);

		chartColor = new ColorPickerItem();
		chartColor.setTitle("Chart Color");
		
		backgroundColor = new ColorPickerItem();
		backgroundColor.setTitle("Background Color");
		
		isInverted = new CheckboxItem();
		isInverted.setTitle("Invert axes");
		isInverted.setValue(false);

		setWidth(250);
		setTitleWidth("*"); //$NON-NLS-1$
		setFields(new FormItem[] { chartTitle, chartType, chartColor, backgroundColor, isInverted });
	}


	public Boolean getIsInverted() {
		return isInverted.getValueAsBoolean();
	}


	public void setIsInverted(Boolean isInverted) {
		this.isInverted.setValue(isInverted);
	}


	public String getTitle() {
		return chartTitle.getValueAsString();
	}

	public void setTitle(String title) {
		chartTitle.setValue(title);
	}

	public String getType(){
		return chartType.getValueAsString();
	}
	public String getChartColor(){
		return chartColor.getValueAsString();
	}

	public void setChartColor(String color){
		chartColor.setValue(color);
		
	}
	public String getBackgroundColor(){
		return backgroundColor.getValueAsString();
	}

	public void setBackgroundColor(String color){
		backgroundColor.setValue(color);
		
	}
}
