package com.hanghae.settlement_system.repository.video;

import com.hanghae.settlement_system.domain.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByUserId(Long userId);
}