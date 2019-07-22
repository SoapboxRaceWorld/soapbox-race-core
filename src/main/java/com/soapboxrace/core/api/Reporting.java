package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.HardwareInfoBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.HardwareInfoEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.http.HardwareInfo;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/Reporting")
public class Reporting {

    @EJB
    private HardwareInfoBO hardwareInfoBO;

    @EJB
    private TokenSessionBO tokenBO;

    @EJB
    private UserDAO userDAO;

    @POST
    @Secured
    @Path("/SendHardwareInfo")
    @Produces(MediaType.APPLICATION_XML)
    public Response sendHardwareInfo(InputStream is, @HeaderParam("securityToken") String securityToken) {
        HardwareInfo hardwareInfo = UnmarshalXML.unMarshal(is, HardwareInfo.class);
        HardwareInfoEntity hardwareInfoEntity = hardwareInfoBO.save(hardwareInfo);
        UserEntity user = tokenBO.getUser(securityToken);
        user.setGameHardwareHash(hardwareInfoEntity.getHardwareHash());
        userDAO.update(user);
        return Response.ok().build();
    }

    @POST
    @Secured
    @Path("/SendUserSettings")
    @Produces(MediaType.APPLICATION_XML)
    public Response sendUserSettings() {
        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("/SendMultiplayerConnect")
    @Produces(MediaType.APPLICATION_XML)
    public Response sendMultiplayerConnect() {
        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("/SendClientPingTime")
    @Produces(MediaType.APPLICATION_XML)
    public Response sendClientPingTime() {
        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("/LoginAnnouncementClicked")
    @Produces(MediaType.APPLICATION_XML)
    public Response loginAnnouncementClicked() {
        return Response.ok().build();
    }

    @PUT
    @Path("{path:.*}")
    @Produces(MediaType.APPLICATION_XML)
    public Response genericEmptyPut(@PathParam("path") String path) {
        System.out.println("empty PUT!!!");
        return Response.ok().build();
    }
}
