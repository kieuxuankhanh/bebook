package com.example.demo.repository;

import com.example.demo.model.Chapter;
import com.example.demo.model.Chuong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChuongRepository extends MongoRepository<Chuong, String> {
    Page<Chapter> findByStoryId(String storyId, Pageable pageable);
    boolean existsByChapterTitleAndStoryId(String chapterTitle, String storyId);
}
