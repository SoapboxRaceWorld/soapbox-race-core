package com.soapboxrace.jaxb.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface XsiSchemaLocation {

	public String schemaLocation();

}
