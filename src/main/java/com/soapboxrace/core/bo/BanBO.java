package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.BanDAO;
import com.soapboxrace.core.dao.HardwareInfoDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.HardwareInfoEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.UserEntity;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Stateless
public class BanBO {

    @EJB
    private BanDAO banDAO;

    @EJB
    private HardwareInfoDAO hardwareInfoDAO;

    @Schedule(minute = "*", hour = "*")
    public void checkExpiredBans() {
        List<BanEntity> expiredBans = banDAO.findAllExpired();

        for (BanEntity banEntity : expiredBans) {
            expireBan(banEntity);
        }
    }

    public BanEntity getUserBan(UserEntity userEntity) {
        return banDAO.findByUser(userEntity);
    }

    public boolean isBanned(UserEntity userEntity) {
        return getUserBan(userEntity) != null;
    }

    public void unbanUser(UserEntity userEntity) {
        BanEntity banEntity = banDAO.findByUser(userEntity);

        if (banEntity != null) {
            expireBan(banEntity);
        }
    }

    public void banUser(UserEntity userToBan, PersonaEntity adminPersona, String banReason, LocalDateTime banExpiration) {
        BanEntity banEntity = new BanEntity();
        banEntity.setUserEntity(userToBan);
        banEntity.setEndsAt(banExpiration);
        banEntity.setStarted(LocalDateTime.now());
        banEntity.setReason(banReason);
        banEntity.setBannedBy(adminPersona);
        banEntity.setActive(true);
        banDAO.insert(banEntity);

        HardwareInfoEntity hardwareInfoEntity = hardwareInfoDAO.findByUserId(userToBan.getId());

        if (hardwareInfoEntity != null) {
            hardwareInfoEntity.setBanned(true);
            hardwareInfoDAO.update(hardwareInfoEntity);
        }
    }

    public void expireBan(BanEntity banEntity) {
        Objects.requireNonNull(banEntity);
        UserEntity userEntity = banEntity.getUserEntity();

        if (userEntity.getGameHardwareHash() != null) {
            HardwareInfoEntity hardwareInfoEntity = hardwareInfoDAO.findByHardwareHash(userEntity.getGameHardwareHash());
            hardwareInfoEntity.setBanned(false);
            hardwareInfoDAO.update(hardwareInfoEntity);
        }
        banEntity.setActive(false);
        banDAO.update(banEntity);
    }
}
