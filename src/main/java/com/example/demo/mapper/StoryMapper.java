package com.example.demo.mapper;

import com.example.demo.model.Story;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.UUID;

public interface StoryMapper {
    List<Story> findAllStory();
    Story findStoryById(@Param("id") UUID id);
    Story findStoryByTitle(@Param("title") String title);
    Story findStoryByAuthor(@Param("author") String author);
    Story findStoryByGenres(@Param("genres") List<String> genres);
    int insert(@Param("story") Story story);
    int update(@Param("story") Story story);
    int delete(@Param("id") UUID id);
}
