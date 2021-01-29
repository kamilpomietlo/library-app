package com.kamilpomietlo.libraryapp.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element value must not be present in database.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailNotInUseImpl.class)
@Documented
public @interface EmailNotInUse {
    String message() default "Email is already in use!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
