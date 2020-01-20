/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.LuckyDrawItem;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Random;

@Stateless
public class DropBO {
    @EJB
    private ProductBO productBO;

    public ProductEntity getRandomProductItem() {
        String[] productTypeArr = {"PERFORMANCEPART", "POWERUP", "SKILLMODPART", "VISUALPART"};
        Random random = new Random();
        int number = random.nextInt(productTypeArr.length);
        return productBO.getRandomDrop(productTypeArr[number]);
    }

    public LuckyDrawItem copyProduct2LuckyDraw(ProductEntity productEntity) {
        LuckyDrawItem luckyDrawItem = new LuckyDrawItem();
        luckyDrawItem.setDescription(productEntity.getProductTitle());
        luckyDrawItem.setHash(productEntity.getHash());
        luckyDrawItem.setIcon(productEntity.getIcon());
        luckyDrawItem.setRemainingUseCount(productEntity.getUseCount());
        luckyDrawItem.setResellPrice(productEntity.getResalePrice());
        luckyDrawItem.setVirtualItem(DigestUtils.md5Hex(productEntity.getHash().toString()));
        luckyDrawItem.setVirtualItemType(productEntity.getProductType());
        luckyDrawItem.setWasSold(false);
        return luckyDrawItem;
    }
}
