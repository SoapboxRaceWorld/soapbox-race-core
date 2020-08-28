package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.UsedPowerupEntity;

import javax.ejb.Stateless;

@Stateless
public class UsedPowerupDAO extends LongKeyedDAO<UsedPowerupEntity> {

    public UsedPowerupDAO() {
        super(UsedPowerupEntity.class);
    }
}
