package com.example.song_service.service;

import com.example.song_service.model.SongMetadataResponseDto;
import com.example.song_service.model.SongModel;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SongMapper {

    public SongModel toModel(Metadata metadata) {
        return SongModel.builder()
                .resourceId(Integer.parseInt(metadata.get("resourceId")))
                .artist(Objects.isNull(metadata.get("xmpDM:artist")) ? metadata.get("artist") : metadata.get("xmpDM:artist"))
                .album(Objects.isNull(metadata.get("xmpDM:album")) ? metadata.get("album") : metadata.get("xmpDM:album"))
                .year(Objects.isNull(metadata.get("xmpDM:releaseDate")) ? metadata.get("year") : metadata.get("xmpDM:releaseDate"))
                .duration(formatDuration(Objects.isNull(metadata.get("xmpDM:duration")) ? formatDuration(metadata.get("duration")) : formatDuration(metadata.get("xmpDM:duration"))))
                .title(Objects.isNull(metadata.get("dc:title")) ? metadata.get("title") : metadata.get("dc:title"))
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

    private String formatDuration(String duration) {
        if (duration != null && !duration.isEmpty()) {
            try {
                double milliseconds = Double.parseDouble(duration);
                long seconds = (long) (milliseconds / 1000) % 60;
                long minutes = (long) (milliseconds / (1000 * 60)) % 60;
                return String.format("%02d:%02d", minutes, seconds);
            } catch (NumberFormatException ex) {
                return duration;
            }
        }
        return null;
    }
}
