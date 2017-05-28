package com.soapboxrace.core.api;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.ProductBO;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.ArrayOfCategoryTrans;
import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.http.ProductTrans;

@Path("/catalog")
public class Catalog {

	@EJB
	private ProductBO productBO;

	@GET
	@Secured
	@Path("/productsInCategory")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfProductTrans productsInCategory(@QueryParam("categoryName") String categoryName, @QueryParam("clientProductType") String clientProductType) {
		ArrayOfProductTrans arrayOfProductTrans = new ArrayOfProductTrans();
		List<ProductEntity> productsInCategory = productBO.productsInCategory(categoryName, clientProductType);
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
	public ArrayOfCategoryTrans categories() {
		return new ArrayOfCategoryTrans();
	}
}
