package com.hanghae.settlement_system.dto.video;

import com.hanghae.settlement_system.domain.video.Video;
import lombok.Data;

@Data
public class VideoResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long viewCount;
    private Long playtime;
    private Long userId;

    public VideoResponseDto(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.viewCount = video.getViewCount();
        this.playtime = video.getPlaytime();
        this.userId = video.getUserId();
    }
}

