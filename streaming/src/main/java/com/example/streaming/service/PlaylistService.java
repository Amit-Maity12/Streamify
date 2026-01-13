package com.example.streaming.service;

import com.example.streaming.dtos.PlaylistResponseDTO;
import com.example.streaming.entity.Playlist;
import com.example.streaming.entity.Song;
import com.example.streaming.entity.User;
import com.example.streaming.repository.PlaylistsRepo;
import com.example.streaming.repository.SongsRepository;
import com.example.streaming.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistsRepo playlistsRepo;
    private final SongsRepository songsRepository;
    private final UsersRepository usersRepository;

    public PlaylistService(PlaylistsRepo playlistsRepo,
                           SongsRepository songsRepository,
                           UsersRepository usersRepository) {
        this.playlistsRepo = playlistsRepo;
        this.songsRepository = songsRepository;
        this.usersRepository = usersRepository;
    }

    // 🔐 Get user's playlists
    public List<PlaylistResponseDTO> getUserPlaylists(String email) {
        User user = getUserByEmail(email);

        return playlistsRepo.findByUser(user)
                .stream()
                .map(p -> new PlaylistResponseDTO(p.getId(), p.getName()))
                .toList();
    }

    // 🔐 Create playlist
    public Playlist createPlaylist(String name, String email) {
        User user = getUserByEmail(email);

        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setUser(user);

        return playlistsRepo.save(playlist);
    }

    // 🔐 Add song (ownership check)
    public void addSong(Long playlistId, Long songId, String email) {
        Playlist playlist = getUserPlaylist(playlistId, email);

        Song song = songsRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        playlist.getSongs().add(song);
        playlistsRepo.save(playlist);
    }

    // 🔐 Remove song
    public void removeSong(Long playlistId, Long songId, String email) {
        Playlist playlist = getUserPlaylist(playlistId, email);

        playlist.getSongs().removeIf(song -> song.getId().equals(songId));
        playlistsRepo.save(playlist);
    }

    // 🔐 Get songs
    public List<Song> getSongs(Long playlistId, String email) {
        return getUserPlaylist(playlistId, email).getSongs();
    }

    // =====================
    // 🔒 Helper methods
    // =====================

    private User getUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Playlist getUserPlaylist(Long playlistId, String email) {
        Playlist playlist = playlistsRepo.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        if (!playlist.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }
        return playlist;
    }





}
