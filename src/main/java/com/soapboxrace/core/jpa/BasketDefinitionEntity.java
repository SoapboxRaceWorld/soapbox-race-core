package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "BASKETDEFINITION")
public class BasketDefinitionEntity {

    @Id
    private String productId;

    @Lob
    @Column(length = 65535)
    private String ownedCarTrans;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOwnedCarTrans() {
        return ownedCarTrans;
    }

    public void setOwnedCarTrans(String ownedCarTrans) {
        this.ownedCarTrans = ownedCarTrans;
    }

}
