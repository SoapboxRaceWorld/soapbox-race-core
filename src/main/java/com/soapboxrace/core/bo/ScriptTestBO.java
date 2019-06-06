package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.AchievementCommerceContext;
import com.soapboxrace.core.bo.util.AchievementEventContext;
import com.soapboxrace.core.bo.util.AchievementProgressionContext;
import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.CarClassesEntity;
import com.soapboxrace.core.jpa.EventMode;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.AchievementRewards;
import com.soapboxrace.jaxb.http.AchievementsPacket;
import com.soapboxrace.jaxb.http.ArbitrationPacket;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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
    private AchievementRewardBO achievementRewardBO;

    @EJB
    private EventDAO eventDAO;

    @EJB
    private PersonaDAO personaDAO;

    @PostConstruct
    public void test() {
        AchievementsPacket achievementsPacket = achievementBO.loadAll(100L);
        HashMap<String, Object> properties = new HashMap<String, Object>() {
            {
                put("event", eventDAO.findById(83));
                put("persona", personaDAO.findById(100L));
                put("eventResult", new TestEventResultStructure() {{
                    setRank(1);
                    setSumOfJumpsDurationInMilliseconds(220);
                }});
                put("eventContext", new AchievementEventContext(EventMode.CIRCUIT, new ArbitrationPacket(), new EventSessionEntity()));
                put("commerceCtx", new AchievementCommerceContext(new CarClassesEntity(), AchievementCommerceContext.CommerceType.CAR_PURCHASE));
                put("progression", new AchievementProgressionContext(1000, 500, 42, true, true));
            }
        };

        AchievementRewards achievementRewards = achievementRewardBO.redeemRewards(100L,3L);

        achievementBO.updateAchievements(100L, "EVENTE", properties);
//        achievementBO.updateAchievements(100L, "COMMERCE", properties);
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
