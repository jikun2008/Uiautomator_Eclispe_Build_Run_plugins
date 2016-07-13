package com.test.uiautomator;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Test extends UiAutomatorTestCase {

	public void testclick() {
		UiObject uiObject = new UiObject(new UiSelector().text("FirstButton"));
		for (int i = 0; i < 100; i++) {
			try {
				uiObject.click();
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
