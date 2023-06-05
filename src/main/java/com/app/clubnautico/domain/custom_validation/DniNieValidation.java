package com.app.clubnautico.domain.custom_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DniNieValidation implements ConstraintValidator<ValidacionIdentidad, String> {

    @Override
    public void initialize(ValidacionIdentidad constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String dniNie, ConstraintValidatorContext constraintValidatorContext) {
        //expresion regular que permita solo DniNIe
        String regexID = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,9}[A-Za-z]$";

        if (dniNie == null) {
            return true;// permite la validacion nula
        }
        return dniNie.matches(regexID);
    }
}
