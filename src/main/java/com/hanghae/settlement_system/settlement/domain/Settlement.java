package com.hanghae.settlement_system.settlement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDate settlementDate;
    private Double videoEarnings; // 동영상 정산 금액
    private Double adEarnings;     // 광고 정산 금액
    private Double totalEarnings;  // 총 정산 금액

    public Settlement(Long userId, LocalDate settlementDate, Double videoEarnings, Double adEarnings) {
        this.userId = userId;
        this.settlementDate = settlementDate;
        this.videoEarnings = videoEarnings;
        this.adEarnings = adEarnings;
        this.totalEarnings = videoEarnings + adEarnings;
    }
}