package com.hanghae.settlement_system.video.dto;

import lombok.Data;

@Data
public class VideoRegistrationRequestDto {
    private Long userId;
    private String title;
    private String description;
    private Long playtime;
}
