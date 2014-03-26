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
		Offline.remove("dtProtocol");
		Offline.remove("dtHost"); //$NON-NLS-1$
		Offline.remove("dtPort"); //$NON-NLS-1$
		Offline.remove("dtUser"); //$NON-NLS-1$
		Offline.remove("dtPwd"); //$NON-NLS-1$
	}

	public void saveSettings() {
		Offline.put("dtProtocol", protocol.getValueAsString());
		Offline.put("dtHost", dtHost.getValueAsString()); //$NON-NLS-1$
		Offline.put("dtPort", dtPort.getValueAsString()); //$NON-NLS-1$
		Offline.put("dtUser", user.getValueAsString()); //$NON-NLS-1$
		Offline.put("dtPwd", password.getValueAsString()); //$NON-NLS-1$
	}

	public void restoreSettings() {

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
