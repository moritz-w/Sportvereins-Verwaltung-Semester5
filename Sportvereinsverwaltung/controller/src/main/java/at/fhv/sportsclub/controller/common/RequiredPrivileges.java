package at.fhv.sportsclub.controller.common;

import at.fhv.sportsclub.model.security.AccessLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
      Created: 17.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredPrivileges {
    boolean authenticationRequired() default true;
    String category();
    AccessLevel[] accessLevel();
}
