package org.loran.gwt.client.portal;

import com.smartgwt.client.widgets.layout.PortalLayout;

public class MyPortal extends PortalLayout{

	/**
	 * @param args
	 */
	public MyPortal(){ 
		super(1);
		setHeight100();
		setWidth100();
		setCanResizePortlets(true);
		setCanDragResize(true);
		setShowColumnMenus(false);

	}
	
	public void addPortlet(){
		
	}
	
}
