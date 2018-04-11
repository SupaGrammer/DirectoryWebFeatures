package io.pivotal.workshop.directory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
//Returns an array of the kinds of elements an annotation type
//can be applied to.
@Retention(RetentionPolicy.RUNTIME)
//determines at what point annotation should be discarded.
public @interface Audit {
	Auditor value() default Auditor.NOTHING;
}
