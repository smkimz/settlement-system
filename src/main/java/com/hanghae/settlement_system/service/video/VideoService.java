package com.hanghae.settlement_system.service.video;

import com.hanghae.settlement_system.exception.ResourceNotFoundException;
import com.hanghae.settlement_system.domain.user.User;
import com.hanghae.settlement_system.repository.user.UserRepository;
import com.hanghae.settlement_system.domain.video.Video;
import com.hanghae.settlement_system.domain.video.VideoViewLog;
import com.hanghae.settlement_system.dto.video.VideoPlayRequestDto;
import com.hanghae.settlement_system.dto.video.VideoRegistrationRequestDto;
import com.hanghae.settlement_system.dto.video.VideoResponseDto;
import com.hanghae.settlement_system.repository.video.VideoRepository;
import com.hanghae.settlement_system.repository.video.VideoViewLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final VideoViewLogRepository videoViewLogRepository;

    public VideoService(UserRepository userRepository, VideoRepository videoRepository, VideoViewLogRepository videoViewLogRepository) {
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
        this.videoViewLogRepository = videoViewLogRepository;
    }

    public void registerVideo(VideoRegistrationRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Video video = new Video();
        video.setUserId(user.getId());
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setPlaytime(request.getPlaytime());
        videoRepository.save(video);
    }

    public void playVideo(VideoPlayRequestDto request) {
        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));

        LocalDateTime limitTime = LocalDateTime.now().minusSeconds(30);
        if (!videoViewLogRepository.findByUserIdAndVideoIdOrderByPlayedAtDesc(request.getUserId(), request.getVideoId())
                .isEmpty()) {
            return;
        }

        if (video.getUserId().equals(request.getUserId())) {
            video.setViewCount(video.getViewCount() + 1);
        }

        videoRepository.save(video);

        VideoViewLog log = new VideoViewLog();
        log.setUserId(video.getUserId());
        log.setVideoId(video.getId());
        log.setPlayedAt(LocalDateTime.now());
        log.setStartedFrom(request.getPlaybackPosition());
        videoViewLogRepository.save(log);
    }

    public VideoResponseDto getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));
        return new VideoResponseDto(video);
    }

    public List<VideoResponseDto> getAllVideos() {
        return videoRepository.findAll()
                .stream()
                .map(VideoResponseDto::new)
                .collect(Collectors.toList());
    }
}

