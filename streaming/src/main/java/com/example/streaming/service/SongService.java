package com.example.streaming.service;

import com.example.streaming.dtos.SongDTO;
import com.example.streaming.entity.Song;
import com.example.streaming.repository.SongsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    private final SongsRepository songsRepository;
    public SongService(SongsRepository songsRepository){
        this.songsRepository = songsRepository;
    }

    public Song saveIfNotExists(SongDTO dto) {

        return songsRepository.findBySpotifyId(dto.getSpotifyId())
                .orElseGet(() -> {
                    Song song = new Song();
                    song.setSpotifyId(dto.getSpotifyId());
                    song.setTitle(dto.getTitle());
                    song.setArtist(dto.getArtist());
//                    song.setDuration(dto.getDuration());
                    song.setImgUrl(dto.getImgUrl());
                    song.setStreamUrl(dto.getStreamUrl());
                    return songsRepository.save(song);
                });
    }
}
