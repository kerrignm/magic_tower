package com.game.magictower.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    
    private static Gson sGson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
    
    public static <T> T fromJson(String value,Class<T> cls) {
        return sGson.fromJson(value,cls);
    }
    
    public static <T> T fromJson(String value, Type typeOfT) {
        return sGson.fromJson(value, typeOfT);
    }

    public static <T> String toJson(T object) {
        return sGson.toJson(object);
    }
}
