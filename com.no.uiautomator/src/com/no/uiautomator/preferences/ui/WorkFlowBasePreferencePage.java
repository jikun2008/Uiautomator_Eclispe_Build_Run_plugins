package com.no.uiautomator.preferences.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.no.uiautomator.QuickUiautomatorActivator;
import com.no.uiautomator.preferences.WorkFlowPreferenceConstants;

/**
 * 首选项中的workFlow页面
 * 
 * @author lww
 *
 */
public class WorkFlowBasePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Text userName; // 用户名
	private Text password; // 密码框

	public WorkFlowBasePreferencePage() {
		super();
		setPreferenceStore(QuickUiautomatorActivator.getDefault().getPreferenceStore());
		setDescription("This is a workflowBase PreferencePage!");
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	// 该方法为必须实现的方法，在此方法中创建页面上的各种控件
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		// 获取保存此页面的PreferenceStore对象
		IPreferenceStore preferenceStore = getPreferenceStore();

		new Label(composite, SWT.LEFT).setText("登录用户名:");
		userName = new Text(composite, SWT.BORDER);
		userName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// 设置用户名为保存在文件中的值
		userName.setText(preferenceStore.getString(WorkFlowPreferenceConstants.ID));

		new Label(composite, SWT.LEFT).setText("登录密码:");
		password = new Text(composite, SWT.BORDER);
		password.setEchoChar('*'); // 设置密码用*显示
		password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// 设置密码为保存在文件中的值
		password.setText(preferenceStore.getString(WorkFlowPreferenceConstants.ID));
		return composite;
	}

	/*
	 * 覆盖父类中的方法，但单击“恢复默认值”按钮时调用该方法
	 */
	protected void performDefaults() {
		IPreferenceStore preferenceStore = getPreferenceStore();
		userName.setText(preferenceStore.getDefaultString(WorkFlowPreferenceConstants.ID));
		password.setText(preferenceStore.getDefaultString(WorkFlowPreferenceConstants.ID));
	}

	/*
	 * 覆盖父类中的方法，但单击“应用”按钮时调用该方法
	 */
	public boolean performOk() {
		IPreferenceStore preferenceStore = getPreferenceStore();
		if (userName != null)
			preferenceStore.setValue(WorkFlowPreferenceConstants.ID, userName.getText());
		if (password != null)
			preferenceStore.setValue(WorkFlowPreferenceConstants.ID, password.getText());
		return true;
	}

	@Override // 用于扩展自己的按钮
	protected void contributeButtons(Composite parent) {
		// super.contributeButtons(parent);
		Button bt1 = new Button(parent, SWT.NONE);
		bt1.setText("按钮一");
		((GridLayout) parent.getLayout()).numColumns++;
		Button bt2 = new Button(parent, SWT.NONE);
		bt2.setText("按钮二");
		((GridLayout) parent.getLayout()).numColumns++;
	}
}