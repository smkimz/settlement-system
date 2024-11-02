package com.hanghae.settlement_system.ad.domain;

import com.hanghae.settlement_system.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdViewLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false) // ad_id 외래키와 연결
    private Ad ad;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // user_id 외래키와 연결
    private User user;

    private LocalDateTime playedAt;
}
