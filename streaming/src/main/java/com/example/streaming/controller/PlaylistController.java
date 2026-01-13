package com.example.streaming.controller;

import com.example.streaming.dtos.PlaylistDTO;
import com.example.streaming.dtos.PlaylistReq;
import com.example.streaming.dtos.PlaylistResponseDTO;
import com.example.streaming.dtos.PlaylistSongDTO;
import com.example.streaming.entity.Playlist;
import com.example.streaming.entity.Song;
import com.example.streaming.entity.User;
import com.example.streaming.service.PlaylistService;
import com.example.streaming.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;

    public PlaylistController(PlaylistService playlistService,UserService userService){
        this.playlistService = playlistService;
        this.userService = userService;
    }

    // 🔐 Get all playlists of logged-in user
    @GetMapping
    public ResponseEntity<List<PlaylistResponseDTO>> getMyPlaylists(Authentication authentication) {

        String email = authentication.getName(); // comes from JWT subject

        List<PlaylistResponseDTO> playlists = playlistService.getUserPlaylists(email);

        return ResponseEntity.ok(playlists);
    }

    // 🔐 Create playlist
    @PostMapping("/create")
    public ResponseEntity<PlaylistResponseDTO> create(
            @RequestBody PlaylistReq dto,
            Authentication authentication) {

        String email = authentication.getName();
        Playlist playlist = playlistService.createPlaylist(dto.getName(), email);

        return ResponseEntity.ok(
                new PlaylistResponseDTO(playlist.getId(), playlist.getName())
        );
    }

    // 🔐 Add song to own playlist
    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<?> addSong(
            @PathVariable Long playlistId,
            @PathVariable Long songId,
            Authentication authentication) {

        playlistService.addSong(playlistId, songId, authentication.getName());
        return ResponseEntity.ok("Song added");
    }

    // 🔐 Remove song
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<?> removeSong(
            @PathVariable Long playlistId,
            @PathVariable Long songId,
            Authentication authentication) {

        playlistService.removeSong(playlistId, songId, authentication.getName());
        return ResponseEntity.ok("Song removed");
    }

    // 🔐 View songs of own playlist
    @GetMapping("/{playlistId}/songs")
    public ResponseEntity<List<Song>> viewSongs(
            @PathVariable Long playlistId,
            Authentication authentication) {

        return ResponseEntity.ok(
                playlistService.getSongs(playlistId, authentication.getName())
        );
    }
    //view playlist
    @GetMapping("/{playlistId}")
    public PlaylistResponseDTO view(
            @PathVariable Long playlistId,
            Authentication authentication) {

        String email = authentication.getName();

        Playlist playlist = playlistService.getUserPlaylist(playlistId, email);
        return new PlaylistResponseDTO(playlist.getId(), playlist.getName());
    }



}
