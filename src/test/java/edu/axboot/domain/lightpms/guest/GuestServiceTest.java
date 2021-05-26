package edu.axboot.domain.lightpms.guest;


import edu.axboot.AXBootApplication;
import edu.axboot.controllers.dto.GuestResponseDto;
import edu.axboot.domain.lightpms.room.RoomServiceTest;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AXBootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GuestServiceTest {
    private static Logger logger= LoggerFactory.getLogger(GuestServiceTest.class);

    @Autowired
    private GuestService guestService;

    Long id;

    @Test
    public void test1_투숙객_저장() {
        Guest guest = Guest.builder()
                            .id(null)
                            .guestNm("김다현")
                            .guestNmEng("KimDaHyun")
                            .guestTel("01055556666")
                            .email("d-hyun10@hanmail.net")
                            .birth("19941010")
                            .gender("F")
                            .langCd("KO")
                            .build();

        id  = guestService.saveUsingQueryDsl(guest);

        assertTrue(id > 0);
    }

    @Test
    public void test2_투숙객_목록() {
        String guestNm = "김다현";
        String guestTel = "01055556666";
        String email = "d-hyun10@hanmail.net";
        String filter = "";

        List<Guest> list = guestService.list(guestNm, guestTel, email, filter);

        assertTrue(list.size() > 0);
    }

    @Test
    public void test3_투숙객_상세조회() {
        GuestResponseDto guestResponseDto = guestService.selectOne(617L);

        assertTrue(guestResponseDto.getId() == 617L);
    }

    @Test
    public void test4_투숙객_수정() {
        Guest guest = Guest.builder()
                .id(617L)
                .guestTel("01047778888")
                .email("d-hyun10@naver.com")
                .build();

        id  = guestService.saveUsingQueryDsl(guest);

        assertTrue(id > 0);
    }

}
