/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CategoryDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.VinylProductDAO;
import com.soapboxrace.core.jpa.CategoryEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.core.jpa.VinylProductEntity;
import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.http.ProductTrans;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductBO {

    @EJB
    private ProductDAO productDAO;

    @EJB
    private CategoryDAO categoryDao;

    @EJB
    private VinylProductDAO vinylProductDao;

    @EJB
    private PersonaDAO personaDao;

    public List<ProductTrans> getProductTransList(List<ProductEntity> productEntities) {
        List<ProductTrans> productTransList = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            productTransList.add(productEntityToProductTrans(productEntity));
        }

        return productTransList;
    }

    private ProductTrans productEntityToProductTrans(ProductEntity productEntity) {
        ProductTrans productTrans = new ProductTrans();
        productTrans.setBundleItems(new ArrayOfProductTrans());
        productTrans.setCurrency(productEntity.getCurrency());
        productTrans.setDurationMinute(productEntity.getDurationMinute());
        productTrans.setDescription(productEntity.getDescription());
        productTrans.setHash(productEntity.getHash());
        productTrans.setIcon(productEntity.getIcon());
        productTrans.setSecondaryIcon(productEntity.getSecondaryIcon());
        productTrans.setLevel(productEntity.getLevel());
        productTrans.setLongDescription(productEntity.getLongDescription());
        productTrans.setPrice(productEntity.getPrice());
        productTrans.setPriority(productEntity.getPriority());
        productTrans.setProductId(productEntity.getProductId());
        productTrans.setProductTitle(productEntity.getProductTitle());
        productTrans.setProductType(productEntity.getProductType());
        productTrans.setUseCount(productEntity.getUseCount());
        productTrans.setSecondaryIcon(productEntity.getSecondaryIcon());
        productTrans.setVisualStyle(productEntity.getVisualStyle());
        productTrans.setWebIcon(productEntity.getWebIcon());
        productTrans.setWebLocation(productEntity.getWebLocation());

        for (ProductEntity bundledProductEntity : productEntity.getBundleItems()) {
            productTrans.getBundleItems().getProductTrans().add(productEntityToProductTrans(bundledProductEntity));
        }

        return productTrans;
    }

    public List<ProductEntity> productsInCategory(String categoryName, String productType, Long personaId) {
        boolean premium = false;
        int level = 1;
        if (personaId != null && !personaId.equals(0L)) {
            PersonaEntity personaEntity = personaDao.find(personaId);
            premium = personaEntity.getUser().isPremium();
            level = personaEntity.getLevel();
        }
        List<ProductEntity> productEntities = productDAO.findByLevelEnabled(categoryName, productType, level, true, premium);

        for (ProductEntity product : productEntities) {
            product.getBundleItems().size();
        }
        return productEntities;
    }

    public ProductEntity getRandomDrop(String productType) {
        List<ProductEntity> productEntities = productDAO.findDropsByType(productType);

        if (productEntities.isEmpty()) {
            throw new RuntimeException("No droppable products of type '" + productType + "' to work with!");
        }

        double weightSum = productEntities.stream().mapToDouble(p -> getDropWeight(p, productEntities)).sum();

        int randomIndex = -1;
        double random = Math.random() * weightSum;

        for (int i = 0; i < productEntities.size(); i++) {
            random -= getDropWeight(productEntities.get(i), productEntities);

            if (random <= 0.0d) {
                randomIndex = i;
                break;
            }
        }

        if (randomIndex == -1) {
            throw new RuntimeException("Random selection failed for products of type " + productType + " (weightSum=" + weightSum + ")");
        }

        return productEntities.get(randomIndex);
    }

    public List<CategoryEntity> categories() {
        return categoryDao.getAll();
    }

    public ArrayOfProductTrans getVinylByCategory(CategoryEntity categoryEntity, Long personaId) {
        boolean premium = false;
        int level = 1;
        if (personaId != null && !personaId.equals(0L)) {
            PersonaEntity personaEntity = personaDao.find(personaId);
            premium = personaEntity.getUser().isPremium();
            level = personaEntity.getLevel();
        }
        ArrayOfProductTrans arrayOfProductTrans = new ArrayOfProductTrans();
        List<VinylProductEntity> vinylProductEntity = vinylProductDao.findByCategoryLevelEnabled(categoryEntity,
                level, true, premium);
        for (VinylProductEntity entity : vinylProductEntity) {
            ProductTrans productTrans = new ProductTrans();
            productTrans.setCurrency(entity.getCurrency());
            productTrans.setDurationMinute(entity.getDurationMinute());
            productTrans.setHash(entity.getHash());
            productTrans.setIcon(entity.getIcon());
            productTrans.setSecondaryIcon(entity.getSecondaryIcon());
            productTrans.setLevel(entity.getLevel());
            productTrans.setPrice(entity.getPrice());
            productTrans.setPriority(entity.getPriority());
            productTrans.setProductId(entity.getProductId());
            productTrans.setProductTitle(entity.getProductTitle());
            productTrans.setProductType(entity.getProductType());
            productTrans.setUseCount(entity.getUseCount());
            arrayOfProductTrans.getProductTrans().add(productTrans);
        }
        return arrayOfProductTrans;
    }

    private double getDropWeight(ProductEntity p, List<ProductEntity> productEntities) {
        if (p.getDropWeight() == null) {
            return 1.0d / productEntities.size();
        }

        return p.getDropWeight();
    }
}
