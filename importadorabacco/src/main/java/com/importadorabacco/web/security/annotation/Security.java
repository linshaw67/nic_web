package com.importadorabacco.web.security.annotation;

import com.importadorabacco.web.security.constant.Level;

import java.lang.annotation.*;

/**
 * security annotation
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Security {
    /**
     * define the role type that need to execute the method
     */
    int value() default Level.MEMBER;
}
