package net.thomasnardone.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UIField {
	Type value() default Type.Text;

	int width() default 100;

	public enum Type {
		Combo, Date, EditText, Text
	}
}