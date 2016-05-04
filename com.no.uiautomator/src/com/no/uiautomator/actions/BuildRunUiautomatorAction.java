package com.no.uiautomator.actions;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.no.uiautomator.QuickUiautomatorActivator;
import com.no.uiautomator.preferences.WorkFlowPreferenceConstants;
import com.no.uiautomator.utils.AutoWork;
import com.no.uiautomator.utils.CmdCommandGenerator;
import com.no.uiautomator.utils.ConsoleFactory;
import com.no.uiautomator.utils.AutoWork.OnCmdListener;

/**
 * 
 * @author Slive
 *
 */
@SuppressWarnings("restriction")
public class BuildRunUiautomatorAction implements IObjectActionDelegate {
	private final String TAG_LOG="QuickCmdAction:";
	private Object selected = null;
	private Class<?> selectedClass = null;
	private String packageclassname = "";

	private String path;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		if (this.selected == null) {
			MessageDialog.openInformation(new Shell(), "Quick Cmd", "Unable to cmd " + this.selectedClass.getName());
			return;
		}
		path = "";
		File directory = null;
		if ((this.selected instanceof IResource)) {
			directory = new File(((IResource) this.selected).getLocation().toOSString());

		} else if ((this.selected instanceof File)) {
			directory = (File) this.selected;
		}

		try {
			String filePath = "";
			String startPath = "c:";
			if (directory != null) {
				if (directory.isDirectory()) {
					filePath = directory.getAbsolutePath();
				} else {
					filePath = directory.getAbsolutePath();
				}

				// 截取当前文件的开头盘符
				startPath = filePath.substring(0, 2);
				packageclassname = filePath;
			}

			filePath = getProjectPath(selected);
			path = filePath;
			packageclassname = packageclassname.replace(filePath+File.separator+"src"+File.separator, "")
					.replace(File.separator, ".").replace(".java", "");
			// 运行CMD命令，并切换到当前目录下
			if (isBuildFileExists(path)) {
				dojar();
			} else {
				dobuildxml();
			}

			// Runtime.getRuntime().exec("cmd /c start cd " + filePath, null,
			// new File(startPath));
		} catch (Exception e) {
			System.err.println(e.toString());
			ConsoleFactory.printToConsole(TAG_LOG+":"+e.toString());
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		IAdaptable adaptable = null;
		this.selected = null;
		if ((selection instanceof IStructuredSelection)) {
			adaptable = (IAdaptable) ((IStructuredSelection) selection).getFirstElement();
			this.selectedClass = adaptable.getClass();
			if ((adaptable instanceof IResource)) {
				this.selected = ((IResource) adaptable);
			} else if (((adaptable instanceof PackageFragment))
					&& ((((PackageFragment) adaptable).getPackageFragmentRoot() instanceof JarPackageFragmentRoot))) {
				this.selected = getJarFile(((PackageFragment) adaptable).getPackageFragmentRoot());
			} else if ((adaptable instanceof JarPackageFragmentRoot)) {
				this.selected = getJarFile(adaptable);
			} else {
				this.selected = ((IResource) adaptable.getAdapter(IResource.class));
			}
		}
	}

	protected File getJarFile(IAdaptable adaptable) {
		JarPackageFragmentRoot jpfr = (JarPackageFragmentRoot) adaptable;
		File selected = jpfr.getPath().makeAbsolute().toFile();
		if (!selected.exists()) {
			File projectFile = new File(jpfr.getJavaProject().getProject().getLocation().toOSString());
			selected = new File(projectFile.getParent() + selected.toString());
		}
		return selected;
	}

	public static String getProjectPath(Object selected) {
		String path = "";

		path = ((IResource) selected).getProject().getLocation().toString();
		// String name = "";
		// name = ((IResource) selected).getProject().getName().toString();
		path=path.replace("/", File.separator);

		return path;
	}

	/**
	 * 判断build.xml是否存在
	 * 
	 * @param path
	 * @return
	 */
	private boolean isBuildFileExists(String path) {
		File file = new File(path + File.separator + "build.xml");
		return file.exists();
	}

	private void dobuildxml() {
		AutoWork autoWork = new AutoWork();
		String TAG = "build build.xml";
		autoWork.doProgress(TAG, CmdCommandGenerator.buildbuildxmlFileCommand(path), new OnCmdListener() {

			@Override
			public void onsuccess() {
				// TODO Auto-generated method stub

				dojar();
				reshFile();

			}

			@Override
			public void onfailed() {
				// TODO Auto-generated method stub

			}
		});
	}

	private void dojar() {
		File file = new File(path);
		String name = file.getName();
		String TAG = "build +" + name + ".jar";
		AutoWork autoWork = new AutoWork();
		autoWork.doProgressFilePath(TAG, CmdCommandGenerator.buildbuildJarCommand(path), path, new OnCmdListener() {

			@Override
			public void onsuccess() {
				// TODO Auto-generated method stub
				reshFile();
				doPushJartoPhone();
			}

			@Override
			public void onfailed() {
				// TODO Auto-generated method stub
			}
		});

	}

	private void doPushJartoPhone() {
		AutoWork autoWork = new AutoWork();
		File file = new File(path);
		String name = file.getName();
		String TAG = "push " + name + ".jar" + " to phone";
		autoWork.doProgress(TAG, CmdCommandGenerator.buildPushToAndroidCommand(path), new OnCmdListener() {

			@Override
			public void onsuccess() {
				// TODO Auto-generated method stub
				doStartJarPhone();
			}

			@Override
			public void onfailed() {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * 开始执行uiautomator
	 * 
	 * @param path
	 * @param packageclassname
	 */
	private void doStartJarPhone() {
		AutoWork autoWork = new AutoWork();
		File file = new File(path);
		String name = file.getName();
		String TAG = "adb shell uiautomator runtest " + name + ".jar";
		autoWork.doProgress(TAG, CmdCommandGenerator.buildstartAndroidCommand(path, packageclassname),
				new OnCmdListener() {

					@Override
					public void onsuccess() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onfailed() {
						// TODO Auto-generated method stub

					}
				});

	}

	private void reshFile() {
		if ((selected instanceof IResource)) {
			try {

				((IResource) selected).getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
				// ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE,
				// null);
				// ((IResource) selected).refreshLocal(IResource.DEPTH_INFINITE,
				// null);
				// ConsoleFactory.printToConsole("reshFile!");
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				ConsoleFactory.printToConsole("reshFile!" + e.toString());
				e.printStackTrace();
			}
		}
	}

}
