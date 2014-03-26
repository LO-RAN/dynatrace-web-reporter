package org.loran.gwt.client.forms;

import java.util.LinkedHashMap;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ColorPickerItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;

public class ChartForm extends DynamicForm {
	SelectItem chartType;
	ColorPickerItem chartColor;

	public ChartForm() {
		chartType = new SelectItem();
		chartType.setTitle("Chart Type");
		chartType.setTooltip("select visualisation kind");

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("line", "Lines"); //$NON-NLS-1$ 
		valueMap.put("spline", "Spline"); //$NON-NLS-1$ 
		valueMap.put("area", "Area"); //$NON-NLS-1$ 
		valueMap.put("column", "Columns"); //$NON-NLS-1$ 

		chartType.setValueMap(valueMap);

		chartType.setImageURLPrefix("/images/icons/16/"); //$NON-NLS-1$
		chartType.setImageURLSuffix(".png"); //$NON-NLS-1$

		LinkedHashMap<String, String> iconMap = new LinkedHashMap<String, String>();
		iconMap.put("line", "line_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put("spline", "spline_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put("area", "area_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		iconMap.put("column", "column_chart"); //$NON-NLS-1$ //$NON-NLS-2$
		chartType.setValueIcons(iconMap);

		chartType.setDefaultToFirstOption(true);

		chartColor = new ColorPickerItem();
		chartColor.setTitle("Chart Color");

		setWidth(250);
		setTitleWidth("*"); //$NON-NLS-1$
		setFields(new FormItem[] { chartType, chartColor });
	}

	public String getType(){
		return chartType.getValueAsString();
	}
public String getColor(){
	return chartColor.getValueAsString();
}

public void setColor(String color){
	chartColor.setValue(color);
	
}
}
