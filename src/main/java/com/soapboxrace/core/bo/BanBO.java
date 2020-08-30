package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.BanDAO;
import com.soapboxrace.core.jpa.BanEntity;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class BanBO {

    @EJB
    private BanDAO banDAO;

    @Schedule(minute = "*", hour = "*")
    public void checkExpiredBans() {
        List<BanEntity> expiredBans = banDAO.findAllExpired();

        for (BanEntity banEntity : expiredBans) {

        }
    }
}
