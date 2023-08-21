package org.example.validation;



import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

public class EntityValidator {
    public static final ValidatorFactory validatorFactory = Validation.byDefaultProvider()
  .configure()
  .messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory();

    public static final Validator validator = validatorFactory.usingContext()
  .messageInterpolator(new ParameterMessageInterpolator()).getValidator();
}
