package edu.axboot.domain.lightpms.guest;

import com.chequer.axboot.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends AXBootJPAQueryDSLRepository<Guest, Long> {
}
