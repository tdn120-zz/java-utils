package net.thomasnardone.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UIField {
	boolean column() default true;

	String label() default "";

	Type value() default Type.Text;

	int width() default -1;

	public enum Type {
		Combo, Date, EditText, Text
	}
}
