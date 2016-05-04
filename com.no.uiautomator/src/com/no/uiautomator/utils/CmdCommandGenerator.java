package com.no.uiautomator.utils;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.preference.IPreferenceStore;

import com.no.uiautomator.QuickUiautomatorActivator;
import com.no.uiautomator.preferences.WorkFlowPreferenceConstants;

public class CmdCommandGenerator {
	private static final String START_CMD = "cmd /c ";

	/**
	 * 生成产生build.xml文件的cmd的指令
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static String buildbuildxmlFileCommand(String path) {
		File file = new File(path);
		String filename = file.getName();

		IPreferenceStore store = QuickUiautomatorActivator.getDefault().getPreferenceStore();
		String id = store.getString(WorkFlowPreferenceConstants.ID);

		String cmd = START_CMD + "android create uitest-project -n " + filename + " -t " + id + " -p " + path;
		System.out.println("cmd=" + cmd);
		return cmd;
	}

	/**
	 * 生成生成jar文件的cmd指令
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static String buildbuildJarCommand(String path) {
		String cmd = START_CMD + "ant build";
		System.out.println("cmd=" + cmd);
		return cmd;
	}

	/**
	 * 生成PUSH到手机的cmd指令
	 * 
	 * @param path
	 * @return
	 */
	public static String buildPushToAndroidCommand(String path) {
		File file = new File(path);
		String filename = file.getName();
		String jarBinpath = path + File.separator + "bin" + File.separator + filename + ".jar";
		String cmd = START_CMD + "adb push " + jarBinpath + " data/local/tmp";
		System.out.println("cmd=" + cmd);
		return cmd;

	}

	/**
	 * 生成执行命令cmd指令
	 * 
	 * @param path
	 * @return
	 */
	public static String buildstartAndroidCommand(String path, String packageclassname) {
		File file = new File(path);
		String filename = file.getName();
		String jarname = filename + ".jar";
		String cmd = START_CMD + "adb shell uiautomator runtest " + jarname + " -c " + packageclassname;
		System.out.println("cmd=" + cmd);
		return cmd;
	}
}
