package com.sysco.sampleService.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {InputValidator.CaloriesValidator.class})
public @interface InputValidator {

    String message() default "Can be only numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    class CaloriesValidator implements ConstraintValidator<InputValidator, String> {
        private static final Pattern VALID_PATTERN = Pattern.compile("^[0-9]+$");

        @Override
        public boolean isValid(String calories, ConstraintValidatorContext context) {
            if (calories == null) {
                return false;
            }
            return VALID_PATTERN.matcher(calories).matches();
        }
    }
}