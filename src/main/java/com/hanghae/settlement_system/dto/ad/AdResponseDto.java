package com.hanghae.settlement_system.dto.ad;

import com.hanghae.settlement_system.domain.ad.Ad;
import lombok.Data;

@Data
public class AdResponseDto {
    private Long id;
    private Long videoId;
    private Long viewCount;

    public AdResponseDto(Ad ad) {
        this.id = ad.getId();
        this.videoId = ad.getVideoId();
        this.viewCount = ad.getViewCount();
    }
}

