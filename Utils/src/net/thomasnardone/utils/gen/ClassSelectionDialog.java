package net.thomasnardone.utils.gen;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.UIManager;

@SuppressWarnings("serial")
public class ClassSelectionDialog extends ReflectionSelectionDialog {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		final String className = new ClassSelectionDialog(Object.class).select();
		System.out.println(className);
	}

	private final Class<?>	baseClass;

	public ClassSelectionDialog(final Class<?> baseClass) {
		this(baseClass, "Select Class...");
	}

	public ClassSelectionDialog(final Class<?> baseClass, final String title) {
		super(title);
		this.baseClass = baseClass;
	}

	@Override
	protected List<String> findItems(final File root, String packageName) {
		final String name = root.getName();
		if (root.isDirectory()) {
			List<String> classes = new LinkedList<String>();
			packageName = updatePackage(packageName, name);

			for (File file : root.listFiles()) {
				classes.addAll(findItems(file, packageName));
			}
			return classes;
		} else if (root.getName().endsWith(".class")) {
			String className = packageName + "." + root.getName().replace(".class", "");
			try {
				final Class<?> clazz = Class.forName(className);
				if (baseClass.isAssignableFrom(clazz)
						&& !(clazz.isMemberClass() || clazz.isAnonymousClass() || Modifier.isAbstract(clazz.getModifiers()))) {
					return Arrays.asList(className);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ExceptionInInitializerError e) {
				e.printStackTrace();
			}
		}
		return Collections.emptyList();
	}

}