package com.hanghae.settlement_system.repository.ad;

import com.hanghae.settlement_system.domain.ad.AdViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdViewLogRepository extends JpaRepository<AdViewLog, Long> {

    List<AdViewLog> findByUserId(Long userId);

    boolean existsByUserIdAndAdIdAndPlayedAtAfter(Long userId, Long adId, LocalDateTime playedAfter);
}
