package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.http.ProductTrans;

@Path("/catalog")
public class Catalog {

	@GET
	@Path("/productsInCategory")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfProductTrans productsInCategory(@QueryParam("categoryName") String categoryName, @QueryParam("clientProductType") String clientProductType) {
		ArrayOfProductTrans arrayOfProductTrans = new ArrayOfProductTrans();
		// categoryName=Starting_Cars&clientProductType=PRESETCAR
		if ("Starting_Cars".equals(categoryName) && "PRESETCAR".equals(clientProductType)) {
			ProductTrans productTrans = new ProductTrans();
			productTrans.setCurrency("CASH");
			productTrans.setDurationMinute(0);
			productTrans.setHash(1133182666);
			productTrans.setIcon("Black_64x64");
			productTrans.setLevel(0);
			productTrans.setPrice(200000.00D);
			productTrans.setPriority(0);
			productTrans.setProductId("SRV-SCAR7");
			productTrans.setProductTitle("BLACK");
			productTrans.setProductType("PRESETCAR");
			productTrans.setUseCount(1);
			arrayOfProductTrans.getProductTrans().add(productTrans);
		}
		return arrayOfProductTrans;
	}
}
