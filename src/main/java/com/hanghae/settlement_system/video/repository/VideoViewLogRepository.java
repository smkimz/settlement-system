package com.hanghae.settlement_system.video.repository;

import com.hanghae.settlement_system.video.domain.VideoViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoViewLogRepository extends JpaRepository<VideoViewLog, Long> {
    List<VideoViewLog> findByUserIdAndVideoIdOrderByPlayedAtDesc(Long userId, Long videoId);
}
