/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.*;
import com.soapboxrace.core.dao.CardPackDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.RewardTableDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.script.ScriptException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class ItemRewardBO {

    private final Random random = new Random();

    @EJB
    private ScriptingBO scriptingBO;

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

    public RewardedItemsContainer getRewards(PersonaEntity personaEntity, String rewardScript) {
        try {
            if (rewardScript != null) {
                return handleReward(scriptToItem(rewardScript), inventoryBO.getInventory(personaEntity), personaEntity);
            }

            throw new RuntimeException("rewardScript was null!");
        } catch (Exception e) {
            throw new EngineException("Failed to generate rewards with script: " + rewardScript, e, EngineExceptionCode.LuckyDrawCouldNotDrawProduct, true);
        }
    }

    public void convertRewards(RewardedItemsContainer rewardedItemsContainer, GenericCommerceResult commerceResult) {
        if (commerceResult.getCommerceItems() == null)
            commerceResult.setCommerceItems(new ArrayOfCommerceItemTrans());
        if (commerceResult.getInventoryItems() == null)
            commerceResult.setInventoryItems(new ArrayOfInventoryItemTrans());
        if (commerceResult.getPurchasedCars() == null)
            commerceResult.setPurchasedCars(new ArrayOfOwnedCarTrans());

        for (ItemRewardCash itemRewardCash : rewardedItemsContainer.getCashRewardList()) {
            CommerceItemTrans commerceItemTrans = new CommerceItemTrans();
            commerceItemTrans.setTitle("LB_CASH," + itemRewardCash.getCash());
            commerceItemTrans.setHash(-429893590);
            commerceResult.getCommerceItems().getCommerceItemTrans().add(commerceItemTrans);
        }

        for (WrappedInventoryItemReward wrappedInventoryItemReward : rewardedItemsContainer.getInventoryItemList()) {
            CommerceItemTrans commerceItemTrans = new CommerceItemTrans();
            InventoryItemEntity inventoryItemEntity = wrappedInventoryItemReward.getInventoryItemEntity();
            ProductEntity productEntity = inventoryItemEntity.getProductEntity();
            Integer useCount = wrappedInventoryItemReward.getUseCount();
            commerceItemTrans.setHash(productEntity.getHash());
            commerceItemTrans.setTitle(productEntity.getProductTitle());

            if (useCount != -1) {
                commerceItemTrans.setTitle(commerceItemTrans.getTitle() + " x" + useCount);
            }

            commerceResult.getCommerceItems().getCommerceItemTrans().add(commerceItemTrans);
            commerceResult.getInventoryItems().getInventoryItemTrans().add(inventoryBO.convertItemToItemTrans(inventoryItemEntity));
        }

        for (WrappedCarReward carReward : rewardedItemsContainer.getCarRewardList()) {
            commerceResult.getPurchasedCars().getOwnedCarTrans().add(OwnedCarConverter.entity2Trans(carReward.getOwnedCarEntity()));
            ProductEntity productEntity = carReward.getProductEntity();

            CommerceItemTrans commerceItemTrans = new CommerceItemTrans();
            commerceItemTrans.setHash(productEntity.getHash());
            commerceItemTrans.setTitle(productEntity.getProductTitle());

            commerceResult.getCommerceItems().getCommerceItemTrans().add(commerceItemTrans);
        }
    }

    private ItemRewardBase scriptToItem(String rewardScript) {
        Map<String, Object> bindings = new HashMap<>();
        bindings.put("generator", getGenerator());

        try {
            return scriptToItem(rewardScript, bindings);
        } catch (ScriptException e) {
            throw new EngineException("Failed to execute script: " + rewardScript, e, EngineExceptionCode.LuckyDrawCouldNotDrawProduct, true);
        }
    }

    public RewardGenerator getGenerator() {
        return new RewardGenerator();
    }

    private ItemRewardBase scriptToItem(String rewardScript, Map<String, Object> bindings) throws ScriptException {
        Object obj = scriptingBO.eval(rewardScript, bindings);

        if (obj instanceof ItemRewardBase) {
            return (ItemRewardBase) obj;
        } else if (obj instanceof RewardBuilder) {
            return ((RewardBuilder<?>) obj).build();
        }

        throw new RuntimeException("Invalid script return: " + obj.getClass().getName());
    }

    private RewardedItemsContainer handleReward(ItemRewardBase itemRewardBase, InventoryEntity inventoryEntity,
                                                PersonaEntity personaEntity) {
        RewardedItemsContainer rewardedItemsContainer = new RewardedItemsContainer();
        if (itemRewardBase instanceof ItemRewardCash) {
            ItemRewardCash achievementRewardCash = (ItemRewardCash) itemRewardBase;
            driverPersonaBO.updateCash(personaEntity, personaEntity.getCash() + achievementRewardCash.getCash());
            rewardedItemsContainer.getCashRewardList().add(achievementRewardCash);
        } else if (itemRewardBase instanceof ItemRewardMulti) {
            ItemRewardMulti achievementRewardMulti = (ItemRewardMulti) itemRewardBase;
            achievementRewardMulti.getAchievementRewardList().forEach(r -> {
                RewardedItemsContainer newContainer = handleReward(r, inventoryEntity, personaEntity);
                rewardedItemsContainer.getInventoryItemList().addAll(newContainer.getInventoryItemList());
                rewardedItemsContainer.getCarRewardList().addAll(newContainer.getCarRewardList());
                rewardedItemsContainer.getCashRewardList().addAll(newContainer.getCashRewardList());
            });
        } else {
            List<ProductEntity> productEntities = new ArrayList<>(itemRewardBase.getProducts());
            Integer useCount = -1;

            if (itemRewardBase instanceof ItemRewardQuantityProduct) {
                useCount = ((ItemRewardQuantityProduct) itemRewardBase).getUseCount();
            }

            for (ProductEntity productEntity : productEntities) {
                switch (productEntity.getProductType().toLowerCase()) {
                    case "presetcar":
                        rewardedItemsContainer.getCarRewardList().add(new WrappedCarReward(basketBO.addCar(productEntity, personaEntity), productEntity));
                        break;
                    case "performancepart":
                    case "skillmodpart":
                    case "visualpart":
                        rewardedItemsContainer.getInventoryItemList().add(new WrappedInventoryItemReward(
                                inventoryBO.addInventoryItem(inventoryEntity,
                                        productEntity.getProductId(), useCount, true), useCount
                        ));
                        break;
                    case "powerup":
                        rewardedItemsContainer.getInventoryItemList().add(new WrappedInventoryItemReward(
                                inventoryBO.addStackedInventoryItem(inventoryEntity,
                                        productEntity.getProductId(), useCount, true), useCount
                        ));
                        break;
                }
            }
        }

        return rewardedItemsContainer;
    }

    public static class WrappedInventoryItemReward {
        private final InventoryItemEntity inventoryItemEntity;

        private final Integer useCount;

        public WrappedInventoryItemReward(InventoryItemEntity inventoryItemEntity, Integer useCount) {
            this.inventoryItemEntity = inventoryItemEntity;
            this.useCount = useCount;
        }

        public InventoryItemEntity getInventoryItemEntity() {
            return inventoryItemEntity;
        }

        public Integer getUseCount() {
            return useCount;
        }
    }

    public static class WrappedCarReward {
        private final CarEntity carEntity;

        private final ProductEntity productEntity;

        public WrappedCarReward(CarEntity carEntity, ProductEntity productEntity) {
            this.carEntity = carEntity;
            this.productEntity = productEntity;
        }

        public CarEntity getOwnedCarEntity() {
            return carEntity;
        }

        public ProductEntity getProductEntity() {
            return productEntity;
        }
    }

    public static class RewardedItemsContainer {
        private final List<ItemRewardCash> cashRewardList = new ArrayList<>();

        private final List<WrappedInventoryItemReward> inventoryItemList = new ArrayList<>();

        private final List<WrappedCarReward> carRewardList = new ArrayList<>();

        public List<ItemRewardCash> getCashRewardList() {
            return cashRewardList;
        }

        public List<WrappedInventoryItemReward> getInventoryItemList() {
            return inventoryItemList;
        }

        public List<WrappedCarReward> getCarRewardList() {
            return carRewardList;
        }
    }

    /**
     * Reward builder for cash rewards
     */
    public static class CashRewardBuilder extends RewardBuilder<ItemRewardCash> {

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
     * Base class for a reward builder
     *
     * @param <T> Reward object type
     */
    private abstract static class RewardBuilder<T extends ItemRewardBase> {
        public abstract T build();
    }

    /**
     * Exposes access to builder objects for rewards.
     */
    @SuppressWarnings("unused")
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

            return rewards.get(random.nextInt(rewards.size()));
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
                Map<Long, Double> weights = new HashMap<>();
                double defaultWeight = 1.0d / numProducts;

                for (ProductEntity productEntity : productEntities) {
                    Double dropWeight = productEntity.getDropWeight();
                    weights.put(productEntity.getId(), dropWeight == null ? defaultWeight : dropWeight);
                }
                double weightSum = weights.values().stream().mapToDouble(d -> d).sum();

                int randomIndex = -1;
                double random = Math.random() * weightSum;

                for (int i = 0; i < productEntities.size(); i++) {
                    random -= weights.get(productEntities.get(i).getId());

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
                    productEntities.get(random.nextInt(numProducts)),
                    quantity);
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
                        EngineExceptionCode.LuckyDrawContextNotFoundOrEmpty, true);
            }

            int numItems = items.size();

            if (this.weighted) {
                Map<Long, Double> weights = new HashMap<>();
                double defaultWeight = 1.0d / numItems;

                for (RewardTableItemEntity rewardTableItemEntity : items) {
                    Double dropWeight = rewardTableItemEntity.getDropWeight();
                    weights.put(rewardTableItemEntity.getId(), dropWeight == null ? defaultWeight : dropWeight);
                }
                double weightSum = weights.values().stream().mapToDouble(d -> d).sum();

                int randomIndex = -1;
                double random = Math.random() * weightSum;

                for (int i = 0; i < items.size(); i++) {
                    random -= weights.get(items.get(i).getId());

                    if (random <= 0.0d) {
                        randomIndex = i;
                        break;
                    }
                }

                if (randomIndex == -1) {
                    throw new EngineException("Weighted random failed for " + this.tableName,
                            EngineExceptionCode.LuckyDrawCouldNotDrawProduct, true);
                }

                RewardTableItemEntity rewardTableItemEntity = items.get(randomIndex);
                try {
                    return scriptToItem(rewardTableItemEntity.getScript());
                } catch (Exception e) {
                    throw new EngineException("Could not execute script: " + rewardTableItemEntity.getScript(), e, EngineExceptionCode.LuckyDrawCouldNotDrawProduct, true);
                }
            }

            RewardTableItemEntity rewardTableItemEntity = items.get(random.nextInt(numItems));

            try {
                return scriptToItem(rewardTableItemEntity.getScript());
            } catch (Exception e) {
                throw new EngineException("Could not execute script: " + rewardTableItemEntity.getScript(), e, EngineExceptionCode.LuckyDrawCouldNotDrawProduct, true);
            }
        }
    }

    /**
     * Reward builder for random selections
     */
    @SuppressWarnings("unused")
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
            return this.choices.get(random.nextInt(this.choices.size()));
        }
    }
}
