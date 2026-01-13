package com.example.streaming.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(unique = true, nullable = false)
    private String spotifyId;
    private String title;
    private String artist;
    private int duration;
    private String imgUrl;
    private String streamUrl;

}
