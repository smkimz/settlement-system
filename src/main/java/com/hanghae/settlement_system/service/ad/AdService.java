package com.hanghae.settlement_system.service.ad;

import com.hanghae.settlement_system.domain.ad.Ad;
import com.hanghae.settlement_system.domain.ad.AdViewLog;
import com.hanghae.settlement_system.dto.ad.AdRegistrationRequestDto;
import com.hanghae.settlement_system.dto.ad.AdResponseDto;
import com.hanghae.settlement_system.repository.ad.AdRepository;
import com.hanghae.settlement_system.repository.ad.AdViewLogRepository;
import com.hanghae.settlement_system.exception.ResourceNotFoundException;
import com.hanghae.settlement_system.repository.user.UserRepository;
import com.hanghae.settlement_system.domain.video.Video;
import com.hanghae.settlement_system.repository.video.VideoRepository;
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

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 광고 조회수 추가
        ad.setViewCount(ad.getViewCount() + 1);
        ad.setUpdatedAt(LocalDateTime.now());
        adRepository.save(ad);

        // 광고 로그 추가
        AdViewLog log = new AdViewLog();
        log.setUserId(userId);
        log.setAdId(adId);
        adViewLogRepository.save(log);
    }

    public void registerAd(AdRegistrationRequestDto request) {
        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));

        Ad ad = new Ad();
        ad.setVideoId(video.getId());
        ad.setViewCount(0L);
        ad.setCreatedAt(LocalDateTime.now());
        ad.setUpdatedAt(LocalDateTime.now());
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

