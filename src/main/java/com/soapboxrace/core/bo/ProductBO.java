package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CategoryDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.VinylProductDAO;
import com.soapboxrace.core.jpa.CategoryEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.core.jpa.VinylProductEntity;
import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.http.ProductTrans;

@Stateless
public class ProductBO {

	@EJB
	private ProductDAO ProductDAO;
	
	@EJB
	private CategoryDAO categoryDao;

	@EJB
	private VinylProductDAO vinylProductDao;
	
	public List<ProductEntity> productsInCategory(String categoryName, String productType) {
		return ProductDAO.findByLevelEnabled(categoryName, productType, 1, true);
	}
	
	public List<CategoryEntity> categories() {
		return categoryDao.getAll();
	}
	
	public ArrayOfProductTrans getVinylByCategory(CategoryEntity categoryEntity) {
		ArrayOfProductTrans arrayOfProductTrans = new ArrayOfProductTrans();
		List<VinylProductEntity> vinylProductEntity = vinylProductDao.findByCategoryLevelEnabled(categoryEntity, 1, true);
		for (VinylProductEntity entity : vinylProductEntity) {
			ProductTrans productTrans = new ProductTrans();
			productTrans.setCurrency(entity.getCurrency());
			productTrans.setDurationMinute(entity.getDurationMinute());
			productTrans.setHash((int) entity.getHash());
			productTrans.setIcon(entity.getIcon());
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

}
