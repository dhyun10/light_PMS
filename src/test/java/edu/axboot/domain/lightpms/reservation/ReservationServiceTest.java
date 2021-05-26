package edu.axboot.domain.lightpms.reservation;

import com.chequer.axboot.core.parameter.RequestParams;
import edu.axboot.AXBootApplication;
import edu.axboot.controllers.dto.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AXBootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservationServiceTest {
    private static Logger logger= LoggerFactory.getLogger(ReservationServiceTest.class);

    @Autowired
    private ReservationService reservationService;

    @Test
    public void test1_예약_등록() {
        List<ReservationMemo> memoList = new ArrayList<>();
        memoList.add(ReservationMemo.builder()
                .memoCn("나나나")
                .memoDtti("2021-05-26")
                .build());

        ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .arrDt("2021-05-26")
                .nightCnt(1)
                .depDt("2021-05-27")
                .roomTypCd("DB")
                .adultCnt(2)
                .childCnt(0)
                .guestNm("아아아")
                .guestNmEng("aaa")
                .guestTel("01088887777")
                .langCd("KO")
                .gender("F")
                .saleTypCd("01")
                .srcCd("01")
                .payCd("01")
                .salePrc("100000")
                .memoList(memoList)
                .build();

        Long id = reservationService.saveUsingQueryDsl(requestDto);

        assertTrue(id > 0);
    }

    @Transactional
    @Test
    public void test2_예약_상세조회()  {
        ReservationResponseDto dto = reservationService.selectOne(1752L);

        assertTrue(dto.getId() == 1752L);
    }

    @Test
    public void test3_예약_목록() {
        RequestParams params = new RequestParams();
        params.put("roomTypCd", "SB");

        List<ReservationListResponseDto> list = reservationService.list(params);

        assertTrue(list.size() > 0);
    }

    @Test
    public void test4_예약_상태변경() {
        List<Long> ids = new ArrayList<>();
        ids.add(1694L);
        ids.add(1752L);

        String sttusCd = "CHK_01";

        int result = reservationService.updateSttusCd(ids, sttusCd);

        assertTrue(result > 0);
    }

    @Test
    public void test5_예약_변경() {
        List<ReservationMemo> memoList = new ArrayList<>();
        memoList.add(ReservationMemo.builder()
                .memoCn("ㅎㅇ")
                .memoDtti("2021-05-26")
                .build());

        ReservationUpdateRequestDto dto = ReservationUpdateRequestDto.builder()
                .arrDt("2021-05-26")
                .nightCnt(1)
                .depDt("2021-05-27")
                .roomTypCd("DB")
                .adultCnt(2)
                .childCnt(0)
                .guestNm("아아아")
                .guestNmEng("aaa")
                .guestTel("01088887777")
                .langCd("KO")
                .gender("F")
                .saleTypCd("01")
                .srcCd("01")
                .payCd("CASH")
                .salePrc("100000")
                .advnYn("N")
                .sttusCd("CHK_02")
                .memoList(memoList)
                .build();

        Long id = reservationService.update(1752L, dto);

        assertTrue(id == 1752L);
    }

    @Test
    public void test6_매출현황() {
        RequestParams params = new RequestParams();
        params.put("rsvDtStart", "2021-05-10");
        params.put("rsvDtEnd", "2021-05-26");

        List<ReservationSalesResponseDto> list = reservationService.salesList(params);

        assertTrue(list.size() > 0);
    }


}
