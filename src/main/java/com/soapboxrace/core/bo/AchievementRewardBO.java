package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.*;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.AchievementRankEntity;
import com.soapboxrace.core.jpa.AchievementRewardEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.*;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.script.Bindings;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Stateless
public class AchievementRewardBO {
    /**
     * Helper class for generating {@link com.soapboxrace.core.bo.util.AchievementRewardBase} objects
     */
    public class RewardGenerator {
        /**
         * Finds the product with the given entitlement tag and returns it as an {@link AchievementRewardProduct}
         *
         * @param entitlementTag The entitlement tag of the desired product
         * @return The {@link AchievementRewardProduct} instance containing the desired product
         */
        public AchievementRewardProduct generateSingleItem(String entitlementTag) {
            ProductEntity byEntitlementTag = productDAO.findByEntitlementTag(entitlementTag);

            if (byEntitlementTag == null) {
                throw new IllegalArgumentException("Invalid entitlementTag: " + entitlementTag);
            }

            return new AchievementRewardProduct(byEntitlementTag);
        }

        public AchievementRewardMultiProduct multiItems(String[] entitlementTags) {
            List<ProductEntity> productEntities = new ArrayList<>();

            for (String entitlementTag : entitlementTags) {
                ProductEntity byEntitlementTag = productDAO.findByEntitlementTag(entitlementTag);

                if (byEntitlementTag == null) {
                    throw new IllegalArgumentException("Invalid entitlementTag: " + entitlementTag);
                }

                productEntities.add(byEntitlementTag);
            }

            return new AchievementRewardMultiProduct(productEntities);
        }

        public AchievementRewardProduct randomDrop(String[] entitlementTags) {
            List<ProductEntity> productEntities = new ArrayList<>();

            for (String entitlementTag : entitlementTags) {
                ProductEntity byEntitlementTag = productDAO.findByEntitlementTag(entitlementTag);

                if (byEntitlementTag == null) {
                    throw new IllegalArgumentException("Invalid entitlementTag: " + entitlementTag);
                }

                productEntities.add(byEntitlementTag);
            }

            if (productEntities.isEmpty()) {
                throw new IllegalArgumentException("No products to choose from!");
            }

            return new AchievementRewardProduct(productEntities.get(random.nextInt(productEntities.size())));
        }

        public AchievementRewardProduct randomDrop(List<String> entitlementTags) {
            List<ProductEntity> productEntities = new ArrayList<>();

            for (String entitlementTag : entitlementTags) {
                ProductEntity byEntitlementTag = productDAO.findByEntitlementTag(entitlementTag);

                if (byEntitlementTag == null) {
                    throw new IllegalArgumentException("Invalid entitlementTag: " + entitlementTag);
                }

                productEntities.add(byEntitlementTag);
            }

            if (productEntities.isEmpty()) {
                throw new IllegalArgumentException("No products to choose from!");
            }

            return new AchievementRewardProduct(productEntities.get(random.nextInt(productEntities.size())));
        }

        public AchievementRewardCash cashReward(Integer cashAmount) {
            return new AchievementRewardCash(cashAmount);
        }

        public AchievementRewardMulti multipleRewards(AchievementRewardBase[] rewards) {
            return new AchievementRewardMulti(Arrays.asList(rewards));
        }

        // special item finders
        public AchievementRewardProduct findRandomRatedItem(String type, Integer rating) {
            List<ProductEntity> productEntities = productDAO.findBySubTypeAndRarity(type, rating);
            return randomDrop(productEntities.stream().map(ProductEntity::getEntitlementTag).collect(Collectors.toList()));
        }

        public AchievementRewardProduct findRandomRatedItemByProdType(String type, Integer rating) {
            List<ProductEntity> productEntities = productDAO.findByProdTypeAndRarity(type, rating);
            return randomDrop(productEntities.stream().map(ProductEntity::getEntitlementTag).collect(Collectors.toList()));
        }
    }

    @EJB
    private AchievementRankDAO achievementRankDAO;

    @EJB
    private AchievementRewardDAO achievementRewardDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private ProductDAO productDAO;

    private final ThreadLocal<NashornScriptEngine> scriptEngine = ThreadLocal.withInitial(() -> (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn"));

    private Random random = new SecureRandom();

    public AchievementRewards redeemRewards(Long personaId, Long achievementRankId) {
        PersonaEntity personaEntity = personaDAO.findById(personaId);
        AchievementRankEntity achievementRankEntity = achievementRankDAO.findById(achievementRankId);

        if (achievementRankEntity == null) {
            throw new IllegalArgumentException("Invalid achievementRankId: " + achievementRankId);
        }

        AchievementRewards achievementRewards = new AchievementRewards();
        achievementRewards.setAchievementRankId(achievementRankId);
        achievementRewards.setVisualStyle(achievementRankEntity.getRewardVisualStyle());
        achievementRewards.setStatus(CommerceResultStatus.SUCCESS);
        achievementRewards.setCommerceItems(new ArrayOfCommerceItemTrans());
        achievementRewards.setInventoryItems(new ArrayOfInventoryItemTrans());
        achievementRewards.setPurchasedCars(new ArrayOfOwnedCarTrans());
        achievementRewards.setWallets(new ArrayOfWalletTrans());

        AchievementRewardEntity achievementRewardEntity = achievementRewardDAO.findByAchievementRankId(achievementRankId);

        if (achievementRewardEntity.getRewardScript() != null) {
            Bindings bindings = scriptEngine.get().createBindings();
            bindings.put("generator", new RewardGenerator());
            try {
                AchievementRewardBase achievementRewardBase = (AchievementRewardBase) scriptEngine.get().eval(achievementRewardEntity.getRewardScript(), bindings);
                handleReward(achievementRewardBase, achievementRewards, personaEntity);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }

        achievementRewards.getWallets().getWalletTrans().add(new WalletTrans(){{
            setBalance(personaEntity.getCash());
            setCurrency("CASH");
        }});

        return achievementRewards;
    }

    private void handleReward(AchievementRewardBase achievementRewardBase, AchievementRewards achievementRewards, PersonaEntity personaEntity) {
        if (achievementRewardBase instanceof AchievementRewardCash) {
            AchievementRewardCash achievementRewardCash = (AchievementRewardCash) achievementRewardBase;

            achievementRewards.getCommerceItems().getCommerceItemTrans().add(new CommerceItemTrans() {{
                setTitle("LB_CASH," + achievementRewardCash.getCash());
                setHash(-429893590);
            }});

            // TODO: cash limit
            personaEntity.setCash(personaEntity.getCash() + achievementRewardCash.getCash());
            personaDAO.update(personaEntity);
        } else if (achievementRewardBase instanceof AchievementRewardMulti) {
            AchievementRewardMulti achievementRewardMulti = (AchievementRewardMulti) achievementRewardBase;
            achievementRewardMulti.getAchievementRewardList().forEach(r -> handleReward(r, achievementRewards, personaEntity));
        } else {
            List<ProductEntity> productEntities = new ArrayList<>(achievementRewardBase.getProducts());
            for (ProductEntity productEntity : productEntities) {
                achievementRewards.getCommerceItems().getCommerceItemTrans().add(productToCommerceItem(productEntity));
            }
        }
    }

    private CommerceItemTrans productToCommerceItem(ProductEntity productEntity) {
        CommerceItemTrans commerceItemTrans = new CommerceItemTrans();
        commerceItemTrans.setHash(productEntity.getHash());
        commerceItemTrans.setTitle(productEntity.getProductTitle());
        return commerceItemTrans;
    }
}
