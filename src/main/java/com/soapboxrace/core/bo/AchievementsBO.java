package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.soapboxrace.core.bo.util.TimeConverter;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.xmpp.AchievementAwarded;
import com.soapboxrace.jaxb.xmpp.AchievementProgress;
import com.soapboxrace.jaxb.xmpp.AchievementsAwarded;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeAchievementsAwarded;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class AchievementsBO
{
    @EJB
    private BasketBO basketBO;

    @EJB
    private ProductBO productBO;

    @EJB
    private InventoryBO inventoryBO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private AchievementDAO achievementDAO;

    @EJB
    private BadgeDefinitionDAO badgeDefinitionDAO;

    @EJB
    private PersonaAchievementDAO personaAchievementDAO;

    @EJB
    private PersonaAchievementRankDAO personaAchievementRankDAO;

    @EJB
    private AchievementRewardDAO achievementRewardDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    public AchievementsPacket loadall(Long personaId)
    {
        PersonaEntity personaEntity = personaDAO.findById(personaId);
        AchievementsPacket packet = new AchievementsPacket();
        ArrayOfAchievementDefinitionPacket achievementPackets = new ArrayOfAchievementDefinitionPacket();
        ArrayOfBadgeDefinitionPacket badgePackets = new ArrayOfBadgeDefinitionPacket();

        List<AchievementDefinitionEntity> achievements = achievementDAO.getAll();
        List<BadgeDefinitionEntity> badges = badgeDefinitionDAO.getAll();

        for (AchievementDefinitionEntity achievement : achievements)
        {
            AchievementDefinitionPacket achPacket = new AchievementDefinitionPacket();
            ArrayOfAchievementRankPacket arrayOfRanks = new ArrayOfAchievementRankPacket();

            achPacket.setAchievementDefinitionId(achievement.getId().intValue());
            achPacket.setStatConversion(StatConversion.fromValue(achievement.getStatConversion()));
            achPacket.setBadgeDefinitionId(achievement.getBadgeDefinition().getId().intValue());
            achPacket.setProgressText(achievement.getProgressText());
            achPacket.setIsVisible(true);

            PersonaAchievementEntity personaAchievement = personaAchievementDAO.getForPersonaAchievement(personaEntity, achievement);

            if (personaAchievement != null)
            {
                achPacket.setCurrentValue(personaAchievement.getCurrentValue());
                achPacket.setCanProgress(personaAchievement.isCanProgress());
            } else
            {
                achPacket.setCurrentValue(0);
                achPacket.setCanProgress(true);
            }

            for (AchievementRankEntity rank : achievement.getRanks())
            {
                PersonaAchievementRankEntity personaRank = personaAchievementRankDAO.findByPersonaAchievement(personaEntity, achievement, rank);
                AchievementRankPacket rankPacket = new AchievementRankPacket();
                rankPacket.setPoints(rank.getPoints());
                rankPacket.setRank(rank.getRank());
                rankPacket.setAchievementRankId(rank.getId().intValue());
                rankPacket.setIsRare(rank.isRare());
                rankPacket.setRarity(0.0f);
                rankPacket.setRewardDescription(rank.getRewardDescription());
                rankPacket.setRewardType(rank.getRewardType());
                rankPacket.setRewardVisualStyle(rank.getRewardVisualStyle());
                rankPacket.setState(AchievementState.LOCKED);
                rankPacket.setThresholdValue(rank.getThresholdValue());
//                rankPacket.setAchievedOn(GregorianCalendar.from(ZonedDateTime.from(Instant.parse("0001-01-01T00:00:00"))));

                if (personaRank != null)
                {
                    rankPacket.setState(AchievementState.fromValue(personaRank.getState()));

                    if (personaRank.getState().equals("Completed") || personaRank.getState().equals("RewardWaiting"))
                    {
                        LocalDateTime achievedOn = LocalDateTime.parse(
                                personaRank.getAchievedOn().replace("T", " "),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                        Instant achievedOn = Instant.parse(personaRank.getAchievedOn());
                        GregorianCalendar calendar = new GregorianCalendar();
                        calendar.setTimeInMillis(achievedOn.toInstant(ZoneOffset.UTC).toEpochMilli());

                        XMLGregorianCalendar xmlCalendar = TimeConverter.generateGregorianCalendar(calendar);
                        rankPacket.setAchievedOn(xmlCalendar);
                    }
                } else
                {
                    rankPacket.setState(AchievementState.LOCKED);
                }

                arrayOfRanks.getAchievementRankPacket().add(rankPacket);
            }

            achPacket.setAchievementRanks(arrayOfRanks);

            achievementPackets.getAchievementDefinitionPacket().add(achPacket);
        }

        for (BadgeDefinitionEntity badge : badges)
        {
            BadgeDefinitionPacket badgePacket = new BadgeDefinitionPacket();
            badgePacket.setBackground(badge.getBackground());
            badgePacket.setBorder(badge.getBorder());
            badgePacket.setDescription(badge.getDescription());
            badgePacket.setIcon(badge.getIcon());
            badgePacket.setName(badge.getName());
            badgePacket.setBadgeDefinitionId(badge.getId().intValue());

            badgePackets.getBadgeDefinitionPacket().add(badgePacket);
        }

        packet.setDefinitions(achievementPackets);
        packet.setBadges(badgePackets);
        packet.setPersonaId(personaId);

        return packet;
    }

    public void update(PersonaEntity personaEntity, AchievementDefinitionEntity achievement, Long value) {
        update(personaEntity, achievement, value, true);
    }

    public void update(PersonaEntity personaEntity, AchievementDefinitionEntity achievement, Long value, boolean addToCurrent)
    {
        if (achievement == null || personaEntity == null)
        {
            System.err.println("Unknown achievement or persona!");
            return;
        }

        PersonaAchievementEntity personaAchievementEntity = personaAchievementDAO.getForPersonaAchievement(personaEntity, achievement);

        if (personaAchievementEntity == null)
        {
            personaAchievementEntity = createPersonaAchievement(personaEntity, achievement);
        }

        if (!personaAchievementEntity.isCanProgress()) {
            return;
        }
        
        Long maxValue = achievement.getMaximumValue(),
                currentValue = personaAchievementEntity.getCurrentValue();

        if (addToCurrent)
            value += currentValue;

        if (value > maxValue) value = maxValue;

        personaAchievementEntity.setCurrentValue(value);
        personaAchievementDAO.update(personaAchievementEntity);

        AchievementRankEntity previous = null,
                firstRank = achievement.getRanks().get(0);
        List<PersonaAchievementRankEntity> broadcastRanks = new ArrayList<>();

        for (AchievementRankEntity rank : achievement.getRanks())
        {
            PersonaAchievementRankEntity personaRank = personaAchievementRankDAO.findByPersonaAchievement(personaEntity, achievement, rank);
            PersonaAchievementRankEntity previousRank = null;

            if (personaRank == null)
            {
                personaRank = createPersonaAchievementRank(personaEntity, achievement, rank);
            }

            if (previous != null)
            {
                previousRank = personaAchievementRankDAO.findByPersonaAchievement(personaEntity, achievement, previous);

                if (previousRank == null)
                {
                    previousRank = createPersonaAchievementRank(personaEntity, achievement, previous);
                }
            }

            String state = "";
            boolean complete = false;
            boolean stop = false;

            Long threshold = rank.getThresholdValue();
            
            if (personaRank.getState().equalsIgnoreCase("RewardWaiting") || personaRank.getState().equalsIgnoreCase("Completed")) {
                continue;
            }

            if (value >= threshold && !currentValue.equals(value))
            {
                state = "RewardWaiting";
                complete = true;
            } else
            {
                if (value == 0)
                {
                    state = "Locked";
                    stop = true;
                } else if (value > 0 && value < firstRank.getThresholdValue())
                {
                    state = "InProgress";
                    stop = true;
                } else if (previous != null && previousRank.getState().equals("InProgress"))
                {
                    state = "Locked";
                    stop = true;
                } else if (value > 0 && value < threshold)
                {
                    state = "InProgress";
                    stop = true;
                }
            }

            state = state.trim();

            if (state.isEmpty())
            {
                return;
            }

            if (complete)
            {
                String timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                timeString = timeString.replace(" ", "T");
                personaRank.setAchievedOn(timeString);
            } else
            {
                personaRank.setAchievedOn("0001-01-01T00:00:00");
            }

            // if reward is now waiting, add
            if (state.equalsIgnoreCase("RewardWaiting")
                    && !personaRank.getState().equalsIgnoreCase("RewardWaiting")
                    && !personaRank.getState().equalsIgnoreCase("Completed"))
            {
                personaEntity.setScore(personaEntity.getScore() + rank.getPoints());
                personaDAO.update(personaEntity);

                broadcastRanks.add(personaRank);
                
                update(personaEntity, achievementDAO.findByName("achievement_ACH_EARN_DRIVERSCORE"), (long) personaEntity.getScore(), false);
            } else if (state.equalsIgnoreCase("InProgress"))
            {
                broadcastRanks.add(personaRank);
            }

            personaRank.setState(state);

            personaAchievementRankDAO.update(personaRank);
//            DaoFactory.getPersonaAchievementRankDao().save(personaRank);

            if (stop)
                break;

            personaAchievementEntity.setCanProgress(!value.equals(maxValue));
            personaAchievementDAO.update(personaAchievementEntity);

            previous = rank;
        }

        broadcastAchievement(
                personaEntity,
                personaAchievementEntity,
                broadcastRanks,
                personaEntity.getScore()
        );
    }

    public void broadcastAchievement(PersonaEntity personaEntity,
                                     PersonaAchievementEntity personaAchievementEntity,
                                     List<PersonaAchievementRankEntity> ranks,
                                     int score)
    {
        XMPP_ResponseTypeAchievementsAwarded achievementsAwardedResponse = new XMPP_ResponseTypeAchievementsAwarded();
        AchievementsAwarded achievementsAwarded = new AchievementsAwarded();

        List<AchievementProgress> progressList = new ArrayList<>();
        List<AchievementAwarded> awardedList = new ArrayList<>();

        AchievementDefinitionEntity achievement = personaAchievementEntity.getAchievement();

        if (!ranks.isEmpty())
        {
            for (PersonaAchievementRankEntity personaRank : ranks)
            {
                if (personaRank.getState().equals("RewardWaiting"))
                {
                    AchievementRankEntity baseRank = personaRank.getRank();

                    AchievementAwarded achievementAwarded = new AchievementAwarded();
                    achievementAwarded.setAchievementDefinitionId(achievement.getId());
                    achievementAwarded.setAchievedOn(personaRank.getAchievedOn());
                    achievementAwarded.setAchievementRankId(baseRank.getId());
                    achievementAwarded.setDescription(achievement.getBadgeDefinition().getDescription());
                    achievementAwarded.setIcon(achievement.getBadgeDefinition().getIcon());
                    achievementAwarded.setRare(false);
                    achievementAwarded.setName(achievement.getBadgeDefinition().getName());
                    achievementAwarded.setPoints(baseRank.getPoints());
                    achievementAwarded.setRarity(0f);

                    awardedList.add(achievementAwarded);
                } else if (personaRank.getState().equals("InProgress"))
                {
                    boolean alreadyHas = progressList.stream().anyMatch(ap -> ap.getAchievementDefinitionId().equals(achievement.getId()));

                    if (alreadyHas)
                    {
                        break;
                    }

                    AchievementProgress achievementProgress = new AchievementProgress();
                    achievementProgress.setAchievementDefinitionId(achievement.getId());
                    achievementProgress.setCurrentValue(personaAchievementEntity.getCurrentValue());

                    progressList.add(achievementProgress);
                }
            }
        }

        achievementsAwarded.setAchievements(awardedList.stream().distinct().collect(Collectors.toList()));
        achievementsAwarded.setProgressed(progressList.stream().distinct().collect(Collectors.toList()));
        achievementsAwarded.setPersonaId(personaEntity.getPersonaId());
        achievementsAwarded.setScore(score);

        achievementsAwardedResponse.setAchievementsAwarded(achievementsAwarded);

        openFireSoapBoxCli.send(achievementsAwardedResponse, personaEntity.getPersonaId());
    }

    public AchievementRewards redeemReward(PersonaEntity persona, AchievementRankEntity rank)
    {
        if (persona == null || rank == null)
            return new AchievementRewards();
        AchievementDefinitionEntity achievement = rank.getAchievementDefinition();
        PersonaAchievementRankEntity personaRank = personaAchievementRankDAO.findByPersonaAchievement(persona, achievement, rank);

        if (personaRank == null)
            return new AchievementRewards();
        if (!(personaRank.getState().equalsIgnoreCase("RewardWaiting")))
            return new AchievementRewards();

        AchievementRewards achievementRewards = new AchievementRewards();
        achievementRewards.setAchievementRankId(rank.getId());
        achievementRewards.setVisualStyle(rank.getRewardVisualStyle());

        ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();

        final List<CommerceItemTrans> commerceItems = new ArrayList<>();

        AchievementRewardEntity rewardEntity = achievementRewardDAO.findByDescription(rank.getRewardDescription());

        if (rewardEntity != null)
        {
            final String rewardEntityValue = rewardEntity.getValue();

            if (rewardEntityValue.equalsIgnoreCase("CarPrizePack"))
            {
                ProductEntity randomCar = productDAO.getRandomDrop("NFSW_NA_EP_PRESET_RIDES_ALL_Category");

                basketBO.addCar(randomCar.getProductId(), persona);
            } else if (rewardEntityValue.equalsIgnoreCase("CardPack"))
            {
                List<ProductEntity> products = new ArrayList<>();

                for (int i = 0; i < 5; i++)
                {
                    ProductEntity product;

                    do
                    {
                        product = productDAO.getRandomDrop();
                    } while (products.contains(product));

                    products.add(product);
                    CommerceItemTrans item = new CommerceItemTrans();
                    item.setHash(product.getHash());
                    item.setTitle(product.getProductTitle());
                    commerceItems.add(item);
                }
            } else if (rewardEntityValue.startsWith("Car"))
            {
                String[] parts = rewardEntityValue.split(Pattern.quote("|"));

                if (parts.length == 2)
                {
                    String carId = parts[1];
                    basketBO.addCar(carId, persona);
                    ProductEntity productEntity = productDAO.findByProductId(carId);
                    commerceItems.add(new CommerceItemTrans()
                    {
                        {
                            setTitle(productEntity.getProductTitle());
                            setHash(productEntity.getHash());
                        }
                    });
                }
            } else if (rewardEntityValue.startsWith("RatedPerformancePart"))
            {
                String[] parts = rewardEntityValue.split(Pattern.quote("|"));

                if (parts.length == 3)
                {
                    int rating = Integer.parseInt(parts[1]);
                    String type = parts[2].toUpperCase();
                    List<ProductEntity> performanceParts = productBO.productsInCategory("NFSW_NA_EP_PERFORMANCEPARTS", "PERFORMANCEPART", persona.getPersonaId());

                    switch (type)
                    {
                        case "ENGINE":
                        {
                            List<ProductEntity> engines = performanceParts.stream()
                                    .filter(p -> p.getIcon().contains("engine"))
                                    .filter(p -> p.getIcon().contains("r" + rating))
                                    .collect(Collectors.toList());
                            int productIndex = new Random().nextInt(engines.size());

                            inventoryBO.addDroppedItem(
                                    engines.get(productIndex),
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(engines.get(productIndex).getProductTitle());
                                    setHash(engines.get(productIndex).getHash());
                                }
                            });

                            break;
                        }
                        case "FORCED_INDUCTION":
                        {
                            List<ProductEntity> forcedInductions = performanceParts.stream()
                                    .filter(p -> p.getIcon().contains("forcedind"))
                                    .filter(p -> p.getIcon().contains("r" + rating))
                                    .collect(Collectors.toList());
                            int productIndex = new Random().nextInt(forcedInductions.size());

                            inventoryBO.addDroppedItem(
                                    forcedInductions.get(productIndex),
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(forcedInductions.get(productIndex).getProductTitle());
                                    setHash(forcedInductions.get(productIndex).getHash());
                                }
                            });

                            break;
                        }
                        case "TRANSMISSION":
                        {
                            List<ProductEntity> transmissions = performanceParts.stream()
                                    .filter(p -> p.getIcon().contains("trans"))
                                    .filter(p -> p.getIcon().contains("r" + rating))
                                    .collect(Collectors.toList());
                            int productIndex = new Random().nextInt(transmissions.size());

                            inventoryBO.addDroppedItem(
                                    transmissions.get(productIndex),
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(transmissions.get(productIndex).getProductTitle());
                                    setHash(transmissions.get(productIndex).getHash());
                                }
                            });

                            break;
                        }
                        case "SUSPENSION":
                        {
                            List<ProductEntity> suspensions = performanceParts.stream()
                                    .filter(p -> p.getIcon().contains("susp"))
                                    .filter(p -> p.getIcon().contains("r" + rating))
                                    .collect(Collectors.toList());
                            int productIndex = new Random().nextInt(suspensions.size());

                            inventoryBO.addDroppedItem(
                                    suspensions.get(productIndex),
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(suspensions.get(productIndex).getProductTitle());
                                    setHash(suspensions.get(productIndex).getHash());
                                }
                            });
                        }
                        case "BRAKES":
                        {
                            List<ProductEntity> brakes = performanceParts.stream()
                                    .filter(p -> p.getIcon().contains("brake"))
                                    .filter(p -> p.getIcon().contains("r" + rating))
                                    .collect(Collectors.toList());
                            int productIndex = new Random().nextInt(brakes.size());

                            inventoryBO.addDroppedItem(
                                    brakes.get(productIndex),
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(brakes.get(productIndex).getProductTitle());
                                    setHash(brakes.get(productIndex).getHash());
                                }
                            });
                        }
                        case "TIRES":
                        {
                            List<ProductEntity> tires = performanceParts.stream()
                                    .filter(p -> p.getIcon().contains("tire"))
                                    .filter(p -> p.getIcon().contains("r" + rating))
                                    .collect(Collectors.toList());
                            int productIndex = new Random().nextInt(tires.size());

                            inventoryBO.addDroppedItem(
                                    tires.get(productIndex),
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(tires.get(productIndex).getProductTitle());
                                    setHash(tires.get(productIndex).getHash());
                                }
                            });
                        }
                    }
                }
            } else if (rewardEntityValue.startsWith("SkillPack"))
            {
                String[] parts = rewardEntityValue.split(Pattern.quote("|"));

                if (parts.length == 2)
                {
                    String type = parts[1].toUpperCase();
                    List<ProductEntity> skillMods = productBO.productsInCategory("NFSW_NA_EP_SKILLMODPARTS", "SKILLMODPART", persona.getPersonaId());
                    List<ProductEntity> productsToUse = new ArrayList<>();

                    switch (type)
                    {
                        case "ANY":
                        {
                            productsToUse = skillMods;
                            break;
                        }
                        case "REWARDS":
                        {
                            productsToUse = skillMods.stream()
                                    .filter(p -> p.getProductTitle().equalsIgnoreCase("BOUNTY HUNTER")
                                            || p.getProductTitle().equalsIgnoreCase("EXPLORER")
                                            || p.getProductTitle().equalsIgnoreCase("SOCIALITE"))
                                    .collect(Collectors.toList());
                            break;
                        }
                        case "PURSUIT":
                        {
                            productsToUse = skillMods.stream()
                                    .filter(p -> p.getProductTitle().equalsIgnoreCase("COOLDOWN")
                                            || p.getProductTitle().equalsIgnoreCase("DEMOLITION")
                                            || p.getProductTitle().equalsIgnoreCase("EVASION")
                                            || p.getProductTitle().equalsIgnoreCase("OVERDRIVE")
                                            || p.getProductTitle().equalsIgnoreCase("RAM")
                                            || p.getProductTitle().equalsIgnoreCase("RAMPAGE")
                                            || p.getProductTitle().equalsIgnoreCase("RAPID REFILL")
                                            || p.getProductTitle().equalsIgnoreCase("RELOAD")
                                            || p.getProductTitle().equalsIgnoreCase("UNRELENTING FORCE"))
                                    .collect(Collectors.toList());
                            break;
                        }
                        case "RACE":
                        {
                            productsToUse = skillMods.stream()
                                    .filter(p -> p.getProductTitle().equalsIgnoreCase("AERODYNAMIC")
                                            || p.getProductTitle().equalsIgnoreCase("CATCH UP")
                                            || p.getProductTitle().equalsIgnoreCase("EXTENDED NITROUS")
                                            || p.getProductTitle().equalsIgnoreCase("HEADSTART")
                                            || p.getProductTitle().equalsIgnoreCase("JUMPSTART")
                                            || p.getProductTitle().equalsIgnoreCase("MAGNETIZED")
                                            || p.getProductTitle().equalsIgnoreCase("MOMENTUM")
                                            || p.getProductTitle().equalsIgnoreCase("PERSEVERENCE")
                                            || p.getProductTitle().equalsIgnoreCase("RAPID FIRE")
                                            || p.getProductTitle().equalsIgnoreCase("REDLINE")
                                            || p.getProductTitle().equalsIgnoreCase("ROLLING FORTRESS")
                                            || p.getProductTitle().equalsIgnoreCase("SAFETY FIRST")
                                            || p.getProductTitle().equalsIgnoreCase("SUPER SHOT")
                                            || p.getProductTitle().equalsIgnoreCase("TRAFFIC COP"))
                                    .collect(Collectors.toList());
                            break;
                        }
                        case "EXPLORE":
                        {
                            productsToUse = skillMods.stream()
                                    .filter(p -> p.getProductTitle().equalsIgnoreCase("BOUNTY HUNTER")
                                            || p.getProductTitle().equalsIgnoreCase("EXPLORER")
                                            || p.getProductTitle().equalsIgnoreCase("LIGHTNING REFLEX")
                                            || p.getProductTitle().equalsIgnoreCase("RADAR")
                                            || p.getProductTitle().equalsIgnoreCase("SOCIALITE")
                                            || p.getProductTitle().equalsIgnoreCase("TREASURE HUNTER"))
                                    .collect(Collectors.toList());
                            break;
                        }
                        case "DRAG":
                        {
                            productsToUse = skillMods.stream()
                                    .filter(p -> p.getProductTitle().equalsIgnoreCase("TAKEOFF")
                                            || p.getProductTitle().equalsIgnoreCase("WELL OILED MACHINE")
                                            || p.getProductTitle().equalsIgnoreCase("AERO DRAG"))
                                    .collect(Collectors.toList());
                            break;
                        }
                        /*
                                            || p.getProductTitle().equalsIgnoreCase("TAKEOFF")
                                            || p.getProductTitle().equalsIgnoreCase("WELL OILED MACHINE")
                                            || p.getProductTitle().equalsIgnoreCase("AERO DRAG")*/
                    }

                    List<ProductEntity> products = new ArrayList<>();

                    for (int i = 0; i < 5 && i < productsToUse.size(); i++)
                    {
                        ProductEntity product;

                        do
                        {
                            product = productsToUse.get(new Random().nextInt(productsToUse.size()));
                        } while (products.contains(product));

                        products.add(product);
                        CommerceItemTrans item = new CommerceItemTrans();
                        item.setHash(product.getHash());
                        item.setTitle(product.getProductTitle());
                        commerceItems.add(item);
                    }
                }
            } else if (rewardEntityValue.startsWith("RatedSkillMod"))
            {
                String[] parts = rewardEntityValue.split(Pattern.quote("|"));

                if (parts.length == 3)
                {
                    int rating = Integer.parseInt(parts[1]);
                    String type = parts[2].toUpperCase();
                    List<ProductEntity> skillMods = productBO.productsInCategory("NFSW_NA_EP_SKILLMODPARTS", "SKILLMODPART", persona.getPersonaId());

                    switch (type)
                    {
                        case "PURSUIT":
                        {
                            List<ProductEntity> pursuitSkills = skillMods.stream()
                                    .filter(p -> p.getProductTitle().equalsIgnoreCase("COOLDOWN")
                                            || p.getProductTitle().equalsIgnoreCase("DEMOLITION")
                                            || p.getProductTitle().equalsIgnoreCase("EVASION")
                                            || p.getProductTitle().equalsIgnoreCase("OVERDRIVE")
                                            || p.getProductTitle().equalsIgnoreCase("RAM")
                                            || p.getProductTitle().equalsIgnoreCase("RAMPAGE")
                                            || p.getProductTitle().equalsIgnoreCase("RAPID REFILL")
                                            || p.getProductTitle().equalsIgnoreCase("RELOAD")
                                            || p.getProductTitle().equalsIgnoreCase("UNRELENTING FORCE"))
                                    .collect(Collectors.toList());
                            final int productIndex = new Random().nextInt(pursuitSkills.size());
                            ProductEntity productEntity = pursuitSkills.stream().filter(s -> s.getProductTitle().equalsIgnoreCase(pursuitSkills.get(productIndex).getProductTitle()))
                                    .collect(Collectors.toList())
                                    .get(rating - 1);

                            inventoryBO.addDroppedItem(
                                    productEntity,
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(productEntity.getProductTitle());
                                    setHash(productEntity.getHash());
                                }
                            });

                            break;
                        }
                        case "RACE":
                        {
                            List<ProductEntity> raceSkills = skillMods.stream()
                                    .filter(p -> p.getProductTitle().equalsIgnoreCase("AERODYNAMIC")
                                            || p.getProductTitle().equalsIgnoreCase("CATCH UP")
                                            || p.getProductTitle().equalsIgnoreCase("EXTENDED NITROUS")
                                            || p.getProductTitle().equalsIgnoreCase("HEADSTART")
                                            || p.getProductTitle().equalsIgnoreCase("JUMPSTART")
                                            || p.getProductTitle().equalsIgnoreCase("MAGNETIZED")
                                            || p.getProductTitle().equalsIgnoreCase("MOMENTUM")
                                            || p.getProductTitle().equalsIgnoreCase("PERSEVERENCE")
                                            || p.getProductTitle().equalsIgnoreCase("RAPID FIRE")
                                            || p.getProductTitle().equalsIgnoreCase("REDLINE")
                                            || p.getProductTitle().equalsIgnoreCase("ROLLING FORTRESS")
                                            || p.getProductTitle().equalsIgnoreCase("SAFETY FIRST")
                                            || p.getProductTitle().equalsIgnoreCase("SUPER SHOT")
                                            || p.getProductTitle().equalsIgnoreCase("TRAFFIC COP")
                                            || p.getProductTitle().equalsIgnoreCase("TAKEOFF")
                                            || p.getProductTitle().equalsIgnoreCase("WELL OILED MACHINE")
                                            || p.getProductTitle().equalsIgnoreCase("AERO DRAG"))
                                    .collect(Collectors.toList());
                            final int productIndex = new Random().nextInt(raceSkills.size());
                            ProductEntity productEntity = raceSkills.stream().filter(s -> s.getProductTitle().equalsIgnoreCase(raceSkills.get(productIndex).getProductTitle()))
                                    .collect(Collectors.toList())
                                    .get(rating - 1);

                            inventoryBO.addDroppedItem(
                                    productEntity,
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(productEntity.getProductTitle());
                                    setHash(productEntity.getHash());
                                }
                            });

                            break;
                        }
                        case "EXPLORE":
                        {
                            List<ProductEntity> exploreSkills = skillMods.stream()
                                    .filter(p -> p.getProductTitle().equalsIgnoreCase("BOUNTY HUNTER")
                                            || p.getProductTitle().equalsIgnoreCase("EXPLORER")
                                            || p.getProductTitle().equalsIgnoreCase("LIGHTNING REFLEX")
                                            || p.getProductTitle().equalsIgnoreCase("RADAR")
                                            || p.getProductTitle().equalsIgnoreCase("SOCIALITE")
                                            || p.getProductTitle().equalsIgnoreCase("TREASURE HUNTER"))
                                    .collect(Collectors.toList());
                            final int productIndex = new Random().nextInt(exploreSkills.size());
                            ProductEntity productEntity = exploreSkills.stream().filter(s -> s.getProductTitle().equalsIgnoreCase(exploreSkills.get(productIndex).getProductTitle()))
                                    .collect(Collectors.toList())
                                    .get(rating - 1);

                            inventoryBO.addDroppedItem(
                                    productEntity,
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(productEntity.getProductTitle());
                                    setHash(productEntity.getHash());
                                }
                            });

                            break;
                        }
                        case "ANY":
                        {
                            final int productIndex = new Random().nextInt(skillMods.size());
                            ProductEntity productEntity = skillMods.stream().filter(s -> s.getProductTitle().equalsIgnoreCase(skillMods.get(productIndex).getProductTitle()))
                                    .collect(Collectors.toList())
                                    .get(rating - 1);

                            inventoryBO.addDroppedItem(
                                    productEntity,
                                    persona
                            );

                            commerceItems.add(new CommerceItemTrans()
                            {
                                {
                                    setTitle(productEntity.getProductTitle());
                                    setHash(productEntity.getHash());
                                }
                            });
                            break;
                        }
                    }
                }
            } else if (rewardEntityValue.startsWith("Powerups"))
            {
                String[] parts = rewardEntityValue.split(Pattern.quote("|"));

                if (parts.length == 3)
                {
                    String powerup = parts[1];
                    int amount = Integer.parseInt(parts[2]);
                    String productId = "";

                    switch (powerup)
                    {
                        case "RUN_FLATS":
                        {
                            productId = "SRV-POWERUP0";
                            break;
                        }
                        case "TRAFFIC_MAGNET":
                        {
                            productId = "SRV-POWERUP1";
                            break;
                        }
                        case "INSTANT_COOLDOWN":
                        {
                            productId = "SRV-POWERUP2";
                            break;
                        }
                        case "SHIELD":
                        {
                            productId = "SRV-POWERUP3";
                            break;
                        }
                        case "SLINGSHOT":
                        {
                            productId = "SRV-POWERUP4";
                            break;
                        }
                        case "READY":
                        {
                            productId = "SRV-POWERUP5";
                            break;
                        }
                        case "JUGGERNAUT":
                        {
                            productId = "SRV-POWERUP6";
                            break;
                        }
                        case "EMERGENCY_EVADE":
                        {
                            productId = "SRV-POWERUP7";
                            break;
                        }
                        case "TEAM_EMERGENCY_EVADE":
                        {
                            productId = "SRV-POWERUP8";
                            break;
                        }
                        case "NITROUS":
                        {
                            productId = "SRV-POWERUP9";
                            break;
                        }
                        case "ONE_MORE_LAP":
                        {
                            productId = "SRV-POWERUP10";
                            break;
                        }
                        case "TEAM_SLINGSHOT":
                        {
                            productId = "SRV-POWERUP11";
                            break;
                        }
                    }

                    ProductEntity productEntity = productDAO.findByProductId(productId);
                    productEntity.setUseCount(amount);
                    InventoryItemEntity inventoryItemEntity = inventoryBO.addDroppedItem(productEntity, persona);

                    commerceItems.add(new CommerceItemTrans()
                    {
                        {
                            setTitle(productEntity.getProductTitle().replace("x15", "x" + amount));
                            setHash(productEntity.getHash());
                        }
                    });

                    InventoryItemTrans inventoryItemTrans = new InventoryItemTrans();
                    inventoryItemTrans.setEntitlementTag(inventoryItemEntity.getEntitlementTag());
                    inventoryItemTrans.setHash(inventoryItemEntity.getHash());
                    inventoryItemTrans.setInventoryId(inventoryItemEntity.getId());
                    inventoryItemTrans.setProductId("DO NOT USE ME");
                    inventoryItemTrans.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount());
                    inventoryItemTrans.setResellPrice(inventoryItemEntity.getResalePrice());
                    inventoryItemTrans.setStringHash(inventoryItemEntity.getStringHash());
                    inventoryItemTrans.setStatus(inventoryItemEntity.getStatus());
                    inventoryItemTrans.setVirtualItemType(inventoryItemEntity.getVirtualItemType());

                    arrayOfInventoryItemTrans.getInventoryItemTrans().add(inventoryItemTrans);
                }
            } else if (rewardEntityValue.startsWith("Cash"))
            {
                String[] parts = rewardEntityValue.split(Pattern.quote("|"));
                int cash = parts.length == 2 ? Integer.parseInt(parts[1]) : 0;

                CommerceItemTrans item = new CommerceItemTrans();
                item.setHash(-429893590);
                item.setTitle("GM_CATALOG_00000190," + cash);

                commerceItems.add(item);
                persona.setCash(persona.getCash() + cash);
                personaDAO.update(persona);
            } else
            {
                String[] parts = rewardEntityValue.split(",");
                int index = new Random().nextInt(parts.length);
                String[] parts2 = rewardEntityValue.split(Pattern.quote("|"));
                int amount = parts2.length == 2 ? Integer.parseInt(parts2[1]) : 1;

                String productId = parts[index];

                if (productId.contains("|"))
                {
                    productId = productId.substring(0, productId.indexOf('|') - 1);
                }

                final ProductEntity productEntity = productDAO.findByProductId(productId);
                productEntity.setUseCount(amount);

                inventoryBO.addDroppedItem(productEntity, persona);

                commerceItems.add(new CommerceItemTrans()
                {
                    {
                        setTitle(productEntity.getProductTitle() + " x" + amount);
                        setHash(productEntity.getHash());
                    }
                });
            }
        }

        ArrayOfCommerceItemTrans arrayOfCommerceItemTrans = new ArrayOfCommerceItemTrans();
        arrayOfCommerceItemTrans.getCommerceItemTrans().addAll(commerceItems);
        achievementRewards.setCommerceItems(arrayOfCommerceItemTrans);
        WalletTrans walletTrans = new WalletTrans();
        walletTrans.setBalance(persona.getCash());
        walletTrans.setCurrency("CASH");

        ArrayOfWalletTrans arrayOfWalletTrans = new ArrayOfWalletTrans();
        arrayOfWalletTrans.getWalletTrans().add(walletTrans);

        achievementRewards.setWallets(arrayOfWalletTrans);
        achievementRewards.setStatus(CommerceResultStatus.SUCCESS);
        achievementRewards.setInventoryItems(arrayOfInventoryItemTrans);
        achievementRewards.setInvalidBasket(new InvalidBasketTrans());
        achievementRewards.setPurchasedCars(new ArrayOfOwnedCarTrans());

        return achievementRewards;
    }

    private PersonaAchievementEntity createPersonaAchievement(PersonaEntity personaEntity, AchievementDefinitionEntity achievement)
    {
        PersonaAchievementEntity entity = new PersonaAchievementEntity();
        entity.setCanProgress(true);
        entity.setCurrentValue(0L);
        entity.setAchievement(achievement);
        entity.setPersona(personaEntity);

        personaAchievementDAO.insert(entity);

        return entity;
    }

    private PersonaAchievementRankEntity createPersonaAchievementRank(
            PersonaEntity personaEntity, AchievementDefinitionEntity achievement, AchievementRankEntity achievementRank)
    {
        PersonaAchievementRankEntity personaRank = new PersonaAchievementRankEntity();

        personaRank.setPersona(personaEntity);
        personaRank.setAchievement(achievement);
        personaRank.setRank(achievementRank);
        personaRank.setState("Locked");
        personaRank.setAchievedOn("0001-01-01T00:00:00");

        personaAchievementRankDAO.insert(personaRank);

        return personaRank;
    }
}