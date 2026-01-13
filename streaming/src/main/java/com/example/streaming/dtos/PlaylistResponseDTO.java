package com.example.streaming.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistResponseDTO {
    private Long playListId;
    private String name;

    public PlaylistResponseDTO(Long playListId, String name) {
        this.playListId=playListId;
        this.name=name;
    }
}
