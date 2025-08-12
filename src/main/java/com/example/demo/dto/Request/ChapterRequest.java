package com.example.demo.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterRequest {
    private String story_id;
    private String chapter_title;
    private String content;
}
