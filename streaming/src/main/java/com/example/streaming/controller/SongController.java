package com.example.streaming.controller;

import com.example.streaming.dtos.SongDTO;
import com.example.streaming.service.MusicApiService;
import com.example.streaming.service.SongStreamService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final MusicApiService musicApiService;
    private final SongStreamService songStreamService;

    public SongController(MusicApiService musicApiService,
                          SongStreamService songStreamService) {
        this.musicApiService = musicApiService;
        this.songStreamService = songStreamService;
    }

    //  Public search
    @GetMapping("/search")
    public List<SongDTO> search(@RequestParam String q) {
        return musicApiService.searchSong(q);
    }

    //  Stream preview
    @GetMapping("/{spotifyId}/stream")
    public ResponseEntity<Resource> stream(@PathVariable String spotifyId) {
        return songStreamService.streamPreview(spotifyId);
    }

}
