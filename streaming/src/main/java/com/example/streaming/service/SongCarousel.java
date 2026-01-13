package com.example.streaming.service;

import com.example.streaming.dtos.SongDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SongCarousel {
    private final RestTemplate restTemplate;
    private static final String SEARCH_URL =
            "https://v1.nocodeapi.com/kakashisura/spotify/TpALnSLLURsOnPpf/search?q=%s&type=track&perPage=10&page=1";
    public SongCarousel(RestTemplate restTemplate, RestTemplate restTemplate1){
        this.restTemplate = restTemplate1;
    }

    public List<SongDTO> searchSongs(List<String> songNames) {

        List<SongDTO> result = new ArrayList<>();

        for (String songName : songNames) {

            if (result.size() == 5) break;

            String json = restTemplate.getForObject(
                    String.format(SEARCH_URL, songName),
                    String.class
            );

            JSONObject root = new JSONObject(json);
            JSONArray items = root
                    .getJSONObject("tracks")
                    .getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {

                JSONObject track = items.getJSONObject(i);

                // ✅ must have preview
                String previewUrl = track.optString("preview_url", null);
                if (previewUrl == null || previewUrl.isEmpty()) continue;

                // ✅ image
                JSONArray images = track.getJSONObject("album").getJSONArray("images");
                if (images.isEmpty()) continue;

                String imageUrl = images.getJSONObject(0).getString("url");
                String title = track.getString("name");
                String artist = track.getJSONArray("artists")
                        .getJSONObject(0)
                        .getString("name");

                result.add(new SongDTO(
                        track.getString("id"),
                        title,
                        artist,
                        imageUrl,
                        previewUrl
                ));

                break; // ✅ one result per song name
            }
        }

        return result;
    }

}
