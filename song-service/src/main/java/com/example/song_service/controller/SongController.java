package com.example.song_service.controller;

import com.example.song_service.model.CreateSongRequestDto;
import com.example.song_service.model.CreateSongResponseDto;
import com.example.song_service.model.RemoveSongMetadataResponseDto;
import com.example.song_service.model.SongMetadataResponseDto;
import com.example.song_service.service.SongService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/songs")
@Validated
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }


    @PostMapping
    public ResponseEntity<CreateSongResponseDto> saveSongMetadata(
            @RequestBody CreateSongRequestDto requestDto
            ) {
        CreateSongResponseDto responseDto = songService.saveSongMetadata(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SongMetadataResponseDto> getSongMetadata(
            @PathVariable Integer id
    ) {
        SongMetadataResponseDto responseDto = songService.getSongMetadata(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<RemoveSongMetadataResponseDto> removeSongMetadata(
            @RequestParam("id") @Length(max = 199) String id
    ) {
        RemoveSongMetadataResponseDto responseDto = songService.removeSongMetadata(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}
