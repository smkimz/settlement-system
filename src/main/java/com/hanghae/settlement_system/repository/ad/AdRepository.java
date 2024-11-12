package com.hanghae.settlement_system.repository.ad;

import com.hanghae.settlement_system.domain.ad.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
}
