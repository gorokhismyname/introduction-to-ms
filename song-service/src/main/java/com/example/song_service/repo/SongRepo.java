package com.example.song_service.repo;

import com.example.song_service.model.SongModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepo extends JpaRepository<SongModel, Integer> {
}
