package org.loran.gwt.client;

import org.loran.gwt.client.config.ServerConfig;
import org.loran.gwt.client.datasources.ConfigurationsDataSource;
import org.loran.gwt.client.datasources.DashboardsDataSource;
import org.loran.gwt.client.datasources.DashletsDataSource;
import org.loran.gwt.client.datasources.ProfilesDataSource;
import org.loran.gwt.client.datasources.VersionDataSource;
import org.loran.gwt.client.forms.ChartForm;
import org.loran.gwt.client.forms.ServerSettingsForm;
import org.loran.gwt.client.portal.ChartPortlet;
import org.loran.gwt.client.portal.MyPortal;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.rpc.HandleErrorCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.PageKeyHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DynaTraceWebReporter implements EntryPoint {
	ListGrid dashboardsGrid;
	ListGrid dashletsGrid;
	ListGrid profilesGrid;
	// TimeChart chart;
	ServerSettingsForm df;
	DynamicForm vdf;
	ChartForm chartForm;
	MyPortal portal;


	IButton addPortletButton;
	// IButton clearChartButton;

	DataSource dashletsDS;
	DataSource measuresDS;
	DataSource measurementsDS;
	ResultSet rs;
//	Record[] records;

	ServerConfig serverConfig;

	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RPCManager.setHandleErrorCallback(new HandleErrorCallback() {

			@Override
			public void handleError(DSResponse response, DSRequest request) {

		        SC.warn("Error : "+response.getStatus()+" "+response.getHttpResponseCode()+" "+response.getHttpResponseText());
				
			}
		    });

		
		// clearSettings();

		HLayout main = new HLayout();
		main.setWidth100();
		main.setHeight100();

		// added to allow developer's console to be started with "SHIFT+S"
		// shortcut
		if (!GWT.isScript()) {
			KeyIdentifier debugKey = new KeyIdentifier();
			// debugKey.setCtrlKey(true);
			debugKey.setShiftKey(true);
			debugKey.setKeyName("S"); //$NON-NLS-1$

			Page.registerKey(debugKey, new PageKeyHandler() {
				public void execute(String keyName) {
					SC.showConsole();
				}
			});
		}

		df = new ServerSettingsForm();
		df.restoreSettings();


		vdf = new DynamicForm();
		vdf.setWidth(300);
		vdf.setTitleWidth("*"); //$NON-NLS-1$


		chartForm = new ChartForm();


		dashboardsGrid = new ListGrid();
		dashboardsGrid.setID("dashboardsGrid"); //$NON-NLS-1$
		dashboardsGrid.setHeight100();
		dashboardsGrid.setWidth(340);
		dashboardsGrid.setTitle("Dashboards");

		dashletsGrid = new ListGrid();
		dashletsGrid.setID("dashletsGrid"); //$NON-NLS-1$
		dashletsGrid.setHeight100();
		dashletsGrid.setWidth(340);
		dashletsGrid.setTitle("Dashlets");

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
		profilesGrid.setHeight(150);
		profilesGrid.setWidth100();
		profilesGrid.setTitle("Profiles");
		profilesGrid.setCanExpandRecords(true);
		profilesGrid.setExpansionMode(ExpansionMode.RELATED);

		addPortletButton = new IButton("Add selected dashlets");
		addPortletButton.setWidth(150);
		addPortletButton.setDisabled(true);
		addPortletButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				for(ListGridRecord record : dashletsGrid.getSelectedRecords()){
				ChartPortlet portlet = new ChartPortlet(record, chartForm.getType(), chartForm.getIsInverted());
				
				portal.addPortlet(portlet);
				}
			}
		});
		
		/*
		 * clearChartButton = new IButton("Clear Chart");
		 * clearChartButton.setWidth(150); clearChartButton.addClickHandler(new
		 * ClickHandler() { public void onClick(ClickEvent event) {
		 * 
		 * chart.clear(); } });
		 */
		dashboardsGrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				addPortletButton.setDisabled(true);

				dashletsDS = new DashletsDataSource(serverConfig,
						event.getRecord().getAttributeAsString("hrefrel")); //$NON-NLS-1$

				dashletsGrid.setDataSource(dashletsDS);

				dashletsGrid.fetchData();

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

				serverConfig = new ServerConfig(
						 df.getUser()
						,df.getPassword()
						,df.getProtocol()
						,df.getHost()
						,df.getPort()
						);

				// http://localhost:8020/rest/management/version

				DataSource versionDS = new VersionDataSource(serverConfig);

				vdf.setDataSource(versionDS);
				vdf.fetchData();

				DataSource profilesDS = new ProfilesDataSource(serverConfig);
				profilesGrid.setDataSource(profilesDS);

				profilesGrid.fetchData();

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

			}
		});

		VLayout formlayout = new VLayout();
		formlayout.addMember(df);
		formlayout.addMember(connectButton);
		formlayout.addMember(vdf);
		formlayout.addMember(profilesGrid);

		//main.addMember(formlayout);
		SectionStack leftSideLayout = new SectionStack();  
        leftSideLayout.setWidth(280);  
        leftSideLayout.setShowResizeBar(true);  
        leftSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);  
        leftSideLayout.setAnimateSections(true);  
  
        SectionStackSection serverSection = new SectionStackSection("Server");  
        serverSection.setExpanded(true);  
        serverSection.setItems(formlayout);  
  
        SectionStackSection otherSection = new SectionStackSection("Other...");  
        //otherSection.setItems(new HelpPane());  
        otherSection.setExpanded(true);  
  
        leftSideLayout.setSections(serverSection, otherSection);  

        main.addMember(leftSideLayout);

		HLayout gridslayout = new HLayout();
		gridslayout.addMember(dashboardsGrid);
		gridslayout.addMember(dashletsGrid);

		VLayout chartlayout = new VLayout();
		chartlayout.addMember(chartForm);
		chartlayout.addMember(addPortletButton);
		// chartlayout.addMember(clearChartButton);

		gridslayout.addMember(chartlayout);
		
		gridslayout.setShowResizeBar(true);
		gridslayout.setHeight(160);

		//main.addMember(gridslayout);

		portal = new MyPortal();
		VLayout rightSideLayout = new VLayout();
		rightSideLayout.addMember(gridslayout);
		rightSideLayout.addMember(portal);
		main.addMember(rightSideLayout);
		main.draw();
	}

	
}
