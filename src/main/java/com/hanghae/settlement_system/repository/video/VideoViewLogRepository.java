package com.hanghae.settlement_system.repository.video;

import com.hanghae.settlement_system.domain.video.VideoViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoViewLogRepository extends JpaRepository<VideoViewLog, Long> {

    List<VideoViewLog> findByUserIdAndVideoIdOrderByPlayedAtDesc(Long userId, Long videoId);

    long countByVideoIdAndPlayedAtBetween(Long videoId, LocalDateTime startDate, LocalDateTime endDate);
}
