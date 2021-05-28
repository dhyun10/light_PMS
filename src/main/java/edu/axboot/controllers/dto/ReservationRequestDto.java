package edu.axboot.controllers.dto;

import edu.axboot.domain.lightpms.guest.Guest;
import edu.axboot.domain.lightpms.reservation.Reservation;
import edu.axboot.domain.lightpms.reservation.ReservationMemo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestDto {
    private String rsvDt;
    private int sno;
    private String rsvNum;
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
    private BigDecimal salePrc;
    private BigDecimal svcPrc;

    private List<ReservationMemo> memoList;


    @Builder
    public ReservationRequestDto(
            String rsvDt, int sno, String rsvNum, String roomTypCd, String roomNum,
            String arrDt, String arrTime, String depDt, String depTime, int nightCnt, int adultCnt, int childCnt,
            Long guestId, String guestNm, String guestNmEng, String guestTel, String email, String langCd, String birth, String gender,
            String saleTypCd, String sttusCd, String srcCd, String payCd, String advnYn, BigDecimal salePrc, BigDecimal svcPrc,
            List<ReservationMemo> memoList) {
        this.rsvDt = rsvDt;
        this.sno = sno;
        this.rsvNum = rsvNum;
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

    public Reservation toEntity() {
        return Reservation.builder()
                .rsvDt(rsvDt).rsvNum(rsvNum).sno(sno).sttusCd(sttusCd)
                .arrDt(arrDt).arrTime(arrTime).depDt(depDt).depTime(depTime).nightCnt(nightCnt)
                .roomTypCd(roomTypCd).roomNum(roomNum)
                .adultCnt(adultCnt).childCnt(childCnt)
                .guestId(guestId).guestNm(guestNm).guestNmEng(guestNmEng).guestTel(guestTel)
                .email(email).langCd(langCd).birth(birth).gender(gender)
                .saleTypCd(saleTypCd).payCd(payCd).advnYn(advnYn).salePrc(salePrc).svcPrc(svcPrc).srcCd(srcCd)
                .build();
    }

    public Guest saveGuest() {
        return Guest.builder()
                .id(guestId).guestNm(guestNm).guestNmEng(guestNmEng).guestTel(guestTel)
                .email(email).langCd(langCd).birth(birth).gender(gender).build();
    }
}
