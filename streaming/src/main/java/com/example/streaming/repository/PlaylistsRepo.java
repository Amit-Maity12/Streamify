package com.example.streaming.repository;

import com.example.streaming.entity.Playlist;
import com.example.streaming.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistsRepo  extends JpaRepository<Playlist,Long> {
    List<Playlist> findByUserId(Long userId);
    List<Playlist> findByUser(User user);
}
