package com.hanghae.settlement_system.video.dto;

import lombok.Data;

@Data
public class VideoPlayRequestDto {
    private Long userId;
    private Long videoId;
    private Long playbackPosition;
}
