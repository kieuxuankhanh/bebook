package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "chuong")
public class Chuong {
    @Id
    private String id;
    @Field("story_id")
    private String storyId;
    @Field("chapter_id")
    private String chapterId;
    private String chapterUrl;
    private int chapter_number;
    private String content;
}
