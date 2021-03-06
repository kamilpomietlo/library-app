package com.kamilpomietlo.libraryapp.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Password fields in the annotated class must be equal.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesImpl.class)
@Documented
public @interface PasswordMatches {
    String message() default "Passwords don't match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
