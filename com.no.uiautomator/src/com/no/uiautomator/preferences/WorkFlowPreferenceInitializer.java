package com.no.uiautomator.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.no.uiautomator.QuickUiautomatorActivator;

/**
 * Class used to initialize default preference values. 首选项的初始化
 */
public class WorkFlowPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = QuickUiautomatorActivator.getDefault().getPreferenceStore();
		store.setDefault(WorkFlowPreferenceConstants.ID, 18);

	}
}