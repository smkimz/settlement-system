package com.hanghae.settlement_system.video.repository;

import com.hanghae.settlement_system.video.domain.VideoViewLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoViewLogRepository extends JpaRepository<VideoViewLog, Long> {

    Page<VideoViewLog> findByUserId(Long userId, Pageable pageable);

    Long countByUserId(Long userId); // 특정 유저의 VideoViewLog 갯수 조회

    List<VideoViewLog> findByUserIdAndVideoIdOrderByPlayedAtDesc(Long userId, Long videoId);
}
