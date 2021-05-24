package edu.axboot.domain.lightpms.reservation;

import com.chequer.axboot.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationMemoRepository extends AXBootJPAQueryDSLRepository<ReservationMemo, Long> {
}
