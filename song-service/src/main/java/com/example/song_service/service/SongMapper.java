package com.example.song_service.service;

import com.example.song_service.model.SongMetadataResponseDto;
import com.example.song_service.model.SongModel;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {

    public SongModel toModel(Metadata metadata) {
        return SongModel.builder()
                .resourceId(Integer.parseInt(metadata.get("resourceId")))
                .artist(metadata.get("xmpDM:artist"))
                .album(metadata.get("xmpDM:album"))
                .year(metadata.get("xmpDM:releaseDate"))
                .duration(formatDuration(metadata.get("xmpDM:duration")))
                .title(metadata.get("dc:title"))
                .build();
    }

    public SongMetadataResponseDto toDto(SongModel model) {
        return new SongMetadataResponseDto(
                model.getId(),
                model.getArtist(),
                model.getAlbum(),
                model.getDuration(),
                model.getResourceId(),
                model.getYear(),
                model.getTitle()
        );
    }

    private String formatDuration(String durationInMillis) {
        if (durationInMillis != null && !durationInMillis.isEmpty()) {
            double milliseconds = Double.parseDouble(durationInMillis);
            long seconds = (long) (milliseconds / 1000) % 60;
            long minutes = (long) (milliseconds / (1000 * 60)) % 60;
            return String.format("%02d:%02d", minutes, seconds);
        }
        return null;
    }
}
