package com.mycompany.springboot.demo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * An interface that allows to create a custom annotation.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Constraint(validatedBy = EmailValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidEmail {
	String message() default "Invalid email";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
