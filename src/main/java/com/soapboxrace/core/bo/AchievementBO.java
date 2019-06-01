package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.AchievementDAO;
import com.soapboxrace.core.dao.PersonaAchievementDAO;
import com.soapboxrace.core.dao.PersonaAchievementRankDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.AchievementEntity;
import com.soapboxrace.core.jpa.AchievementRankEntity;
import com.soapboxrace.core.jpa.PersonaAchievementEntity;
import com.soapboxrace.core.jpa.PersonaAchievementRankEntity;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.script.Bindings;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;

@Stateless
public class AchievementBO {

    @EJB
    private PersonaAchievementDAO personaAchievementDAO;

    @EJB
    private PersonaAchievementRankDAO personaAchievementRankDAO;

    @EJB
    private AchievementDAO achievementDAO;

    @EJB
    private PersonaDAO personaDAO;

    private final ThreadLocal<NashornScriptEngine> scriptEngine = ThreadLocal.withInitial(() -> (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn"));

    /**
     * Update all appropriate achievements in the given category for the persona by the given ID
     *
     * @param personaId           The ID of the persona whose achievement progress must be updated
     * @param achievementCategory The category of achievements to evaluate
     * @param properties          Relevant contextual information for achievements.
     */
    public void updateAchievements(Long personaId, String achievementCategory, Map<String, Object> properties) {
        final Bindings bindings = scriptEngine.get().createBindings();
        bindings.putAll(properties);

        for (AchievementEntity achievementEntity : achievementDAO.findAllByCategory(achievementCategory)) {
            if (!achievementEntity.getAutoUpdate()) continue;

            try {
                Boolean shouldUpdate = (Boolean) scriptEngine.get().eval(achievementEntity.getUpdateTrigger(), bindings);
                if (shouldUpdate) {
                    System.out.println("DEBUG: update " + achievementEntity.getName() + " for " + personaId);
                    updateAchievement(personaId, achievementEntity, bindings);
                } else {
                    System.out.println("condition failed");
                    System.out.println(achievementEntity.getUpdateTrigger());

                    bindings.entrySet();
                }
            } catch (ScriptException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateAchievement(Long personaId, AchievementEntity achievementEntity, Bindings bindings) {
        // Locate persona-specific achievement data. Create it if it does not exist
        PersonaAchievementEntity personaAchievementEntity = personaAchievementDAO.findByPersonaIdAndAchievementId(
                personaId, achievementEntity.getId());

        if (personaAchievementEntity == null) {
            personaAchievementEntity = new PersonaAchievementEntity();
            personaAchievementEntity.setCanProgress(true);
            personaAchievementEntity.setCurrentValue(0L);
            personaAchievementEntity.setAchievementEntity(achievementEntity);
            personaAchievementEntity.setPersonaEntity(personaDAO.findById(personaId));
            personaAchievementDAO.insert(personaAchievementEntity);
        }

        // If no progression can be made, there's nothing to do.
        if (!personaAchievementEntity.isCanProgress()) {
            return;
        }

        // Determine the value to add to the achievement progress.
        try {
            Long cleanVal = 0L;
            Object rawVal = scriptEngine.get().eval(achievementEntity.getUpdateValue(), bindings);

            bindings.entrySet();

            if (rawVal instanceof Integer) {
                cleanVal = ((Integer) rawVal).longValue();
            } else if (rawVal instanceof Long) {
                cleanVal = (Long) rawVal;
            } else if (rawVal instanceof Float) {
                if (!Float.isNaN((Float)rawVal)) {
                    cleanVal = (long) Math.round((Float) rawVal);
                }
            } else if (rawVal instanceof Double) {
                if (!Double.isNaN((Double)rawVal)) {
                    cleanVal = Math.round((Double) rawVal);
                }
            }

            bindings.entrySet();

            int maxVal = achievementEntity.getRanks().stream()
                    .mapToInt(AchievementRankEntity::getThresholdValue)
                    .max()
                    .getAsInt();
            long newVal = Math.max(0, Math.min(maxVal, personaAchievementEntity.getCurrentValue() + cleanVal));

            for (int i = 0; i < achievementEntity.getRanks().size(); i++) {
                AchievementRankEntity previous = null;
                AchievementRankEntity current = achievementEntity.getRanks().get(i);
                PersonaAchievementRankEntity previousRank = null;
                PersonaAchievementRankEntity currentRank = personaAchievementRankDAO.findByPersonaIdAndAchievementRankId(personaId, current.getId());

                if (i > 0) {
                    previous = achievementEntity.getRanks().get(i - 1);
                    previousRank = personaAchievementRankDAO.findByPersonaIdAndAchievementRankId(personaId, previous.getId());

                    if (previousRank == null) {
                        previousRank = createPersonaAchievementRank(personaAchievementEntity, previous);
                    }
                }

                if (currentRank == null) {
                    currentRank = createPersonaAchievementRank(personaAchievementEntity, current);
                }

                long threshold = current.getThresholdValue();

                if (currentRank.getState().equals("Completed") || currentRank.getState().equals("RewardWaiting"))
                    continue;

                if (newVal >= threshold && newVal != personaAchievementEntity.getCurrentValue()) {
                    currentRank.setState("RewardWaiting");
                    currentRank.setAchievedOn(LocalDateTime.now());
                    personaAchievementRankDAO.update(currentRank);
                } else if (previous != null && previousRank.getState().equals("InProgress")) {
                    currentRank.setState("Locked");
                    personaAchievementRankDAO.update(currentRank);
                    break;
                } else if (newVal > 0 && newVal < threshold) {
                    currentRank.setState("InProgress");
                    personaAchievementRankDAO.update(currentRank);
                    break;
                }
            }

            personaAchievementEntity.setCurrentValue(newVal);
            personaAchievementEntity.setCanProgress(newVal < maxVal);
            personaAchievementDAO.update(personaAchievementEntity);
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
    }

    private PersonaAchievementRankEntity createPersonaAchievementRank(PersonaAchievementEntity personaAchievementEntity, AchievementRankEntity achievementRankEntity) {
        PersonaAchievementRankEntity rankEntity = new PersonaAchievementRankEntity();
        rankEntity.setState("Locked");
        rankEntity.setPersonaAchievementEntity(personaAchievementEntity);
        rankEntity.setAchievementRankEntity(achievementRankEntity);
        personaAchievementRankDAO.insert(rankEntity);
        return rankEntity;
    }
}
