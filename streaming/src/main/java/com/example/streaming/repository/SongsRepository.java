package com.example.streaming.repository;

import com.example.streaming.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongsRepository extends JpaRepository<Song,Long> {
    Optional<Song> findBySpotifyId(String spotifyId);
}
