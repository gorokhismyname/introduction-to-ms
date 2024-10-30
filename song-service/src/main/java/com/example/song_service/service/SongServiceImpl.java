package com.example.song_service.service;

import com.example.song_service.exception.MetadataFieldValidationException;
import com.example.song_service.exception.SongMetadataNotFoundException;
import com.example.song_service.model.*;
import com.example.song_service.repo.SongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepo songRepo;

    @Autowired
    public SongServiceImpl(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    @Override
    public CreateSongResponseDto saveSongMetadata(CreateSongRequestDto requestDto) {

        Map<String, String> metadata = requestDto.metadata();

        validateMetadata(metadata);

        SongModel songModel = SongModel.builder()
                .name(metadata.get(MetadataField.NAME.label))
                .album(metadata.get(MetadataField.ALBUM.label))
                .artist(metadata.get(MetadataField.ARTIST.label))
                .year(metadata.get(MetadataField.YEAR.label))
                .length(metadata.get(MetadataField.LENGTH.label))
                .resourceId(metadata.get(MetadataField.RESOURCE_ID.label))
                .build();

        SongModel savedSong = songRepo.save(songModel);
        return new CreateSongResponseDto(savedSong.getId());
    }


    @Override
    public SongMetadataResponseDto getSongMetadata(Integer id) {

        SongModel songModel = songRepo.findById(id)
                .orElseThrow(() -> new SongMetadataNotFoundException(id));

        Map<String, String> metadataMap= new HashMap<>();

        Arrays.stream(MetadataField.values())
                .forEach(field -> {
                    String fieldValueByName = FieldValueFetcher.getFieldValueByName(field.label, songModel);
                    metadataMap.put(field.label, fieldValueByName);
                });

        return new SongMetadataResponseDto(metadataMap);
        }

    @Override
    public RemoveSongMetadataResponseDto removeSongMetadata(String id) {
        try {
            List<Integer> idList = Arrays.stream(id.split(","))
                    .map(Integer::parseInt)
                    .toList();
            songRepo.deleteAllById(idList);
            return new RemoveSongMetadataResponseDto(idList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateMetadata(Map<String, String> metadata) {
        Optional<MetadataField> missingField = Arrays.stream(MetadataField.values())
                .filter(field -> !metadata.containsKey(field.label))
                .findFirst();
        if (missingField.isPresent()) throw new MetadataFieldValidationException(missingField.get().label);
    }
}
