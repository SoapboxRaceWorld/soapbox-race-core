package com.soapboxrace.core.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;

public class InjectorFactory
{
    private static Injector injector;
    
    public static Injector getInjector()
    {
        if (injector == null)
            injector = Guice.createInjector(new ServletModule(), new XMPPModule());
        return injector;
    }
}
