package com.example.demo.controller;

import com.example.demo.dto.Request.ChapterRequest;
import com.example.demo.dto.Response.ChapterResponse;
import com.example.demo.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @GetMapping("/page")
    public Page<ChapterResponse> page(
            @RequestParam(defaultValue = "") String storyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return chapterService.getAllByStoryId(storyId, PageRequest.of(page, size));
    }

    @PostMapping("/create")
    public ChapterResponse create(@RequestBody ChapterRequest chapterRequest){
        return chapterService.createChapter(chapterRequest);
    }

    @PutMapping("/update/{id}")
    public ChapterResponse update(@PathVariable String id, @RequestBody ChapterRequest chapterRequest){
        return chapterService.updateChapter(id, chapterRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id){
        chapterService.deleteChapter(id);
    }
}
