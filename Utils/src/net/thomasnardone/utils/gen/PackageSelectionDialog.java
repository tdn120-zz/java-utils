package net.thomasnardone.utils.gen;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.UIManager;

@SuppressWarnings("serial")
public class PackageSelectionDialog extends ReflectionSelectionDialog {

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		final String packageName = new PackageSelectionDialog().select();
		System.out.println(packageName);
	}

	public PackageSelectionDialog() {
		super("Select Package...");
	}

	@Override
	protected List<String> findItems(final File root, final String packageName) {
		final String name = root.getName();
		if (root.isDirectory()) {
			List<String> packages = new LinkedList<String>();
			String newPackage = updatePackage(packageName, name);

			boolean addRoot = false;
			for (File file : root.listFiles()) {
				if (file.isDirectory()) {
					packages.addAll(findItems(file, newPackage));
				} else {
					addRoot = true;
				}
			}
			if (addRoot) {
				packages.add(newPackage);
			}
			return packages;
		} else {
			return Collections.emptyList();
		}
	}
}
