package com.group6.nova.dashboard.backend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Qualifier;

/// Annotation for qualifying the `orderLineImportJob` bean, used for validating order-related logic
/// in Spring's dependency injection.
///
/// @author Martin Kedmenec
@SuppressWarnings("DuplicateStringLiteralInspection")
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier("orderLineAndProductImportJob")
public @interface OrderLineAndProductImportJob {}
