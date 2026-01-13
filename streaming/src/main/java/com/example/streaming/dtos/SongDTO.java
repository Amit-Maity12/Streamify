package com.example.streaming.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDTO {

    private String spotifyId;
    private String title;
    private String artist;
    private String imgUrl;
    private String streamUrl;

    public SongDTO(String spotifyId, String title, String artist,
                   String imgUrl, String streamUrl) {
        this.spotifyId = spotifyId;
        this.title = title;
        this.artist = artist;
        this.imgUrl = imgUrl;
        this.streamUrl = streamUrl;
    }

    public void setId(String id) {

    }

    public void setPreviewUrl(String previewUrl) {
    }



}

