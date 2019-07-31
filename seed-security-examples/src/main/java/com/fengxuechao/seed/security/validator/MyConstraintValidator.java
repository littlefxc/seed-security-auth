package com.fengxuechao.seed.security.validator;

import com.fengxuechao.seed.security.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fengxuechao
 * @date 2019-07-25
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, String> {

    @Autowired
    private HelloService service;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("my validator init ");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        service.greeting("fxc ");
        System.out.println(value);
        return true;
    }
}
