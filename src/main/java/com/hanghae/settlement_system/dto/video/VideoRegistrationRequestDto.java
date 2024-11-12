package com.hanghae.settlement_system.dto.video;

import lombok.Data;

@Data
public class VideoRegistrationRequestDto {
    private Long userId;
    private String title;
    private String description;
    private Long playtime;
}
