package com.hanghae.settlement_system.video.controller;

import com.hanghae.settlement_system.video.dto.VideoPlayRequestDto;
import com.hanghae.settlement_system.video.dto.VideoRegistrationRequestDto;
import com.hanghae.settlement_system.video.dto.VideoResponseDto;
import com.hanghae.settlement_system.video.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerVideo(@RequestBody VideoRegistrationRequestDto request) {
        videoService.registerVideo(request);
        return ResponseEntity.ok("Video registered successfully");
    }

    @PostMapping("/play")
    public ResponseEntity<?> playVideo(@RequestBody VideoPlayRequestDto request) {
        videoService.playVideo(request);
        return ResponseEntity.ok("Playback started");
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDto> getVideoById(@PathVariable Long id) {
        VideoResponseDto video = videoService.getVideoById(id);
        return ResponseEntity.ok(video);
    }

    @GetMapping
    public ResponseEntity<List<VideoResponseDto>> getAllVideos() {
        List<VideoResponseDto> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }
}

