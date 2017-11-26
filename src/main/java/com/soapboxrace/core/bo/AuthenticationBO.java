package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.BanDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.UserEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AuthenticationBO
{
    @EJB
    private BanDAO banDAO;

    public BanEntity checkUserBan(UserEntity userEntity)
    {
        return banDAO.findByUser(userEntity);
    }

    public BanEntity checkNonUserBan(String hwid, String ip, String email)
    {
        BanEntity banEntity;
        if ((banEntity = banDAO.findByHWID(hwid)) != null)
            return banEntity;
        else if ((banEntity = banDAO.findByIp(ip)) != null)
            return banEntity;
        else if ((banEntity = banDAO.findByEmail(email)) != null)
            return banEntity;
        return null;
    }
}
