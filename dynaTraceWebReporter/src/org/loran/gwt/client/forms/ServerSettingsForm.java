package org.loran.gwt.client.forms;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.Offline;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class ServerSettingsForm extends DynamicForm {
	SelectItem protocol;
	TextItem dtHost;
	IntegerItem dtPort;
	TextItem user;
	PasswordItem password;

	private static String PROTOCOL_ID= "dtProtocol";
	private static String HOST_ID    = "dtHost";
	private static String PORT_ID    = "dtPort";
	private static String USER_ID    = "dtUSer";
	private static String PASSWORD_ID= "dtPwd";

	public ServerSettingsForm() {
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

		setWidth100();
		setTitleWidth("*"); //$NON-NLS-1$
		setFields(new FormItem[] { protocol, dtHost, dtPort, user, password });

	}
	
	public String getUser(){
		return user.getValueAsString();
	}
	public String getPassword(){
		return password.getValueAsString();
	}
	public String getHost(){
		return dtHost.getValueAsString();
	}
	public String getPort(){
		return dtPort.getValueAsString();
	}
	public String getProtocol(){
		return protocol.getValueAsString();
	}

	public void clearSettings() {
		Offline.remove(PROTOCOL_ID);
		Offline.remove(HOST_ID);
		Offline.remove(PORT_ID);
		Offline.remove(USER_ID);
		Offline.remove(PASSWORD_ID);
	}

	public void saveSettings() {
		Offline.put(PROTOCOL_ID, protocol.getValueAsString());
		Offline.put(HOST_ID, dtHost.getValueAsString());
		Offline.put(PORT_ID, dtPort.getValueAsString());
		Offline.put(USER_ID, user.getValueAsString());
		Offline.put(PASSWORD_ID, password.getValueAsString());
	}

	public void restoreSettings() {

		if (Offline.get(PROTOCOL_ID) != null) {
			protocol.setValue(Offline.get(PROTOCOL_ID));
			// } else {
			// protocol.setValue(protocol.getDefaultValue());
		}

		if (Offline.get(HOST_ID) != null) {
			dtHost.setValue(Offline.get(HOST_ID));
		} else {
			dtHost.setValue("localhost"); //$NON-NLS-1$
		}
		if (Offline.get(PORT_ID) != null) {
			dtPort.setValue(Offline.get(PORT_ID));
		} else {
			dtPort.setValue(8020);
		}
		if (Offline.get(USER_ID) != null) {
			user.setValue(Offline.get(USER_ID));
		} else {
			user.setValue("admin"); //$NON-NLS-1$
		}
		if (Offline.get(PASSWORD_ID) != null) {
			password.setValue(Offline.get(PASSWORD_ID));
		} else {
			password.setValue("admin"); //$NON-NLS-1$
		}
	}
}
