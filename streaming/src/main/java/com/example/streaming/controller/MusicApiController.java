package com.example.streaming.controller;

import com.example.streaming.dtos.SongDTO;
import com.example.streaming.service.MusicApiService;
import com.example.streaming.service.SongCarousel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:5173")
public class MusicApiController {

    private final MusicApiService musicApiService;
    private final SongCarousel songCarousel;
    public MusicApiController(MusicApiService musicApiService,SongCarousel songCarousel){
        this.musicApiService = musicApiService;
        this.songCarousel = songCarousel;
    }

    @GetMapping("/carousel")
    public List<SongDTO> carousel(@RequestParam String q) {
        return musicApiService.searchSong(q);
    }

    @PostMapping("/song-carousel")
    public List<SongDTO> songCarousel(@RequestBody List<String> songs){
        return songCarousel.searchSongs(songs);
    }

}
