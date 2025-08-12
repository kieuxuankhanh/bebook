package com.example.demo.controller;

import com.example.demo.dto.Request.StoryRequest;
import com.example.demo.dto.Response.StoryResponse;
import com.example.demo.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/story")
public class StoryController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/search")
    public Page<StoryResponse> searchStories(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return storyService.getStoriesByName(name, PageRequest.of(page, size));
    }

    @GetMapping("/all")
    public Page<StoryResponse> getAllStories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return storyService.getAllStory(PageRequest.of(page, size));
    }
    @PostMapping("/create")
    public StoryResponse createStory(@RequestBody StoryRequest rq){
        return storyService.createStory(rq);
    }

    @PutMapping("/update/{id}")
    public StoryResponse updateStory(@PathVariable String id, @RequestBody StoryRequest rq){
        return storyService.updateStory(id, rq);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStory(@PathVariable String id){
        storyService.deleteStory(id);
    }
}
