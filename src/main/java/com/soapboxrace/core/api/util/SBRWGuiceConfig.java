package com.soapboxrace.core.api.util;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.soapboxrace.core.inject.InjectorFactory;

public class SBRWGuiceConfig extends GuiceServletContextListener
{
    @Override
    protected Injector getInjector()
    {
        return InjectorFactory.getInjector();
    }
}