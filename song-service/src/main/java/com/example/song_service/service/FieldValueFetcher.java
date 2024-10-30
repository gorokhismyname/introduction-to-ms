package com.example.song_service.service;

import com.example.song_service.model.SongModel;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class FieldValueFetcher {
    public static String getFieldValueByName(String fieldName, SongModel model) {
        try {
            Field declaredField = model.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            return (String) declaredField.get(model);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
