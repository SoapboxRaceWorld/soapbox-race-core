package com.soapboxrace.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONUtils {
    private static Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    public static <T> T deserialize(String json) {
        return gson.fromJson(json, new TypeToken<T>(){}.getType());
    }
    
    public static <T> String serialize(T obj) {
        return gson.toJson(obj, new TypeToken<T>(){}.getType());
    }
}
