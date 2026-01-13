package com.example.streaming.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistSongDTO {
    private Long playlistId;
    private  Long songId;
}
