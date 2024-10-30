package com.example.song_service.model;

public enum MetadataField {

    NAME("name"),
    ARTIST("artist"),
    ALBUM("album"),
    LENGTH("length"),
    RESOURCE_ID("resourceId"),
    YEAR("year");

    public final String label;

    private MetadataField(String label) {
        this.label = label;
    }

    }
