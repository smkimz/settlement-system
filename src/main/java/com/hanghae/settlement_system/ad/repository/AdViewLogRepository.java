package com.hanghae.settlement_system.ad.repository;

import com.hanghae.settlement_system.ad.domain.AdViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AdViewLogRepository extends JpaRepository<AdViewLog, Long> {
    boolean existsByUserIdAndAdIdAndPlayedAtAfter(Long userId, Long adId, LocalDateTime playedAfter);
}
