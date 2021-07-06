/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.google.common.base.Charsets;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class Argon2BO {
    @EJB
    private ParameterBO parameterBO;

    private Argon2 argon2 = Argon2Factory.create();
    private String paramString;
    private int timeCost;
    private int memoryCost;
    private int parallelism;
    private Integer iterations;

    private void loadParameters() {
        paramString = parameterBO.getStrParam("ARGON2_PARAMS", "500:16384:1");
        String[] splits = paramString.split(":");
        if (splits.length < 3 || splits.length > 4) {
            throw new RuntimeException("Invalid Argon2 parameters");
        }
        try {
            this.timeCost = Integer.parseInt(splits[0]);
            this.memoryCost = Integer.parseInt(splits[1]);
            this.parallelism = Integer.parseInt(splits[2]);
            if (splits.length == 4) {
                this.iterations = Integer.parseInt(splits[3]);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid Argon2 parameters");
        }
    }

    @PostConstruct
    public void init() {
        loadParameters();
        if (iterations == null) {
            iterations = Argon2Helper.findIterations(argon2, timeCost, memoryCost, parallelism);
            paramString += ":" + iterations.toString();
            parameterBO.setParameter("ARGON2_PARAMS", paramString);
        }
        LoggerFactory.getLogger("Argon2").info(
                "Initialized. timeCost = {}, memoryCost = {}, parallelism = {}, iterations = {}",
                timeCost, memoryCost, parallelism, iterations);
    }

    public String hash(String password) {
        byte[] bytes = password.getBytes(Charsets.UTF_8);
        String hash = argon2.hash(iterations, memoryCost, parallelism, bytes);
        argon2.wipeArray(bytes);
        return hash;
    }

    public boolean verify(String password, String hash) {
        byte[] bytes = password.getBytes(Charsets.UTF_8);
        boolean verifyResult = argon2.verify(hash, bytes);
        argon2.wipeArray(bytes);
        return verifyResult;
    }

    public boolean needsRehash(String hash) {
        return argon2.needsRehash(hash, iterations, memoryCost, parallelism);
    }
}