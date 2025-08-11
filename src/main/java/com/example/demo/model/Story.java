package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "stories")
public class Story {
    @Id
    private UUID id;
    private String title;
    private String author;
    private List<String> genres;
    private String description;
    private String img;
    private String source;
}
