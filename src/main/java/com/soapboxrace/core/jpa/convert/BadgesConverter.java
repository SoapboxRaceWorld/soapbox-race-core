package com.soapboxrace.core.jpa.convert;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.soapboxrace.jaxb.http.BadgePacket;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

public class BadgesConverter implements AttributeConverter<List<BadgePacket>, String>
{
    @Override
    public String convertToDatabaseColumn(List<BadgePacket> attribute)
    {
        JsonArray jsonArray = new JsonArray();

        if (attribute != null) {
            for (BadgePacket packet : attribute) {
                JsonObject badgeObject = new JsonObject();

                badgeObject.addProperty("achievementRankId", packet.getAchievementRankId());
                badgeObject.addProperty("badgeDefinitionId", packet.getBadgeDefinitionId());
                badgeObject.addProperty("isRare", packet.isIsRare());
                badgeObject.addProperty("rarity", packet.getRarity());
                badgeObject.addProperty("slot", packet.getSlotId());

                jsonArray.add(badgeObject);
            }
        }

        return jsonArray.toString();
    }

    @Override
    public List<BadgePacket> convertToEntityAttribute(String dbData)
    {
        if (dbData == null) dbData = "";
        
        dbData = dbData.trim();

        if (dbData.isEmpty())
            return new ArrayList<>();

        List<BadgePacket> list = new ArrayList<>();

        JsonArray jsonArray = new JsonParser().parse(dbData).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();

            BadgePacket packet = new BadgePacket();
            packet.setAchievementRankId(object.get("achievementRankId").getAsInt());
            packet.setBadgeDefinitionId(object.get("badgeDefinitionId").getAsInt());
            packet.setIsRare(object.get("isRare").getAsBoolean());
            packet.setRarity(object.get("rarity").getAsFloat());
            packet.setSlotId(object.get("slot").getAsShort());

            list.add(packet);
        }

        return list;
    }
}
