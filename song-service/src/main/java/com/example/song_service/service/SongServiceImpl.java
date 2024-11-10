package com.example.song_service.service;

import com.example.song_service.exception.InvalidCSVFormatException;
import com.example.song_service.exception.SongMetadataNotFoundException;
import com.example.song_service.model.*;
import com.example.song_service.repo.SongRepo;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepo songRepo;
    private final SongMapper songMapper;

    @Autowired
    public SongServiceImpl(SongRepo songRepo, SongMapper songMapper) {
        this.songRepo = songRepo;
        this.songMapper = songMapper;
    }

    @Override
    public CreateSongResponseDto saveSongMetadata(Map<String, String> metadataMap) {
        Metadata metadata = mapToMetadata(metadataMap);
        SongModel model = songMapper.toModel(metadata);
        SongModel savedSong = songRepo.save(model);
        return new CreateSongResponseDto(savedSong.getId());
    }

    @Override
    public SongMetadataResponseDto getSongMetadata(Integer id) {
        SongModel songModel = songRepo.findById(id)
                .orElseThrow(() -> new SongMetadataNotFoundException(id));
        return songMapper.toDto(songModel);
    }

    @Override
    public RemoveSongMetadataResponseDto removeSongMetadata(String id) {
        try {
            List<Integer> idList = Arrays.stream(id.split(","))
                    .map(Integer::parseInt)
                    .filter(e -> songRepo.findById(e).isPresent())
                    .toList();
            songRepo.deleteAllById(idList);
            return new RemoveSongMetadataResponseDto(idList);

        } catch (Exception e) {
            throw new InvalidCSVFormatException(e.getMessage());
        }
    }

    private Metadata mapToMetadata(Map<String, String> metadataMap) {
        Metadata metadata = new Metadata();
        for (Map.Entry<String, String> entry : metadataMap.entrySet()) {
            metadata.set(entry.getKey(), entry.getValue());
        }
        return metadata;
    }
}
