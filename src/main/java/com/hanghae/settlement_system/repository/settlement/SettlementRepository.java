package com.hanghae.settlement_system.repository.settlement;

import com.hanghae.settlement_system.domain.settlement.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
