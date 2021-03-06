package de.strasser.peter.hexagonal.application.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = SecurePassword.PasswordValidator.class)
public @interface SecurePassword {

  String message() default
          """
                  # a digit must occur at least once
                  # a lower case letter must occur at least once
                  # an upper case letter must occur at least once
                  # a special character must occur at least once ( one of !@#$%^&*(),.?":{}|<>)\s
                  # no whitespace allowed in the entire string
                  # at least eight places""";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Component
  class PasswordValidator implements ConstraintValidator<SecurePassword, String> {
    private static final String DEFAULT_PASSWORD_REQUIREMENTS =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=\\S+$).{8,}$";

    @Value("${password.format:" + DEFAULT_PASSWORD_REQUIREMENTS + "}")
    private String pwFormat;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
      return s != null && s.matches(pwFormat);
    }
  }
}
