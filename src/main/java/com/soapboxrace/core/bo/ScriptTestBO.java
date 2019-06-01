package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;

@Startup
@Singleton
public class ScriptTestBO {
    public class TestEventResultStructure {
        private int rank;
        private int sumOfJumpsDurationInMilliseconds;

        public int getRank() {
            return rank;
        }

        public int getSumOfJumpsDurationInMilliseconds() {
            return sumOfJumpsDurationInMilliseconds;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public void setSumOfJumpsDurationInMilliseconds(int sumOfJumpsDurationInMilliseconds) {
            this.sumOfJumpsDurationInMilliseconds = sumOfJumpsDurationInMilliseconds;
        }
    }

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private EventDAO eventDAO;

    @EJB
    private PersonaDAO personaDAO;

    @PostConstruct
    public void test() {
        achievementBO.updateAchievements(100L, "EVENT", new HashMap<String, Object>() {
            {
                put("event", eventDAO.findById(83));
                put("persona", personaDAO.findById(100L));
                put("eventResult", new TestEventResultStructure() {{
                    setRank(1);
                    setSumOfJumpsDurationInMilliseconds(220);
                }});
            }
        });
//        NashornScriptEngine nashornScriptEngine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
//        try {
//            Object val = nashornScriptEngine.eval("1 + 1");
//
//            nashornScriptEngine.getFactory();
//        }
//        catch (ScriptException e) {
//            e.printStackTrace();
//        }
    }
}
