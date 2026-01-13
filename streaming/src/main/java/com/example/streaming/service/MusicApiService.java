package com.example.streaming.service;

import com.example.streaming.dtos.SongDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MusicApiService {

    private final RestTemplate restTemplate;

    private static final String SEARCH_URL =
            "https://v1.nocodeapi.com/kakashisura/spotify/TpALnSLLURsOnPpf/search?q=%s&type=track&perPage=5&page=1";

    public MusicApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<SongDTO> searchSong(String query) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0");
        headers.set("Accept", "application/json");
        headers.set("Referer", "http://localhost");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                String.format(SEARCH_URL, query),
                HttpMethod.GET,
                entity,
                String.class
        );

        System.out.println("STATUS = " + response.getStatusCode());
        System.out.println("BODY = " + response.getBody());

        String json = response.getBody();

        JSONObject root = new JSONObject(json);
        JSONArray items = root.getJSONObject("tracks").getJSONArray("items");

        List<SongDTO> songs = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject track = items.getJSONObject(i);

            songs.add(new SongDTO(
                    track.getString("id"),
                    track.getString("name"),
                    track.getJSONArray("artists").getJSONObject(0).getString("name"),
                    track.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url"),
                    track.optString("preview_url", "")
            ));

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
        }

        return songs;
    }
}
