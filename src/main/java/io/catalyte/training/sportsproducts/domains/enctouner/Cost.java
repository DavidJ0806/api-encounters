package io.catalyte.training.sportsproducts.domains.enctouner;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Constraint annotation for cost
 */
@Documented
@Constraint(validatedBy = CostValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cost {

  String message() default "Cost requires 2 decimal places and a non-negative number, ex. '0.00', '12.99'.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}