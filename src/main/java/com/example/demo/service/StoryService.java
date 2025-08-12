package com.example.demo.service;

import com.example.demo.dto.Request.StoryRequest;
import com.example.demo.dto.Response.StoryResponse;
import com.example.demo.model.Story;
import com.example.demo.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    public Page<StoryResponse> getAllStory(Pageable pageable) {
        return storyRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public StoryResponse getStoryById(String id){
        Story story = storyRepository.findById(id).orElseThrow(() -> new RuntimeException("khong co truyen"));
        return mapToResponse(story);
    }

    public Page<StoryResponse> getStoriesByName(String name, Pageable pageable) {
        return storyRepository.findByTitleContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
    }

    public StoryResponse createStory(StoryRequest storyRequest){
        boolean exists = storyRepository.existsByTitleAndAuthor(
                storyRequest.getTitle(),
                storyRequest.getAuthor()
        );
        if (exists) {
            throw new RuntimeException("Truyện với tiêu đề và tác giả này đã tồn tại");
        }

        Story story = new Story();
        story.setId(UUID.randomUUID().toString());
        story.setTitle(storyRequest.getTitle());
        story.setDescription(storyRequest.getDescription());
        story.setAuthor(storyRequest.getAuthor());
        story.setGenres(storyRequest.getGenres());
        storyRepository.save(story);
        return mapToResponse(story);
    }

    public StoryResponse updateStory(String id, StoryRequest request){
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy truyện"));

        story.setTitle(request.getTitle());
        story.setAuthor(request.getAuthor());
        story.setDescription(request.getDescription());
        story.setGenres(request.getGenres());
        story.setImg(request.getImg());
        story.setSource(request.getSource());

        return mapToResponse(storyRepository.save(story));
    }

    public void deleteStory(String id){
        storyRepository.deleteById(id);
    }
    private StoryResponse mapToResponse(Story story){
        return new  StoryResponse(
                story.getId(),
                story.getTitle(),
                story.getAuthor(),
                story.getDescription(),
                story.getGenres(),
                story.getImg(),
                story.getSource()
        );
    }
}
