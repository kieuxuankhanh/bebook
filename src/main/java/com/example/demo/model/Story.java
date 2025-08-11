package com.example.demo.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Story {
    private UUID id;
    private String title;
    private String author;
    private List<String> genres;
    private String description;
    private String img;
    private String source;
}
