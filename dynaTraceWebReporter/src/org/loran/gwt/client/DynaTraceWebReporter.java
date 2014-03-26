package org.loran.gwt.client;

import java.util.LinkedHashMap;

import org.loran.gwt.client.config.ServerConfig;
import org.loran.gwt.client.datasources.ConfigurationsDataSource;
import org.loran.gwt.client.datasources.DashboardsDataSource;
import org.loran.gwt.client.datasources.MeasuresDataSource;
import org.loran.gwt.client.datasources.ProfilesDataSource;
import org.loran.gwt.client.datasources.VersionDataSource;
import org.loran.gwt.client.portal.MyPortal;
import org.loran.gwt.client.portal.MyPortlet;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.Offline;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.PageKeyHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ColorPickerItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DynaTraceWebReporter implements EntryPoint {
	SelectItem protocol;
	TextItem dtHost;
	IntegerItem dtPort;
	TextItem user;
	PasswordItem password;
	ListGrid dashboardsGrid;
	ListGrid measuresGrid;
	ListGrid profilesGrid;
	// TimeChart chart;
	DynamicForm df;
	DynamicForm vdf;
	DynamicForm chartForm;
	MyPortal portal;

	SelectItem chartType;
	ColorPickerItem chartColor;

	IButton addToChartButton;
	// IButton clearChartButton;

	DataSource measuresDS;
	DataSource measurementsDS;
	ResultSet rs;
//	Record[] records;

	ServerConfig serverConfig;

	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

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
		
		

		protocol = new SelectItem();
		protocol.setTitle("Protocol");
		protocol.setTooltip("select plain or encrypted communication scheme");
		protocol.setValueMap("http", "https"); //$NON-NLS-1$ //$NON-NLS-2$
		protocol.setDefaultToFirstOption(true);
		protocol.setRequired(true);

		dtHost = new TextItem();
		dtHost.setTitle("dynaTrace Server Host");
		dtHost.setTooltip("IP address or DNS name of the dynaTrace Server to connect to");
		dtHost.setRequired(true);

		dtPort = new IntegerItem();
		dtPort.setKeyPressFilter("[0-9]"); //$NON-NLS-1$
		dtPort.setTitle("dynaTrace Server Port");
		dtPort.setTextAlign(Alignment.RIGHT);
		dtPort.setTooltip("port of the dynaTrace Server to connect to");
		dtPort.setRequired(true);

		user = new TextItem();
		user.setTitle("User");
		user.setRequired(true);
		user.setTooltip("User Id, as defined in dynaTrace settings");

		password = new PasswordItem();
		password.setTitle("Password");
		password.setTooltip("Password for that user, as managed by the dynaTrace server");
		password.setRequired(true);

		restoreSettings();

		df = new DynamicForm();
		df.setWidth100();
		df.setTitleWidth("*"); //$NON-NLS-1$
		df.setFields(new FormItem[] { protocol, dtHost, dtPort, user, password });

		vdf = new DynamicForm();
		vdf.setWidth(300);
		vdf.setTitleWidth("*"); //$NON-NLS-1$

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

		chartForm = new DynamicForm();
		chartForm.setWidth(250);
		chartForm.setTitleWidth("*"); //$NON-NLS-1$
		chartForm.setFields(new FormItem[] { chartType, chartColor });

		dashboardsGrid = new ListGrid();
		dashboardsGrid.setID("dashboardsGrid"); //$NON-NLS-1$
		dashboardsGrid.setHeight(150);
		dashboardsGrid.setWidth(340);
		dashboardsGrid.setTitle("Dashboards");

		measuresGrid = new ListGrid();
		measuresGrid.setID("measuresGrid"); //$NON-NLS-1$
		measuresGrid.setHeight(150);
		measuresGrid.setWidth(340);
		measuresGrid.setTitle("Measures");

		profilesGrid = new ListGrid() {
			@Override
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

		addToChartButton = new IButton("Add to Chart");
		addToChartButton.setWidth(150);
		addToChartButton.setDisabled(true);
		addToChartButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				MyPortlet portlet = new MyPortlet();
				
				portlet.setTitle(measuresGrid.getSelectedRecord()
						.getAttributeAsString("measure")); //$NON-NLS-1$

//				records = measuresGrid.getSelectedRecord().getAttributeAsRecordArray("measurements");

				portlet.addSeries(
						measuresGrid.getSelectedRecord().getAttributeAsRecordArray("measurements"),
						measuresGrid.getSelectedRecord().getAttributeAsString(
								"measure"), //$NON-NLS-1$
						measuresGrid.getSelectedRecord().getAttributeAsString(
								"unit"), //$NON-NLS-1$
						chartColor.getValueAsString(),
						measuresGrid.getSelectedRecord().getAttributeAsString(
								"aggregation"), chartType.getValueAsString());

				portal.addPortlet(portlet);

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

				measuresDS = new MeasuresDataSource(serverConfig,
						event.getRecord().getAttributeAsString("hrefrel")); //$NON-NLS-1$

				measuresGrid.setDataSource(measuresDS);

				measuresGrid.fetchData();

				addToChartButton.setDisabled(true);
			}
		});

		measuresGrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {

				chartColor.setValue(event.getRecord().getAttributeAsString("color")); //$NON-NLS-1$
				
				addToChartButton.setDisabled(false);
				
	
			}

		});

		IButton connectButton = new IButton("Connect");
		connectButton.setWidth(150);
		connectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				saveSettings();

				serverConfig = new ServerConfig(user.getValueAsString(),
						password.getValueAsString(), protocol
								.getValueAsString(), dtHost.getValueAsString(),
						dtPort.getValueAsString());

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

				measuresGrid.setData(new ListGridRecord[] {});
				addToChartButton.setDisabled(true);
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
		gridslayout.addMember(measuresGrid);

		VLayout chartlayout = new VLayout();
		chartlayout.addMember(chartForm);
		chartlayout.addMember(addToChartButton);
		// chartlayout.addMember(clearChartButton);

		gridslayout.addMember(chartlayout);

		//main.addMember(gridslayout);

		portal = new MyPortal();
		VLayout rightSideLayout = new VLayout();
		rightSideLayout.addMember(gridslayout);
		rightSideLayout.addMember(portal);
		main.addMember(rightSideLayout);
		main.draw();
	}

	private void clearSettings() {
		Offline.remove("dtProtocol");
		Offline.remove("dtHost"); //$NON-NLS-1$
		Offline.remove("dtPort"); //$NON-NLS-1$
		Offline.remove("dtUser"); //$NON-NLS-1$
		Offline.remove("dtPwd"); //$NON-NLS-1$
	}

	private void saveSettings() {
		Offline.put("dtProtocol", protocol.getValueAsString());
		Offline.put("dtHost", dtHost.getValueAsString()); //$NON-NLS-1$
		Offline.put("dtPort", dtPort.getValueAsString()); //$NON-NLS-1$
		Offline.put("dtUser", user.getValueAsString()); //$NON-NLS-1$
		Offline.put("dtPwd", password.getValueAsString()); //$NON-NLS-1$
	}

	private void restoreSettings() {

		if (Offline.get("dtProtocol") != null) {
			protocol.setValue(Offline.get("dtProtocol"));
			// } else {
			// protocol.setValue(protocol.getDefaultValue());
		}

		if (Offline.get("dtHost") != null) { //$NON-NLS-1$
			dtHost.setValue(Offline.get("dtHost")); //$NON-NLS-1$
		} else {
			dtHost.setValue("localhost"); //$NON-NLS-1$
		}
		if (Offline.get("dtPort") != null) { //$NON-NLS-1$
			dtPort.setValue(Offline.get("dtPort")); //$NON-NLS-1$
		} else {
			dtPort.setValue(8020);
		}
		if (Offline.get("dtUser") != null) { //$NON-NLS-1$
			user.setValue(Offline.get("dtUser")); //$NON-NLS-1$
		} else {
			user.setValue("admin"); //$NON-NLS-1$
		}
		if (Offline.get("dtPwd") != null) { //$NON-NLS-1$
			password.setValue(Offline.get("dtPwd")); //$NON-NLS-1$
		} else {
			password.setValue("admin"); //$NON-NLS-1$
		}
	}
}
