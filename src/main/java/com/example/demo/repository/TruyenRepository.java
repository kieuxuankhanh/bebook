package com.example.demo.repository;

import com.example.demo.model.Story;
import com.example.demo.model.Truyen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TruyenRepository extends MongoRepository<Truyen, String> {
    Page<Story> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    boolean existsByTitleAndAuthor(String title, String author);
}
