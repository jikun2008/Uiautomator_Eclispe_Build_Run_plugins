package com.no.uiautomator.actions;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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
public class SettingAction implements IObjectActionDelegate {
	private Object selected = null;
	private Class<?> selectedClass = null;
	private static final String START_CMD = "cmd /c ";

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		if (this.selected == null) {
			MessageDialog.openInformation(new Shell(), "Quick Cmd", "Unable to cmd " + this.selectedClass.getName());
			return;
		}
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
					filePath = directory.getParentFile().getAbsolutePath();
				}

				// 截取当前文件的开头盘符
				startPath = filePath.substring(0, 2);
			}
			showSettingDilaog(filePath);

		} catch (Exception e) {
			System.err.println(e.toString());
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

	private void showSettingDilaog(String path) {

		IPreferenceStore store = QuickUiautomatorActivator.getDefault().getPreferenceStore();
		String info = store.getString(WorkFlowPreferenceConstants.ID);
		InputDialog inputDlg = new InputDialog(new Shell(), "enter", "please enter your ID", info,
				new IInputValidator() {
					public String isValid(String newText) {
						int i;
						try {
							i = Integer.parseInt(newText);
						} catch (NumberFormatException e) {
							return "Inter Only!";
						}

						if (i < 0) {
							return "too young!";
						}

						if (i > 150) {
							return "too old!";
						}

						return null;
					}
				});

		if (inputDlg.open() == Window.OK) {
			System.out.println(inputDlg.getValue());
			store.setValue(WorkFlowPreferenceConstants.ID, inputDlg.getValue());
			ConsoleFactory.printToConsole(CmdCommandGenerator.buildbuildxmlFileCommand(path));
			// store.putValue("JIKUNLOVEXU", inputDlg.getValue());
		}
	}

}
