package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.PresenceManager;
import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.jaxb.annotation.XsiSchemaLocation;
import com.soapboxrace.jaxb.http.ArrayOfFriendPersona;
import com.soapboxrace.jaxb.http.FriendPersona;
import com.soapboxrace.jaxb.http.PersonaFriendsList;

import java.util.List;

@Path("/getfriendlistfromuserid")
public class GetFriendListFromUserId
{
    @EJB
    private FriendDAO friendDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireRestApiCli restApiCli;

    @EJB
    private PresenceManager presenceManager;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public PersonaFriendsList getFriendListFromUserId(@QueryParam("userId") Long userId)
    {
        ArrayOfFriendPersona arrayOfFriendPersona = new ArrayOfFriendPersona();
        List<FriendEntity> friendList = friendDAO.findByUserId(userId);

        for (FriendEntity friendEntity : friendList)
        {
            PersonaEntity personaEntity = personaDAO.findById(friendEntity.getPersonaId());

            if (personaEntity == null) continue;

            FriendPersona friendPersona = new FriendPersona();
            friendPersona.setIconIndex(personaEntity.getIconIndex());
            friendPersona.setLevel(personaEntity.getLevel());
            friendPersona.setName(personaEntity.getName());
            friendPersona.setOriginalName(personaEntity.getName());
            friendPersona.setPersonaId(personaEntity.getPersonaId());

            if (friendEntity.getStatus() == 1)
            {
                friendPersona.setPresence(presenceManager.getPresence(personaEntity.getPersonaId()));
            } else
            {
                friendPersona.setPresence(3);
            }

            friendPersona.setSocialNetwork(0);
            friendPersona.setUserId(personaEntity.getUser().getId());

            arrayOfFriendPersona.getFriendPersona().add(friendPersona);
        }

        PersonaFriendsList personaFriendsList = new PersonaFriendsList();
        personaFriendsList.setFriendPersona(arrayOfFriendPersona);

        return personaFriendsList;
    }
}