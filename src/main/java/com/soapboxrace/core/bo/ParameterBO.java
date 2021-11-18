/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.ParameterDAO;
import com.soapboxrace.core.jpa.ParameterEntity;
import com.soapboxrace.core.jpa.UserEntity;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Startup
@Singleton
@Lock(LockType.READ)
public class ParameterBO {

    @EJB
    private ParameterDAO parameterDao;

    @Inject
    private Logger logger;
    private final ConcurrentMap<String, String> parameterMap;
    public ParameterBO() {
        parameterMap = new ConcurrentHashMap<>();
    }
    
    @PostConstruct
    public void init() {
        loadParameters();
    }
    /**
     * Loads parameters from the database
     */
    public void loadParameters() {
        parameterMap.clear();
        for (ParameterEntity parameterEntity : parameterDao.findAll()) {
            if (parameterEntity.getValue() != null)
                parameterMap.put(parameterEntity.getName(), parameterEntity.getValue());
        }
        logger.info("Loaded {} parameters from database", parameterMap.size());
    }

    private String getParameter(String name) {
        return parameterMap.get(name);
    }

    public int getCarLimit(UserEntity userEntity) {
        return userEntity.getMaxCarSlots();
    }

    public int getMaxCash(UserEntity userEntity) {
        if (userEntity.isPremium()) {
            return getIntParam("MAX_PLAYER_CASH_PREMIUM", 9_999_999);
        }
        return getIntParam("MAX_PLAYER_CASH_FREE", 9_999_999);
    }

    public int getMaxLevel(UserEntity userEntity) {
        if (userEntity.isPremium()) {
            return getIntParam("MAX_PLAYER_LEVEL_PREMIUM", 60);
        }
        return getIntParam("MAX_PLAYER_LEVEL_FREE", 60);
    }

    public Integer getIntParam(String parameter) {
        String parameterFromDB = getParameter(parameter);

        if (parameterFromDB == null) {
            throw new RuntimeException("Cannot find integer parameter: " + parameter);
        }

        return Integer.valueOf(parameterFromDB);
    }

    public Boolean getBoolParam(String parameter) {
        String parameterFromDB = getParameter(parameter);

        if (parameterFromDB == null || parameterFromDB.isEmpty()) {
            setParameter(parameter, Boolean.FALSE.toString());
        }

        return Boolean.valueOf(parameterFromDB);
    }

    public String getStrParam(String parameter) {
        String parameterFromDB = getParameter(parameter);

        if (parameterFromDB == null || parameterFromDB.isEmpty()) {
            setParameter(parameter, "");
        }

        return parameterFromDB;
    }

    public List<String> getStrListParam(String parameter) {
        return getStrListParam(parameter, Collections.emptyList());
    }

    public Float getFloatParam(String parameter) {
        String parameterFromDB = getParameter(parameter);

        if (parameterFromDB == null) {
            throw new RuntimeException("Cannot find float parameter: " + parameter);
        }

        return Float.valueOf(parameterFromDB);
    }


    public Integer getIntParam(String parameter, Integer defaultValue) {
        String parameterFromDB = getParameter(parameter);

        if (parameterFromDB == null || parameterFromDB.isEmpty()) {
            setParameter(parameter, parameterFromDB);
            return defaultValue;
        }

        return Integer.valueOf(parameterFromDB);
    }

    public String getStrParam(String parameter, String defaultValue) {
        String parameterFromDB = getParameter(parameter);

        if (parameterFromDB == null || parameterFromDB.isEmpty()) {
            setParameter(parameter, defaultValue);
            return defaultValue;
        }

        return parameterFromDB;
    }

    public List<String> getStrListParam(String parameter, List<String> defaultValue) {
        String parameterFromDB = getParameter(parameter);

        if (parameterFromDB == null || parameterFromDB.isEmpty()) {
            setParameter(parameter, defaultValue.toString());
            return defaultValue;
        }

        return Arrays.asList(parameterFromDB.split(";"));
    }

    public Float getFloatParam(String parameter, Float defaultValue) {
        String parameterFromDB = getParameter(parameter);

        if(parameterFromDB == null || parameterFromDB.isEmpty()) {
            setParameter(parameter, defaultValue.toString());
            return defaultValue;
        }
        
        return Float.valueOf(parameterFromDB);
    }

    public void setParameter(String name, String value) {
        parameterMap.put(name, value);

        ParameterEntity entity = new ParameterEntity();
        entity.setName(name);
        entity.setValue(value);
        parameterDao.update(entity);
    }
}