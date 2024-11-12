package com.hanghae.settlement_system.dto.video;

import lombok.Data;

@Data
public class VideoPlayRequestDto {
    private Long userId;
    private Long videoId;
    private Long playbackPosition;
}
