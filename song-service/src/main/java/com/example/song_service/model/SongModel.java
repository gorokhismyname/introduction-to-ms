package com.example.song_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotNull
    String artist;
    @NotNull
    String album;
    @NotNull
    @Pattern(regexp = "^(?:[0-5][0-9]):[0-5][0-9]$", message = "Duration must be in the format MM:SS")
    String duration;
    @NotNull
    int resourceId;
    @NotNull
    @Pattern(regexp = "\\b(19|20)\\d{2}\\b", message = "Year must be a valid four-digit year in the range 1900-2099")
    String year;
    @NotNull
    String title;
}
