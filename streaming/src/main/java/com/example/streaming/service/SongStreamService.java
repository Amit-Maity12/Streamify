package com.example.streaming.service;

import com.example.streaming.entity.Song;
import com.example.streaming.repository.SongsRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class SongStreamService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String TRACK_URL =
            "https://v1.nocodeapi.com/kakashisura/spotify/KYdUOZdrugLfDFff/tracks?ids=%s";

    public ResponseEntity<Resource> streamPreview(String songId) {

        String json = restTemplate.getForObject(
                String.format(TRACK_URL, songId),
                String.class
        );

        JSONObject root = new JSONObject(json);
        JSONArray tracks = root.getJSONArray("tracks");

        if (tracks.isEmpty()) {
            throw new RuntimeException("Song not found");
        }

        String previewUrl = tracks
                .getJSONObject(0)
                .optString("preview_url");

        if (previewUrl == null || previewUrl.isEmpty()) {
            throw new RuntimeException("Preview not available");
        }

        // Redirect browser to preview stream
        return ResponseEntity
                .status(302)
                .header("Location", previewUrl)
                .build();
    }
}


