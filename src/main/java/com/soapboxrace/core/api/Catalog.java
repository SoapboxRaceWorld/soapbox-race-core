package com.soapboxrace.core.api;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.ProductBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.jpa.CategoryEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.ArrayOfCategoryTrans;
import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.http.CategoryTrans;
import com.soapboxrace.jaxb.http.ProductTrans;

@Path("/catalog")
public class Catalog {

	@EJB
	private ProductBO productBO;

	@EJB
	private TokenSessionBO tokenBO;

	@GET
	@Secured
	@Path("/productsInCategory")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfProductTrans productsInCategory(@HeaderParam("securityToken") String securityToken, @QueryParam("categoryName") String categoryName,
			@QueryParam("clientProductType") String clientProductType) {
		ArrayOfProductTrans arrayOfProductTrans = new ArrayOfProductTrans();
		Long activePersonaId = tokenBO.getActivePersonaId(securityToken);
		List<ProductEntity> productsInCategory = productBO.productsInCategory(categoryName, clientProductType, activePersonaId);
		for (ProductEntity productEntity : productsInCategory) {
			ProductTrans productTrans = new ProductTrans();
			productTrans.setCurrency(productEntity.getCurrency());
			productTrans.setDurationMinute(productEntity.getDurationMinute());
			productTrans.setHash(productEntity.getHash().intValue());
			productTrans.setIcon(productEntity.getIcon());
			productTrans.setLevel(productEntity.getLevel());
			productTrans.setPrice(productEntity.getPrice());
			productTrans.setPriority(productEntity.getPriority());
			productTrans.setProductId(productEntity.getProductId());
			productTrans.setProductTitle(productEntity.getProductTitle());
			productTrans.setProductType(productEntity.getProductType());
			productTrans.setUseCount(productEntity.getUseCount());
			arrayOfProductTrans.getProductTrans().add(productTrans);
		}
		return arrayOfProductTrans;
	}

	@GET
	@Secured
	@Path("/categories")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfCategoryTrans categories(@HeaderParam("securityToken") String securityToken) {
		Long activePersonaId = tokenBO.getActivePersonaId(securityToken);
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
