module world.soapboxrace.server {
    // Core
    exports com.soapboxrace.core.api;
    exports com.soapboxrace.core.api.util;
    exports com.soapboxrace.core.bo;
    exports com.soapboxrace.core.bo.util;
    exports com.soapboxrace.core.dao;
    exports com.soapboxrace.core.dao.util;
    exports com.soapboxrace.core.engine;
    exports com.soapboxrace.core.exceptions;
    exports com.soapboxrace.core.filter;
    exports com.soapboxrace.core.jpa;
    exports com.soapboxrace.core.jpa.convert;
    exports com.soapboxrace.core.jpa.util;
    exports com.soapboxrace.core.vo;
    exports com.soapboxrace.core.xmpp;

    // JAXB
    exports com.soapboxrace.jaxb.annotation;
    exports com.soapboxrace.jaxb.http;
    exports com.soapboxrace.jaxb.login;
    exports com.soapboxrace.jaxb.util;
    exports com.soapboxrace.jaxb.xmpp;

    // UDP
    exports com.soapboxrace.udp;
}