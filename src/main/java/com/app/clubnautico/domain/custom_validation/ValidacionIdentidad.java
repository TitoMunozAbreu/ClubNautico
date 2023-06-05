package com.app.clubnautico.domain.custom_validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DniNieValidation.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacionIdentidad {
    public String message() default "Debes ingresar un documento valido: DNI 12345678A | NIE X1234567D";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
