package com.example.demo.service;

import com.example.demo.dto.Request.ChapterRequest;
import com.example.demo.dto.Response.ChapterResponse;
import com.example.demo.model.Chapter;
import com.example.demo.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    public Page<ChapterResponse> getAllByStoryId(String storyId, Pageable pageable) {
        Page<Chapter> chapters = chapterRepository.findByStoryId(storyId, pageable);
        return chapters.map(this::mapToResponse);
    }

    public ChapterResponse createChapter(ChapterRequest chapterRequest) {
        boolean exits = chapterRepository.existsByChapterTitleAndStoryId(
                chapterRequest.getChapter_title(),
                chapterRequest.getStory_id()
        );
        if (exits) {
            throw new RuntimeException("Chuong cua truyen da ton tai");
        }

        Chapter chapter = new Chapter();
        chapter.setStoryId(chapterRequest.getStory_id());
        chapter.setChapterTitle(chapterRequest.getChapter_title());
        chapter.setChapter_id(UUID.randomUUID().toString());
        chapter.setContent(chapterRequest.getContent());
        chapterRepository.save(chapter);
        return mapToResponse(chapter);
    }

    public ChapterResponse updateChapter(String id, ChapterRequest chapterRequest) {
        Chapter chapter= chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("khong tim thay chuong"));
        chapter.setChapterTitle(chapterRequest.getChapter_title());
        chapter.setContent(chapterRequest.getContent());
        return mapToResponse(chapterRepository.save(chapter));
    }

    public void deleteChapter(String id) {
        chapterRepository.deleteById(id);
    }

    public ChapterResponse mapToResponse(Chapter chapter){
        return new ChapterResponse(
                chapter.getId(),
                chapter.getStoryId(),
                chapter.getChapterTitle(),
                chapter.getChapter_url(),
                chapter.getChapter_id(),
                chapter.getContent()
        );
    }
}
