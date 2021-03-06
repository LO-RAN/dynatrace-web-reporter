package org.loran.gwt.client;

import org.loran.gwt.client.config.ServerConfig;
import org.loran.gwt.client.datasources.ConfigurationsDataSource;
import org.loran.gwt.client.datasources.DashboardsDataSource;
import org.loran.gwt.client.datasources.DashletsDataSource;
import org.loran.gwt.client.datasources.LicenseDataSource;
import org.loran.gwt.client.datasources.LicensedAgentsDataSource;
import org.loran.gwt.client.datasources.ProfilesDataSource;
import org.loran.gwt.client.datasources.VersionDataSource;
import org.loran.gwt.client.forms.ChartForm;
import org.loran.gwt.client.forms.ServerSettingsForm;
import org.loran.gwt.client.portal.ChartPortlet;
import org.loran.gwt.client.portal.MyPortal;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.rpc.HandleErrorCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.DeviceMode;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.PageOrientation;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.PageKeyHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Portlet;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.SplitPane;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DynaTraceWebReporter implements EntryPoint {
	
	
	SplitPane splitPane;
	ListGrid dashboardsGrid;
	ListGrid dashletsGrid;
	ListGrid profilesGrid;
	ListGrid licenseGrid;
	ServerSettingsForm df;
	DynamicForm vdf;
	ChartForm chartForm;
	MyPortal portal;

	// IButton showHeadersToggle;

	IButton addPortletButton;
	IButton clearChartButton;

	DataSource dashletsDS;
	DataSource measuresDS;
	DataSource measurementsDS;
	ResultSet rs;

	ServerConfig serverConfig;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {


		RPCManager.setHandleErrorCallback(new HandleErrorCallback() {

			@Override
			public void handleError(DSResponse response, DSRequest request) {

				SC.warn("Error : " + response.getStatus() + " "
						+ response.getHttpResponseCode() + " "
						+ response.getHttpResponseText());

			}
		});
		


		HLayout main = new HLayout();
		main.setWidth100();
		main.setHeight100();
		// make the UI larger so that it's easier to handle on a touch screen
		Canvas.resizeControls(10);
		Canvas.resizeFonts(2);

		// added to allow developer's console to be started with "SHIFT+C"
		// shortcut
			KeyIdentifier debugKey = new KeyIdentifier();
			debugKey.setShiftKey(true);
			//debugKey.setCtrlKey(true);
			debugKey.setKeyName("C"); //$NON-NLS-1$

			Page.registerKey(debugKey, new PageKeyHandler() {
				public void execute(String keyName) {
					SC.showConsole();
				}
			});

		df = new ServerSettingsForm();
		df.restoreSettings();

		vdf = new DynamicForm();
		vdf.setWidth(300);
		vdf.setTitleWidth("*"); //$NON-NLS-1$

		chartForm = new ChartForm();

		dashboardsGrid = new ListGrid();
		dashboardsGrid.setID("dashboardsGrid"); //$NON-NLS-1$
		dashboardsGrid.setHeight100();
		dashboardsGrid.setWidth("33%");
		dashboardsGrid.setTitle("Dashboards");
		// allow only one selection at a time
		dashboardsGrid.setSelectionType(SelectionStyle.SINGLE);
		dashboardsGrid.setEditEvent(ListGridEditEvent.CLICK);

		dashboardsGrid.groupBy("session");

		dashboardsGrid.addDoubleClickHandler(new DoubleClickHandler() {
			public void onDoubleClick(DoubleClickEvent event) {
				addPortletButton.setDisabled(true);

				Record record = dashboardsGrid.getSelectedRecord();
				if(record != null){

				dashletsDS = new DashletsDataSource(serverConfig, record
						.getAttributeAsString("id"), record
						.getAttributeAsDate("from"), record
						.getAttributeAsDate("to")); //$NON-NLS-1$

				dashletsGrid.setDataSource(dashletsDS);

				dashletsGrid.fetchData();
				}
			}

		});

		dashletsGrid = new ListGrid();
		dashletsGrid.setID("dashletsGrid"); //$NON-NLS-1$
		dashletsGrid.setHeight100();
		dashletsGrid.setWidth("33%");
		dashletsGrid.setTitle("Dashlets");
		// allow multiple selection through repeated simple left clicks
		dashletsGrid.setSelectionType(SelectionStyle.SIMPLE);

		profilesGrid = new ListGrid() {
			protected ListGrid getExpansionComponent(final ListGridRecord record) {

				ListGrid configurationsGrid = new ListGrid();
				configurationsGrid.setWidth100();
				configurationsGrid.setHeight(100);

				DataSource configurationsDS = new ConfigurationsDataSource(
						serverConfig, record.getAttributeAsString("id")); //$NON-NLS-1$

				configurationsGrid.setDataSource(configurationsDS);
				configurationsGrid.fetchData();

				return configurationsGrid;
			}
		};

		profilesGrid.setID("profilesGrid"); //$NON-NLS-1$
		profilesGrid.setHeight("50%");
		profilesGrid.setWidth100();
		profilesGrid.setTitle("Profiles");
		profilesGrid.setCanExpandRecords(true);
		profilesGrid.setExpansionMode(ExpansionMode.RELATED);
		// allow only one selection at a time
		profilesGrid.setSelectionType(SelectionStyle.SINGLE);

		licenseGrid = new ListGrid() {
			protected ListGrid getExpansionComponent(final ListGridRecord record) {

				ListGrid agentsDetailsGrid = new ListGrid();
				agentsDetailsGrid.setWidth100();
				agentsDetailsGrid.setHeight(200);

				DataSource licensedAgentsDS = new LicensedAgentsDataSource();

				agentsDetailsGrid.setDataSource(licensedAgentsDS);
				// agentsDetailsGrid.fetchData();

				agentsDetailsGrid.setData(record
						.getAttributeAsRecordList("licensedagents"));

				return agentsDetailsGrid;
			}
		};
		licenseGrid.setDetailDS(new LicensedAgentsDataSource());

		licenseGrid.setID("licenseGrid"); //$NON-NLS-1$
		licenseGrid.setHeight("50%");
		licenseGrid.setWidth100();
		licenseGrid.setTitle("License");
		licenseGrid.setCanExpandRecords(true);
		licenseGrid.setExpansionMode(ExpansionMode.RELATED);
		// allow only one selection at a time
		licenseGrid.setSelectionType(SelectionStyle.SINGLE);

		addPortletButton = new IButton("Add selected dashlets");
		addPortletButton.setWidth(150);
		addPortletButton.setDisabled(true);
		addPortletButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int columns = (int) Math.sqrt(dashletsGrid.getSelectedRecords().length);
				int rowNum = 0;
				int rowOffset = 0;
				for (ListGridRecord record : dashletsGrid.getSelectedRecords()) {

					ChartPortlet portlet = new ChartPortlet(record, chartForm
							.getType(), chartForm.getIsInverted());
					
					

					// portlet.setShowHeader(showHeadersToggle.getSelected());
					portal.addPortlet(portlet, 0, rowNum, rowOffset);
					rowOffset = (rowOffset + 1) % columns;

					if (rowOffset == 0) {
						rowNum++;
					}

				}
				
				dashletsGrid.deselectAllRecords();
				
				//splitPane.showDetailPane();
			}
		});

		clearChartButton = new IButton("Remove all dashlets");
		clearChartButton.setWidth(150);
		clearChartButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				for (Portlet portlet : portal.getPortlets()) {
					portlet.destroy();
					;
				}
			}
		});


		dashletsGrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {

				addPortletButton.setDisabled(false);

			}
		});

		IButton connectButton = new IButton("Connect");
		connectButton.setWidth(150);
		connectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				df.saveSettings();

				serverConfig = new ServerConfig(df.getUser(), df.getPassword(),
						df.getProtocol(), df.getHost(), df.getPort());

				// http://localhost:8020/rest/management/version

				DataSource versionDS = new VersionDataSource(serverConfig);

				vdf.setDataSource(versionDS);
				vdf.fetchData();

				DataSource profilesDS = new ProfilesDataSource(serverConfig);
				profilesGrid.setDataSource(profilesDS);

				profilesGrid.fetchData();

				DataSource licenseDS = new LicenseDataSource(serverConfig);
				licenseGrid.setDataSource(licenseDS);
				licenseGrid.setDetailDS(licenseDS.getField("licensedagents")
						.getTypeAsDataSource());

				licenseGrid.fetchData();

				DataSource dashboardsDS = new DashboardsDataSource(serverConfig);
				dashboardsGrid.setDataSource(dashboardsDS);
				dashboardsGrid.fetchData();

				dashboardsGrid.getField("hrefrel").setCellFormatter( //$NON-NLS-1$
						new CellFormatter() {
							public String format(Object value,
									ListGridRecord record, int rowNum,
									int colNum) {
								if (value != null) {
									return "<a href='" //$NON-NLS-1$
											+ serverConfig.getRawURLPrefix()
											+ value.toString()
											+ "' target='_blank'>" //$NON-NLS-1$
											+ "open..." + "</a>"; //$NON-NLS-1$
								} else {
									return ""; //$NON-NLS-1$
								}
							}
						});

				addPortletButton.setDisabled(true);
				// chart.clear();
				splitPane.showDetailPane();
			}
		});

		VLayout formlayout = new VLayout();
		formlayout.addMember(df);
		formlayout.addMember(connectButton);
		formlayout.addMember(vdf);
		formlayout.addMember(profilesGrid);
		formlayout.addMember(licenseGrid);

		// main.addMember(formlayout);
		SectionStack leftSideLayout = new SectionStack();
		leftSideLayout.setWidth(300);
		//leftSideLayout.setShowResizeBar(true);
		leftSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
		leftSideLayout.setAnimateSections(true);

		SectionStackSection serverSection = new SectionStackSection("Server");
		serverSection.setExpanded(true);
		serverSection.setItems(formlayout);

		SectionStackSection aboutSection = new SectionStackSection("About");

		HTMLFlow htmlFlow = new HTMLFlow();
		htmlFlow.setOverflow(Overflow.AUTO);
		htmlFlow.setPadding(10);

		String contents = "Built by <a href=\"mailto:laurent.izac@dynatrace.com\"target=\"_blank\">Laurent IZAC (Dynatrace)</a><br><br>Built with :<br>"
		+" <a href=\"https://www.smartclient.com/smartgwt/showcase\" target=\"_blank\"><b>SmartGWT LGPL 12.0p</b></a>"
		+" <br><a href=\"https://www.moxiegroup.com/moxieapps/gwt-highcharts/showcase/#main\" target=\"_blank\"><b>GWT Highcharts 1.7.0</b></a>";

		htmlFlow.setContents(contents);

		aboutSection.setItems(htmlFlow);

		// otherSection.setItems(new HelpPane());
		aboutSection.setExpanded(false);

		leftSideLayout.setSections(serverSection, aboutSection);

		//main.addMember(leftSideLayout);

		HLayout gridslayout = new HLayout();
		gridslayout.addMember(dashboardsGrid);
		gridslayout.addMember(dashletsGrid);

		VLayout chartlayout = new VLayout();
		chartlayout.addMember(chartForm);
		chartlayout.addMember(addPortletButton);
		chartlayout.addMember(clearChartButton);
		// chartlayout.addMember(showHeadersToggle);

		gridslayout.addMember(chartlayout);

		gridslayout.setShowResizeBar(true);
		gridslayout.setHeight(160);

		// main.addMember(gridslayout);

		portal = new MyPortal();
		VLayout rightSideLayout = new VLayout();
		rightSideLayout.addMember(gridslayout);
		rightSideLayout.addMember(portal);
		//main.addMember(rightSideLayout);
		
		splitPane = new SplitPane();
		
        splitPane.setNavigationTitle("Connection");  
        //splitPane.setListTitle("Selection & content");
        splitPane.setDetailTitle("Selection & content");
        //splitPane.setShowLeftButton(true);  
        //splitPane.setShowRightButton(true);  
//        splitPane.setShowMiniNav(true);
        splitPane.setDeviceMode(DeviceMode.TABLET);  
        splitPane.setPageOrientation(PageOrientation.PORTRAIT);

        splitPane.setNavigationPane(leftSideLayout);  
        //splitPane.setListPane(rightSideLayout);  
        splitPane.setDetailPane(rightSideLayout);  

		main.addMember(splitPane);
        
		main.draw();
		splitPane.showNavigationPane();
	}


}
