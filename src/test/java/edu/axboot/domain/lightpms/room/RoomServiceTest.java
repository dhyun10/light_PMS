package edu.axboot.domain.lightpms.room;

import edu.axboot.AXBootApplication;
import lombok.extern.java.Log;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AXBootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoomServiceTest {
    private static Logger logger= LoggerFactory.getLogger(RoomServiceTest.class);

    @Autowired
    private RoomService roomService;

    List<Long> ids = new ArrayList<>();

    @Test
    public void test1_객실정보_저장() {
        List<Room> list = new ArrayList<Room>();
        list.add(Room.builder()
                .id(null)
                .roomNum("401")
                .roomTypCd("SB")
                .dndYn("N")
                .ebYn("N")
                .roomSttusCd("IN")
                .clnSttusCd("VD")
                .svcSttusCd("OOO")
                .isCreated(true)
                .isModified(false)
                .isDeleted(false)
                .build());

        ids = roomService.save(list);

        assertTrue(ids.size() == list.size());
    }

    @Test
    public void test2_객실정보_목록() {
        String roomTypCd = "SB";

        List<Room> list = roomService.roomList(roomTypCd);

        assertTrue(list.size() > 0);
    }

    @Test
    public void test3_객실정보_수정() {
        List<Room> list = new ArrayList<Room>();
        list.add(Room.builder()
                .id(97L)
                .roomNum("401")
                .roomTypCd("SB")
                .dndYn("N")
                .ebYn("N")
                .roomSttusCd("EMT")
                .clnSttusCd("VD")
                .svcSttusCd("OOO")
                .isCreated(false)
                .isModified(true)
                .isDeleted(false)
                .build());

        ids = roomService.save(list);

        assertTrue(ids.size() == list.size());
    }

    @Test
    public void test4_객실정보_삭제() {
        List<Room> list = new ArrayList<Room>();
        list.add(Room.builder()
                .id(97L)
                .roomNum("401")
                .roomTypCd("SB")
                .dndYn("N")
                .ebYn("N")
                .roomSttusCd("EMT")
                .clnSttusCd("VD")
                .svcSttusCd("OOO")
                .isCreated(false)
                .isModified(false)
                .isDeleted(true)
                .build());

        ids = roomService.save(list);

        assertTrue(ids.size() == list.size());
    }
}
