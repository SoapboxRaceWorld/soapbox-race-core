/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.ProductBO;
import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.core.jpa.CategoryEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.ArrayOfCategoryTrans;
import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.http.CategoryTrans;
import com.soapboxrace.jaxb.http.ProductTrans;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/catalog")
public class Catalog {

    @EJB
    private ProductBO productBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @GET
    @Secured
    @Path("/productsInCategory")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfProductTrans productsInCategory(@HeaderParam("securityToken") String securityToken, @QueryParam(
            "categoryName") String categoryName,
                                                  @QueryParam("clientProductType") String clientProductType) {
        ArrayOfProductTrans arrayOfProductTrans = new ArrayOfProductTrans();
        List<ProductEntity> productsInCategory = productBO.productsInCategory(categoryName, clientProductType,
                requestSessionInfo.getActivePersonaId());
        List<ProductTrans> productTransList = productBO.getProductTransList(productsInCategory);
        arrayOfProductTrans.getProductTrans().addAll(productTransList);
        return arrayOfProductTrans;
    }

    @GET
    @Secured
    @Path("/categories")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfCategoryTrans categories(@HeaderParam("securityToken") String securityToken) {
        Long activePersonaId = requestSessionInfo.getActivePersonaId();
        ArrayOfCategoryTrans arrayOfCategoryTrans = new ArrayOfCategoryTrans();
        List<CategoryEntity> listCategoryEntity = productBO.categories();
        for (CategoryEntity entity : listCategoryEntity) {
            CategoryTrans categoryTrans = new CategoryTrans();
            categoryTrans.setCatalogVersion(Integer.parseInt(entity.getCatalogVersion()));
            categoryTrans.setDisplayName(entity.getDisplayName());
            categoryTrans.setFilterType(entity.getFilterType());
            categoryTrans.setIcon(entity.getIcon());
            categoryTrans.setId(entity.getIdentifiant().toString());
            categoryTrans.setLongDescription(entity.getLongDescription());
            categoryTrans.setName(entity.getName());
            categoryTrans.setPriority(entity.getPriority());
            categoryTrans.setProducts(productBO.getVinylByCategory(entity, activePersonaId));
            categoryTrans.setShortDescription(entity.getShortDescription());
            categoryTrans.setShowInNavigationPane(entity.getShowInNavigationPane());
            categoryTrans.setShowPromoPage(entity.getShowPromoPage());
            categoryTrans.setWebIcon(entity.getWebIcon());
            arrayOfCategoryTrans.getCategoryTrans().add(categoryTrans);
        }
        return arrayOfCategoryTrans;
    }
}
