/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.ParameterDAO;
import com.soapboxrace.core.jpa.ParameterEntity;
import com.soapboxrace.core.jpa.UserEntity;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Stateless
public class ParameterBO {

    @EJB
    private ParameterDAO parameterDao;

	private String getParameter(String name) {
		try {
            for (ParameterEntity parameterEntity : parameterDao.findAll()) {
                if (parameterEntity.getName().trim().equals(name)) {
                    return parameterEntity.getValue();
                }
            }
		} catch (Exception e) {
            System.out.println("Unknown parameter name: " + name);
		}
		return null;
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

    public Integer getIntParam(String parameter, Integer defaultValue) {
        String parameterFromDB = getParameter(parameter);
        return parameterFromDB == null || parameterFromDB.isEmpty() ? defaultValue : Integer.valueOf(parameterFromDB);
    }

    public Boolean getBoolParam(String parameter) {
        String parameterFromDB = getParameter(parameter);
        return Boolean.valueOf(parameterFromDB);
    }

    public String getStrParam(String parameter) {
        return getParameter(parameter);
    }

    public String getStrParam(String parameter, String defaultValue) {
        String parameterFromDB = getParameter(parameter);

        return parameterFromDB == null || parameterFromDB.isEmpty() ? defaultValue : parameterFromDB;
    }

    public List<String> getStrListParam(String parameter) {
        String value = getParameter(parameter);

        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.asList(value.split(";"));
    }

    public Float getFloatParam(String parameter) {
        String parameterFromDB = getParameter(parameter);

        if (parameterFromDB == null) {
            throw new RuntimeException("Cannot find float parameter: " + parameter);
        }

        return Float.valueOf(parameterFromDB);
    }

    public Float getFloatParam(String parameter, Float defaultValue) {
        String parameterFromDB = getParameter(parameter);
        return parameterFromDB == null || parameterFromDB.isEmpty() ? defaultValue : Float.valueOf(parameterFromDB);
    }
}