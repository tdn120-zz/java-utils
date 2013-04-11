package net.thomasnardone.utils.gen;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import net.thomasnardone.annotations.UIField;

public abstract class AbstractClassGenerator extends AbstractGenerator {
	protected final Class<?>	clazz;

	public AbstractClassGenerator(final Class<?> clazz) {
		this.clazz = clazz;
	}

	protected List<Field> getDeclaredFields() {
		return getDeclaredFields(false);
	}

	protected List<Field> getDeclaredFields(final boolean columnsOnly) {
		List<Field> fields = new LinkedList<Field>();
		for (Field field : clazz.getDeclaredFields()) {
			if (!(Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))) {
				final UIField annotation = field.getAnnotation(UIField.class);
				if (columnsOnly && (annotation != null) && !annotation.column()) {
					continue;
				}
				fields.add(field);
			}
		}
		return fields;
	}
}
