/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.PersonaDAO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class ScriptTestBO {
    @EJB
    private AchievementBO achievementBO;
    @EJB
    private ItemRewardBO itemRewardBO;
    @EJB
    private EventDAO eventDAO;
    @EJB
    private PersonaDAO personaDAO;

    @PostConstruct
    public void test() {
//        AchievementsPacket achievementsPacket = achievementBO.loadAll(100L);
//        HashMap<String, Object> properties = new HashMap<String, Object>() {
//            {
//                put("event", eventDAO.findById(83));
//                put("persona", personaDAO.findById(100L));
//                put("eventResult", new TestEventResultStructure() {{
//                    setRank(1);
//                    setSumOfJumpsDurationInMilliseconds(220);
//                }});
//                put("eventContext", new AchievementEventContext(EventMode.CIRCUIT, new ArbitrationPacket() {{
//                    setRank(1);
//                }}, new EventSessionEntity() {{
//                    setLobby(new LobbyEntity());
//                    setEvent(new EventEntity());
//                }}));
//                put("commerceCtx", new AchievementCommerceContext(new CarClassesEntity(),
//                AchievementCommerceContext.CommerceType.CAR_PURCHASE));
//                put("progression", new AchievementProgressionContext(1000, 500, 42, true, true));
//            }
//        };
//
//        achievementBO.updateAchievements(100L, "EVENT", properties);
//        achievementBO.updateAchievements(100L, "COMMERCE", properties);
    }

    public class TestEventResultStructure {
        private int rank;
        private int sumOfJumpsDurationInMilliseconds;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getSumOfJumpsDurationInMilliseconds() {
            return sumOfJumpsDurationInMilliseconds;
        }

        public void setSumOfJumpsDurationInMilliseconds(int sumOfJumpsDurationInMilliseconds) {
            this.sumOfJumpsDurationInMilliseconds = sumOfJumpsDurationInMilliseconds;
        }
    }
}
