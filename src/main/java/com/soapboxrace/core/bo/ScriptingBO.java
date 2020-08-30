package com.soapboxrace.core.bo;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.Map;

/**
 * ScriptingBO is a bean that allows for the execution of JavaScript code using the Nashorn engine.
 */
@Singleton
@Startup
public class ScriptingBO {

    @Inject
    private Logger logger;

    private NashornScriptEngine scriptEngine;

    @PostConstruct
    public void init() {
        scriptEngine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        logger.info("Initialized JavaScript engine");
    }

    /**
     * Evaluates JavaScript code and returns the result.
     * This can be invoked concurrently, hence the specification of {@code LockType.READ}.
     *
     * @param script   The JavaScript code to evaluate.
     * @param bindings The bindings to pass to the script.
     * @return The result of the evaluation.
     * @throws ScriptException if an error occurs.
     */
    @Lock(LockType.READ)
    public Object eval(String script, Map<String, Object> bindings) throws ScriptException {
        return scriptEngine.eval(script, new SimpleBindings(bindings));
    }
}
