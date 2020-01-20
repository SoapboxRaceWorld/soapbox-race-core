/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.*;
import com.soapboxrace.core.dao.CardPackDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.RewardTableDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.ArrayOfCommerceItemTrans;
import com.soapboxrace.jaxb.http.CommerceItemTrans;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.script.Bindings;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

@Stateless
public class ItemRewardBO {
    private final ThreadLocal<NashornScriptEngine> scriptEngine =
            ThreadLocal.withInitial(() -> (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn"));
    @EJB
    private PersonaDAO personaDAO;

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

    @EJB
    private CardPackDAO cardPackDAO;

    public ArrayOfCommerceItemTrans getRewards(Long personaId, String rewardScript) {
        try {
            PersonaEntity personaEntity = personaDAO.findById(personaId);
            ArrayOfCommerceItemTrans arrayOfCommerceItemTrans = new ArrayOfCommerceItemTrans();

            if (rewardScript != null) {
                handleReward(scriptToItem(rewardScript), arrayOfCommerceItemTrans, personaEntity);
            }

            return arrayOfCommerceItemTrans;
        } catch (Exception e) {
            throw new EngineException("Failed to generate rewards with script: " + rewardScript, e, EngineExceptionCode.LuckyDrawCouldNotDrawProduct);
        }
    }

    private ItemRewardBase scriptToItem(String rewardScript) {
        Bindings bindings = scriptEngine.get().createBindings();
        bindings.put("generator", getGenerator());

        try {
            return scriptToItem(rewardScript, bindings);
        } catch (ScriptException e) {
            throw new EngineException("Failed to execute script: " + rewardScript, e, EngineExceptionCode.LuckyDrawCouldNotDrawProduct);
        }
    }

    public RewardGenerator getGenerator() {
        return new RewardGenerator();
    }

    private ItemRewardBase scriptToItem(String rewardScript, Bindings bindings) throws ScriptException {
        Object obj = scriptEngine.get().eval(rewardScript, bindings);

        if (obj instanceof ItemRewardBase) {
            return (ItemRewardBase) obj;
        } else if (obj instanceof RewardBuilder) {
            return ((RewardBuilder<?>) obj).build();
        }

        throw new RuntimeException("Invalid script return: " + obj.getClass().getName());
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
                        inventoryBO.addInventoryItem(inventoryBO.getInventory(personaEntity.getPersonaId()),
                                productEntity.getProductId(), useCount);
                        break;
                    case "powerup":
                        inventoryBO.addStackedInventoryItem(inventoryBO.getInventory(personaEntity.getPersonaId()),
                                productEntity.getProductId(), useCount);
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
     * Exposes access to builder objects for rewards.
     */
    public class RewardGenerator {
        /**
         * Creates a cash reward builder
         *
         * @return The builder instance
         */
        public CashRewardBuilder cash() {
            return new CashRewardBuilder();
        }

        /**
         * Creates a random reward builder
         *
         * @return The builder instance
         */
        public RandomSelectionBuilder random() {
            return new RandomSelectionBuilder();
        }

        /**
         * Creates a product reward builder
         *
         * @return The builder instance
         */
        public ProductSelectionBuilder product() {
            return new ProductSelectionBuilder();
        }

        /**
         * Creates a table reward builder
         *
         * @return The builder instance
         */
        public TableSelectionBuilder table() {
            return new TableSelectionBuilder();
        }

        //region Compatibility

        public ItemRewardCash cashReward(int amount) {
            return cash().amount(amount).build();
        }

        public ItemRewardProduct findRandomRatedItem(String subType, Integer quality) {
            return product().subType(subType).rating(quality).build();
        }

        public ItemRewardProduct findRandomRatedItemByProdType(String prodType, Integer quality) {
            return product().type(prodType).rating(quality).build();
        }

        public ItemRewardProduct findRandomItemByProdType(String prodType) {
            return product().type(prodType).weighted(false).build();
        }

        public ItemRewardProduct findWeightedRandomItemByProdType(String prodType) {
            return product().type(prodType).weighted(true).build();
        }

        public ItemRewardProduct generateSingleItem(String entitlementTag) {
            ProductEntity byEntitlementTag = productDAO.findByEntitlementTag(entitlementTag);

            if (byEntitlementTag == null) {
                throw new IllegalArgumentException("Invalid entitlementTag: " + entitlementTag);
            }

            return new ItemRewardProduct(byEntitlementTag);
        }

        public ItemRewardMulti getCardPack(String packId) {
            List<ItemRewardBase> items = new ArrayList<>();
            CardPackEntity cardPackEntity = cardPackDAO.findByEntitlementTag(packId);

            for (CardPackItemEntity cardPackItemEntity : cardPackEntity.getItems()) {
                try {
                    items.add(scriptToItem(cardPackItemEntity.getScript()));
                } catch (Exception e) {
                    throw new RuntimeException("Error while generating card pack " + packId + " - could not execute script " + cardPackItemEntity.getScript(), e);
                }
            }

            return new ItemRewardMulti(items);
        }

        public ItemRewardMulti multiItems(String[] entitlementTags) {
            List<ItemRewardBase> productEntities = new ArrayList<>();

            for (String entitlementTag : entitlementTags) {
                productEntities.add(generateSingleItem(entitlementTag));
            }

            return new ItemRewardMulti(productEntities);
        }

        public ItemRewardMulti multipleRewards(ItemRewardBase[] rewards) {
            return new ItemRewardMulti(Arrays.asList(rewards));
        }

        public ItemRewardBase randomDrop(List<String> entitlementTags) {
            return random().withBuilders(entitlementTags.stream().map(s -> product().entitlementTag(s)).collect(Collectors.toList())).build();
        }

        public ItemRewardBase randomSelection(List<ItemRewardBase> rewards) {
            if (rewards.isEmpty()) {
                throw new IllegalArgumentException("No rewards to choose from!");
            }

            return rewards.get(new Random().nextInt(rewards.size()));
        }

        public ItemRewardBase randomTableItem(String tableId) {
            return table().tableName(tableId).weighted(false).build();
        }

        public ItemRewardQuantityProduct rewardQuantityProduct(String entitlementTag, Integer quantity) {
            return product().entitlementTag(entitlementTag).quantity(quantity).weighted(false).build();
        }

        public ItemRewardBase weightedRandomTableItem(String tableName) {
            return table().tableName(tableName).weighted(true).build();
        }
        //endregion
    }

    /**
     * Reward builder for cash rewards
     */
    public class CashRewardBuilder extends RewardBuilder<ItemRewardCash> {

        private int cash;

        /**
         * Sets the cash amount of the reward
         *
         * @param cash The cash amount
         * @return The updated builder
         */
        public CashRewardBuilder amount(int cash) {
            this.cash = cash;
            return this;
        }

        @Override
        public ItemRewardCash build() {
            return new ItemRewardCash(this.cash);
        }
    }

    /**
     * Reward builder for random selections
     */
    public class RandomSelectionBuilder extends RewardBuilder<ItemRewardBase> {

        private List<ItemRewardBase> choices;

        public RandomSelectionBuilder withChoices(List<ItemRewardBase> choices) {
            this.choices = choices;
            return this;
        }

        public RandomSelectionBuilder withBuilders(List<RewardBuilder<?>> choices) {
            this.choices =
                    choices.stream().map((Function<RewardBuilder<?>, ItemRewardBase>) RewardBuilder::build).collect(Collectors.toList());
            return this;
        }

        @Override
        public ItemRewardBase build() {
            Objects.requireNonNull(this.choices);
            return this.choices.get(new Random().nextInt(this.choices.size()));
        }
    }

    /**
     * Reward builder for product selections
     */
    public class ProductSelectionBuilder extends RewardBuilder<ItemRewardProduct> {

        private boolean isWeighted;

        private String entitlementTag;

        private String category;

        private String productType;

        private String subType;

        private Integer rating;

        private Integer quantity = -1;

        public ProductSelectionBuilder category(String category) {
            this.category = category;
            return this;
        }

        public ProductSelectionBuilder weighted(boolean isWeighted) {
            this.isWeighted = isWeighted;
            return this;
        }

        public ProductSelectionBuilder type(String productType) {
            this.productType = productType;
            return this;
        }

        public ProductSelectionBuilder subType(String subType) {
            this.subType = subType;
            return this;
        }

        public ProductSelectionBuilder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public ProductSelectionBuilder quantity(Integer quantity) {
            if (quantity < -1) {
                throw new IllegalArgumentException("quantity < -1");
            }

            this.quantity = quantity;
            return this;
        }

        public ProductSelectionBuilder entitlementTag(String entitlementTag) {
            this.entitlementTag = entitlementTag;
            return this;
        }

        @Override
        public ItemRewardQuantityProduct build() {
            if (this.entitlementTag != null && !this.entitlementTag.isEmpty()) {
                return new ItemRewardQuantityProduct(productDAO.findByEntitlementTag(this.entitlementTag),
                        this.quantity);
            }

            List<ProductEntity> productEntities = productDAO.findByTraits(
                    this.category,
                    this.productType,
                    this.subType,
                    this.rating
            );

            String debugFormat = String.format("C=%s PT=%s ST=%s R=%d",
                    this.category, this.productType, this.subType, this.rating);
            if (productEntities.isEmpty()) {
                throw new RuntimeException("No products to choose from! " + debugFormat);
            }

            int numProducts = productEntities.size();

            if (this.isWeighted) {
                double weightSum =
                        productEntities.stream().mapToDouble(getDropWeight(numProducts)).sum();

                int randomIndex = -1;
                double random = Math.random() * weightSum;

                for (int i = 0; i < productEntities.size(); i++) {
                    random -= getDropWeight(numProducts).applyAsDouble(productEntities.get(i));

                    if (random <= 0.0d) {
                        randomIndex = i;
                        break;
                    }
                }

                if (randomIndex == -1) {
                    throw new RuntimeException("Weighted random failed! " + debugFormat);
                }

                return new ItemRewardQuantityProduct(productEntities.get(randomIndex), quantity);
            }

            return new ItemRewardQuantityProduct(
                    productEntities.get(new Random().nextInt(productEntities.size())),
                    quantity);
        }

        private ToDoubleFunction<ProductEntity> getDropWeight(int numProducts) {
            return p -> {
                Double dropWeight = p.getDropWeight();
                if (dropWeight == null)
                    return 1.0d / numProducts;
                return dropWeight;
            };
        }
    }

    /**
     * Reward builder for table selections
     */
    public class TableSelectionBuilder extends RewardBuilder<ItemRewardBase> {

        private String tableName;

        private boolean weighted = false;

        public TableSelectionBuilder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public TableSelectionBuilder weighted(boolean weighted) {
            this.weighted = weighted;
            return this;
        }

        @Override
        public ItemRewardBase build() {
            Objects.requireNonNull(this.tableName);

            RewardTableEntity rewardTableEntity = rewardTableDAO.findByName(this.tableName);

            Objects.requireNonNull(rewardTableEntity, this.tableName + " not found!");

            List<RewardTableItemEntity> items = rewardTableEntity.getItems();

            if (items.isEmpty()) {
                throw new EngineException("No items are available in table " + this.tableName,
                        EngineExceptionCode.LuckyDrawContextNotFoundOrEmpty);
            }

            int numItems = items.size();

            if (this.weighted) {
                double weightSum =
                        items.stream().mapToDouble(getDropWeight(numItems)).sum();

                int randomIndex = -1;
                double random = Math.random() * weightSum;

                for (int i = 0; i < items.size(); i++) {
                    random -= getDropWeight(numItems).applyAsDouble(items.get(i));

                    if (random <= 0.0d) {
                        randomIndex = i;
                        break;
                    }
                }

                if (randomIndex == -1) {
                    throw new EngineException("Weighted random failed for " + this.tableName,
                            EngineExceptionCode.LuckyDrawCouldNotDrawProduct);
                }

                RewardTableItemEntity rewardTableItemEntity = items.get(randomIndex);
                try {
                    return scriptToItem(rewardTableItemEntity.getScript());
                } catch (Exception e) {
                    throw new EngineException("Could not execute script: " + rewardTableItemEntity.getScript(), e, EngineExceptionCode.LuckyDrawCouldNotDrawProduct);
                }
            }

            RewardTableItemEntity rewardTableItemEntity = items.get(new Random().nextInt(items.size()));

            try {
                return scriptToItem(rewardTableItemEntity.getScript());
            } catch (Exception e) {
                throw new EngineException("Could not execute script: " + rewardTableItemEntity.getScript(), e, EngineExceptionCode.LuckyDrawCouldNotDrawProduct);
            }
        }

        private ToDoubleFunction<RewardTableItemEntity> getDropWeight(int numItems) {
            return item -> {
                Double dropWeight = item.getDropWeight();
                if (dropWeight == null)
                    return 1.0d / numItems;
                return dropWeight;
            };
        }
    }

    /**
     * Base class for a reward builder
     *
     * @param <T> Reward object type
     */
    private abstract class RewardBuilder<T extends ItemRewardBase> {
        public abstract T build();
    }
}
