package com.example.demo.repository;

import com.example.demo.model.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoryRepository extends MongoRepository<Story, String> {
    Page<Story> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    boolean existsByTitleAndAuthor(String title, String author);
}
