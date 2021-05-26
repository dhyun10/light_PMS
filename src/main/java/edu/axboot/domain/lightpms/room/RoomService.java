package edu.axboot.domain.lightpms.room;

import com.chequer.axboot.core.parameter.RequestParams;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import edu.axboot.domain.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService extends BaseService<Room, Long> {

    private RoomRepository roomRepository;

    @Inject
    public RoomService(RoomRepository roomRepository) {
        super(roomRepository);
        this.roomRepository = roomRepository;
    }

    public List<Room> roomList(String roomTypCd) {
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(roomTypCd)) {
            builder.and(qRoom.roomTypCd.eq(roomTypCd));
        }

        List<Room> list = select()
                .from(qRoom)
                .where(builder)
                .orderBy(qRoom.id.asc())
                .fetch();

        return list;
    }

    public List<Room> roomNumList(RequestParams<Room> request) {
        List<Room> roomList = select().select(
                Projections.fields(Room.class, qRoom.roomNum)
                ).from(qRoom).fetch();
        return roomList;
    }

    @Transactional
    public List<Long> save(List<Room> list) {
        List<Long> ids = new ArrayList<>();
        for (Room room : list) {
            if(room.is__deleted__()) {
                roomRepository.delete(room.getId());
                ids.add(room.getId());
            } else {
                ids.add(roomRepository.save(room).getId());
            }
        }

        return ids;
    }
}
