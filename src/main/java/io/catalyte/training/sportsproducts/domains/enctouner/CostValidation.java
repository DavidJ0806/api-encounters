package io.catalyte.training.sportsproducts.domains.enctouner;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom validator for cost
 */
public class CostValidation implements ConstraintValidator<Cost, BigDecimal> {

  @Override
  public void initialize(Cost cost) {
  }

  @Override
  public boolean isValid(BigDecimal bigDecimal,
      ConstraintValidatorContext constraintValidatorContext) {
    return bigDecimal != null && bigDecimal.toString().matches("^\\d+\\.\\d{2}$");
  }
}