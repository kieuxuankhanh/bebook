package com.example.demo.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChapterResponse {
    private String id;
    private String story_id;
    private String chapter_title;
    private String chapter_url;
    private String chapter_id;
    private String content;
}
