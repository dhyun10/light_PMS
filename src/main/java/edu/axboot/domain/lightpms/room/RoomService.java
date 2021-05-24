package edu.axboot.domain.lightpms.room;

import com.chequer.axboot.core.parameter.RequestParams;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import edu.axboot.domain.BaseService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class RoomService extends BaseService<Room, Long> {

    private RoomRepository roomRepository;

    @Inject
    public RoomService(RoomRepository roomRepository) {
        super(roomRepository);
        this.roomRepository = roomRepository;
    }

    public List<Room> roomList(RequestParams<Room> request) {
        String roomTypCd = request.getString("roomTypCd", "");

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
}
