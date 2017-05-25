package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.jpa.ProductEntity;

@Stateless
public class ProductBO {

	@EJB
	private ProductDAO ProductDAO;

	public List<ProductEntity> productsInCategory(String categoryName, String productType) {
		return ProductDAO.findByLevelEnabled(categoryName, productType, 1, true);
	}

}
