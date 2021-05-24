package edu.axboot.domain.lightpms.room;

import com.chequer.axboot.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends AXBootJPAQueryDSLRepository<Room, Long> {
}
