package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "truyen")
public class Truyen {
    @Id
    private String id;
    private String truyen_name;
    private String volume_name;
    private String title;
    private String author;
    private List<String> genres;
    private String status;
    private String base_url;
    private String description;
}
