package com.hanghae.settlement_system.ad.repository;

import com.hanghae.settlement_system.ad.domain.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
}
