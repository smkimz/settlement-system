package com.hanghae.settlement_system.ad.service;

import com.hanghae.settlement_system.ad.domain.Ad;
import com.hanghae.settlement_system.ad.domain.AdViewLog;
import com.hanghae.settlement_system.ad.dto.AdRegistrationRequestDto;
import com.hanghae.settlement_system.ad.dto.AdResponseDto;
import com.hanghae.settlement_system.ad.repository.AdRepository;
import com.hanghae.settlement_system.ad.repository.AdViewLogRepository;
import com.hanghae.settlement_system.exception.AbusingException;
import com.hanghae.settlement_system.exception.ResourceNotFoundException;
import com.hanghae.settlement_system.user.domain.User;
import com.hanghae.settlement_system.user.repository.UserRepository;
import com.hanghae.settlement_system.video.domain.Video;
import com.hanghae.settlement_system.video.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final AdViewLogRepository adViewLogRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public AdService(AdRepository adRepository, AdViewLogRepository adViewLogRepository, VideoRepository videoRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.adViewLogRepository = adViewLogRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    public void viewAd(Long userId, Long adId) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new ResourceNotFoundException("Ad not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LocalDateTime limitTime = LocalDateTime.now().minusSeconds(30);
        if (adViewLogRepository.existsByUserIdAndAdIdAndPlayedAtAfter(userId, adId, limitTime)) {
            throw new AbusingException("Ad viewing frequency limit reached");
        }

        ad.setViewCount(ad.getViewCount() + 1);
        adRepository.save(ad);

        // Log ad view
        AdViewLog log = new AdViewLog();
        log.setUserId(userId);
        log.setAdId(adId);
        log.setPlayedAt(LocalDateTime.now());
        adViewLogRepository.save(log);
    }

    public void registerAd(AdRegistrationRequestDto request) {
        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));

        Ad ad = new Ad();
        ad.setVideoId(video.getId());
        adRepository.save(ad);
    }

    public AdResponseDto getAdById(Long id) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ad not found"));
        return new AdResponseDto(ad);
    }

    public List<AdResponseDto> getAllAds() {
        return adRepository.findAll()
                .stream()
                .map(AdResponseDto::new)
                .collect(Collectors.toList());
    }
}

