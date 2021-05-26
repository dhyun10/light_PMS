package edu.axboot.controllers.dto;

import edu.axboot.domain.lightpms.reservation.ReservationMemo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationUpdateRequestDto {
    private Long id;
    private String sttusCd;

    private String arrDt;
    private String arrTime;
    private String depDt;
    private String depTime;
    private int nightCnt;

    private String roomNum;
    private String roomTypCd;

    private int adultCnt;
    private int childCnt;

    private Long guestId;
    private String guestNm;
    private String guestNmEng;
    private String guestTel;
    private String email;
    private String langCd;
    private String birth;
    private String gender;

    private String saleTypCd;
    private String srcCd;
    private String payCd;
    private String advnYn;
    private String salePrc;
    private String svcPrc;

    private List<ReservationMemo> memoList;

    @Builder
    public ReservationUpdateRequestDto(
            Long id, String roomTypCd, String roomNum,
            String arrDt, String arrTime, String depDt, String depTime, int nightCnt, int adultCnt, int childCnt,
            Long guestId, String guestNm, String guestNmEng, String guestTel, String email, String langCd, String birth, String gender,
            String saleTypCd, String sttusCd, String srcCd, String payCd, String advnYn, String salePrc, String svcPrc,
            List<ReservationMemo> memoList) {
        this.id = id;
        this.sttusCd = sttusCd;

        this.arrDt = arrDt;
        this.arrTime = arrTime;
        this.depDt = depDt;
        this.depTime = depTime;
        this.nightCnt = nightCnt;

        this.roomTypCd = roomTypCd;
        this.roomNum = roomNum;

        this.adultCnt = adultCnt;
        this.childCnt = childCnt;

        this.guestId = guestId;
        this.guestNm = guestNm;
        this.guestNmEng = guestNmEng;
        this.guestTel = guestTel;
        this.email = email;
        this.langCd = langCd;
        this.birth = birth;
        this.gender = gender;

        this.saleTypCd = saleTypCd;
        this.payCd = payCd;
        this.advnYn = advnYn;
        this.salePrc = salePrc;
        this.svcPrc = svcPrc;
        this.srcCd = srcCd;

        this.memoList = memoList;
    }
}
