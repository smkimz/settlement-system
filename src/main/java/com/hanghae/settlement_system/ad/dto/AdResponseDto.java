package com.hanghae.settlement_system.ad.dto;

import com.hanghae.settlement_system.ad.domain.Ad;
import lombok.Data;

@Data
public class AdResponseDto {
    private Long id;
    private Long videoId;
    private Long viewCount;

    public AdResponseDto(Ad ad) {
        this.id = ad.getId();
        this.videoId = ad.getVideo().getId();
        this.viewCount = ad.getViewCount();
    }
}

