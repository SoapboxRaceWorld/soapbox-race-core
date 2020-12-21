/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.ArrayOfCarClass;
import com.soapboxrace.jaxb.http.CarClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/carclasses")
public class CarClasses {

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfCarClass carClasses() {
        ArrayOfCarClass arrayOfCarClass = new ArrayOfCarClass();

        CarClass carClass_S3 = new CarClass();
        carClass_S3.setCarClassHash(1526233495);
        carClass_S3.setMaxRating((short) 1999);
        carClass_S3.setMinRating((short) 1500);
        arrayOfCarClass.getCarClass().add(carClass_S3);
        
        CarClass carClass_S2 = new CarClass();
        carClass_S2.setCarClassHash(221915816);
        carClass_S2.setMaxRating((short) 1499);
        carClass_S2.setMinRating((short) 1250);
        arrayOfCarClass.getCarClass().add(carClass_S2);
        
        CarClass carClass_S1 = new CarClass();
        carClass_S1.setCarClassHash(86241155);
        carClass_S1.setMaxRating((short) 1249);
        carClass_S1.setMinRating((short) 1000);
        arrayOfCarClass.getCarClass().add(carClass_S1);
        
        CarClass carClass_S = new CarClass();
        carClass_S.setCarClassHash(-2142411446);
        carClass_S.setMaxRating((short) 999);
        carClass_S.setMinRating((short) 750);
        arrayOfCarClass.getCarClass().add(carClass_S);

        CarClass carClass_A = new CarClass();
        carClass_A.setCarClassHash(-405837480);
        carClass_A.setMaxRating((short) 749);
        carClass_A.setMinRating((short) 600);
        arrayOfCarClass.getCarClass().add(carClass_A);

        CarClass carClass_B = new CarClass();
        carClass_B.setCarClassHash(-406473455);
        carClass_B.setMaxRating((short) 599);
        carClass_B.setMinRating((short) 500);
        arrayOfCarClass.getCarClass().add(carClass_B);

        CarClass carClass_C = new CarClass();
        carClass_C.setCarClassHash(1866825865);
        carClass_C.setMaxRating((short) 499);
        carClass_C.setMinRating((short) 400);
        arrayOfCarClass.getCarClass().add(carClass_C);

        CarClass carClass_D = new CarClass();
        carClass_D.setCarClassHash(415909161);
        carClass_D.setMaxRating((short) 399);
        carClass_D.setMinRating((short) 250);
        arrayOfCarClass.getCarClass().add(carClass_D);

        CarClass carClass_E = new CarClass();
        carClass_E.setCarClassHash(872416321);
        carClass_E.setMaxRating((short) 249);
        carClass_E.setMinRating((short) 50);
        arrayOfCarClass.getCarClass().add(carClass_E);

        CarClass carClass_F = new CarClass();
        carClass_F.setCarClassHash(869393278);
        carClass_F.setMaxRating((short) 49);
        carClass_F.setMinRating((short) 0);
        arrayOfCarClass.getCarClass().add(carClass_F);

        return arrayOfCarClass;
    }
}