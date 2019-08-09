package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.*;
import com.soapboxrace.core.dao.CardPackDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.RewardTableDAO;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.ArrayOfCommerceItemTrans;
import com.soapboxrace.jaxb.http.CommerceItemTrans;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.script.Bindings;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class ItemRewardBO {
    private final ThreadLocal<NashornScriptEngine> scriptEngine =
            ThreadLocal.withInitial(() -> (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn"));
    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private CardPackDAO cardPackDAO;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private RewardTableDAO rewardTableDAO;

    @EJB
    private InventoryBO inventoryBO;

    @EJB
    private DriverPersonaBO driverPersonaBO;

    @EJB
    private BasketBO basketBO;
    private Random random = new SecureRandom();

    public ArrayOfCommerceItemTrans getRewards(Long personaId, String rewardScript) {
        PersonaEntity personaEntity = personaDAO.findById(personaId);
        ArrayOfCommerceItemTrans arrayOfCommerceItemTrans = new ArrayOfCommerceItemTrans();

        if (rewardScript != null) {
            try {
                handleReward(scriptToItem(rewardScript), arrayOfCommerceItemTrans, personaEntity);
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        }

        return arrayOfCommerceItemTrans;
    }

    private ItemRewardBase scriptToItem(String rewardScript) throws ScriptException {
        Bindings bindings = scriptEngine.get().createBindings();
        bindings.put("generator", getGenerator());

        return scriptToItem(rewardScript, bindings);
    }

    public RewardGenerator getGenerator() {
        return new RewardGenerator();
    }

    private ItemRewardBase scriptToItem(String rewardScript, Bindings bindings) throws ScriptException {
        return (ItemRewardBase) scriptEngine.get().eval(rewardScript, bindings);
    }

    private void handleReward(ItemRewardBase itemRewardBase, ArrayOfCommerceItemTrans arrayOfCommerceItemTrans,
                              PersonaEntity personaEntity) {
        if (itemRewardBase instanceof ItemRewardCash) {
            ItemRewardCash achievementRewardCash = (ItemRewardCash) itemRewardBase;

            arrayOfCommerceItemTrans.getCommerceItemTrans().add(new CommerceItemTrans() {{
                setTitle("LB_CASH," + achievementRewardCash.getCash());
                setHash(-429893590);
            }});

            driverPersonaBO.updateCash(personaEntity, personaEntity.getCash() + achievementRewardCash.getCash());
        } else if (itemRewardBase instanceof ItemRewardMulti) {
            ItemRewardMulti achievementRewardMulti = (ItemRewardMulti) itemRewardBase;
            achievementRewardMulti.getAchievementRewardList().forEach(r -> handleReward(r, arrayOfCommerceItemTrans,
                    personaEntity));
        } else {
            List<ProductEntity> productEntities = new ArrayList<>(itemRewardBase.getProducts());
            Integer useCount = -1;

            if (itemRewardBase instanceof ItemRewardQuantityProduct) {
                useCount = ((ItemRewardQuantityProduct) itemRewardBase).getUseCount();
            }

            for (ProductEntity productEntity : productEntities) {
                arrayOfCommerceItemTrans.getCommerceItemTrans().add(productToCommerceItem(productEntity, useCount));

                switch (productEntity.getProductType().toLowerCase()) {
                    case "presetcar":
                        basketBO.addCar(productEntity.getProductId(), personaEntity);
                        break;
                    case "performancepart":
                    case "skillmodpart":
                    case "visualpart":
                    case "powerup":
                        inventoryBO.addFromCatalogOrUpdateUsage(productEntity, personaEntity, useCount);
                        break;
                }
            }
        }
    }

    private CommerceItemTrans productToCommerceItem(ProductEntity productEntity, Integer useCount) {
        CommerceItemTrans commerceItemTrans = new CommerceItemTrans();
        commerceItemTrans.setHash(productEntity.getHash());
        commerceItemTrans.setTitle(productEntity.getProductTitle());

        if (useCount != -1) {
            commerceItemTrans.setTitle(commerceItemTrans.getTitle() + " x" + useCount);
        }

        return commerceItemTrans;
    }

    /**
     * Helper class for generating {@link ItemRewardBase} objects
     */
    public class RewardGenerator {
        /**
         * Finds the product with the given entitlement tag and returns it as an {@link ItemRewardProduct}
         *
         * @param entitlementTag The entitlement tag of the desired product
         * @return The {@link ItemRewardProduct} instance containing the desired product
         */
        public ItemRewardProduct generateSingleItem(String entitlementTag) {
            ProductEntity byEntitlementTag = productDAO.findByEntitlementTag(entitlementTag);

            if (byEntitlementTag == null) {
                throw new IllegalArgumentException("Invalid entitlementTag: " + entitlementTag);
            }

            return new ItemRewardProduct(byEntitlementTag);
        }

        public ItemRewardMultiProduct multiItems(String[] entitlementTags) {
            List<ProductEntity> productEntities = new ArrayList<>();

            for (String entitlementTag : entitlementTags) {
                ProductEntity byEntitlementTag = productDAO.findByEntitlementTag(entitlementTag);

                if (byEntitlementTag == null) {
                    throw new IllegalArgumentException("Invalid entitlementTag: " + entitlementTag);
                }

                productEntities.add(byEntitlementTag);
            }

            return new ItemRewardMultiProduct(productEntities);
        }

        public ItemRewardProduct randomDrop(String[] entitlementTags) {
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

            return new ItemRewardProduct(productEntities.get(random.nextInt(productEntities.size())));
        }

        public ItemRewardProduct randomDrop(List<String> entitlementTags) {
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

            return new ItemRewardProduct(productEntities.get(random.nextInt(productEntities.size())));
        }

        public ItemRewardBase randomSelection(List<ItemRewardBase> rewards) {
            if (rewards.isEmpty()) {
                throw new IllegalArgumentException("No rewards to choose from!");
            }

            return rewards.get(random.nextInt(rewards.size()));
        }

        public ItemRewardCash cashReward(Integer cashAmount) {
            return new ItemRewardCash(cashAmount);
        }

        public ItemRewardQuantityProduct rewardQuantityProduct(String entitlementTag, Integer quantity) {
            ProductEntity byEntitlementTag = productDAO.findByEntitlementTag(entitlementTag);

            if (byEntitlementTag == null) {
                throw new IllegalArgumentException("Invalid entitlementTag: " + entitlementTag);
            }

            return new ItemRewardQuantityProduct(byEntitlementTag, quantity);
        }

        public ItemRewardMulti multipleRewards(ItemRewardBase[] rewards) {
            return new ItemRewardMulti(Arrays.asList(rewards));
        }

        // special item finders
        public ItemRewardProduct findRandomRatedItem(String type, Integer rating) {
            List<ProductEntity> productEntities = productDAO.findBySubTypeAndRarity(type, rating);
            try {
                return randomDrop(productEntities.stream().map(ProductEntity::getEntitlementTag).collect(Collectors.toList()));
            } catch (Exception e) {
                throw new RuntimeException("findRandomRatedItem() failed for: " + type + ", " + rating, e);
            }
        }

        public ItemRewardProduct findRandomRatedItemByProdType(String type, Integer rating) {
            List<ProductEntity> productEntities = productDAO.findByProdTypeAndRarity(type, rating);
            try {
                return randomDrop(productEntities.stream().map(ProductEntity::getEntitlementTag).collect(Collectors.toList()));
            } catch (Exception e) {
                throw new RuntimeException("findRandomRatedItemByProdType() failed for: " + type + ", " + rating, e);
            }
        }

        public ItemRewardProduct findRandomItemByProdType(String type) {
            List<ProductEntity> productEntities = productDAO.findDropsByType(type);
            try {
                return randomDrop(productEntities.stream().map(ProductEntity::getEntitlementTag).collect(Collectors.toList()));
            } catch (Exception e) {
                throw new RuntimeException("findRandomItemByProdType() failed for: " + type + " (products: " + productEntities.size() + ")");
            }
        }

        public ItemRewardProduct findWeightedRandomItemByProdType(String type) {
            List<ProductEntity> productEntities = productDAO.findDropsByType(type);

            if (productEntities.isEmpty()) {
                throw new IllegalArgumentException("No products to choose from of type " + type);
            }

            double weightSum = productEntities.stream().mapToDouble(p -> this.getDropWeight(p, productEntities)).sum();

            int randomIndex = -1;
            double random = Math.random() * weightSum;

            for (int i = 0; i < productEntities.size(); i++) {
                random -= this.getDropWeight(productEntities.get(i), productEntities);

                if (random <= 0.0d) {
                    randomIndex = i;
                    break;
                }
            }

            if (randomIndex == -1) {
                throw new RuntimeException("Random selection failed for type " + type);
            }

            return new ItemRewardProduct(productEntities.get(randomIndex));
        }

        private double getDropWeight(ProductEntity p, List<ProductEntity> productEntities) {
            if (p.getDropWeight() == null) {
                return 1.0d / productEntities.size();
            }

            return p.getDropWeight();
        }

        private double getDropWeight(RewardTableItemEntity i, List<RewardTableItemEntity> items) {
            if (i.getDropWeight() == null) {
                return 1.0d / items.size();
            }

            return i.getDropWeight();
        }

        public ItemRewardMulti getCardPack(String cardPackId) {
            List<ItemRewardBase> items = new ArrayList<>();
            CardPackEntity cardPackEntity = cardPackDAO.findByEntitlementTag(cardPackId);

            for (CardPackItemEntity cardPackItemEntity : cardPackEntity.getItems()) {
                try {
                    items.add(scriptToItem(cardPackItemEntity.getScript()));
                } catch (ScriptException e) {
                    throw new RuntimeException("Error while generating card pack " + cardPackId, e);
                }
            }

            return new ItemRewardMulti(items);
        }

        public ItemRewardBase randomTableItem(String tableId) {
            try {
                return randomTableItem(rewardTableDAO.findByName(tableId).getId());
            } catch (Exception e) {
                throw new RuntimeException("Error while doing weighted random for " + tableId, e);
            }
        }

        public ItemRewardBase randomTableItem(Long tableId) {
            RewardTableEntity rewardTableEntity = rewardTableDAO.findByID(tableId);
            List<RewardTableItemEntity> items = rewardTableEntity.getItems();

            if (items.isEmpty()) {
                throw new IllegalArgumentException("No items to choose from in table " + tableId);
            }

            try {
                return scriptToItem(items.get(random.nextInt(items.size())).getScript());
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        }

        public ItemRewardBase weightedRandomTableItem(Long tableId) {
            RewardTableEntity rewardTableEntity = rewardTableDAO.findByID(tableId);
            Objects.requireNonNull(rewardTableEntity);

            List<RewardTableItemEntity> items = rewardTableEntity.getItems();

            if (items.isEmpty()) {
                throw new IllegalArgumentException("No items to choose from in table " + tableId);
            }

            double weightSum =
                    items.stream().mapToDouble(i -> this.getDropWeight(i, items)).sum();
            int randomIndex = -1;
            double random = Math.random() * weightSum;

            for (int i = 0; i < items.size(); i++) {
                random -= this.getDropWeight(items.get(i), items);

                if (random <= 0.0d) {
                    randomIndex = i;
                    break;
                }
            }

            if (randomIndex == -1) {
                throw new RuntimeException("Random selection failed for table " + tableId + ".");
            }

            try {
                return scriptToItem(items.get(randomIndex).getScript());
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        }

        public ItemRewardBase weightedRandomTableItem(String tableId) {
            RewardTableEntity rewardTableEntity = rewardTableDAO.findByName(tableId);

            Objects.requireNonNull(rewardTableEntity);

            try {
                return weightedRandomTableItem(rewardTableEntity.getId());
            } catch (Exception e) {
                throw new RuntimeException("Error while doing weighted random for " + tableId, e);
            }
        }
    }
}
