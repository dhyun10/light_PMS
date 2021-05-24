package edu.axboot.controllers.dto;

import edu.axboot.domain.lightpms.reservation.Reservation;
import edu.axboot.domain.lightpms.reservation.ReservationMemo;
import edu.axboot.domain.lightpms.room.Room;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReservationResponseDto {
    private Long id;
    private String rsvDt;
    private int sno;
    private String rsvNum;

    private Long guestId;
    private String guestNm;
    private String guestNmEng;
    private String guestTel;
    private String email;
    private String langCd;

    private String arrDt;
    private String arrTime;
    private String depDt;
    private String depTime;
    private int nightCnt;

    private String roomTypCd;
    private String roomNum;
    private int adultCnt;
    private int childCnt;

    private String saleTypCd;
    private String sttusCd;
    private String srcCd;

    private String birth;
    private String gender;

    private String payCd;
    private String advnYn;
    private String salePrc;
    private String svcPrc;

    private List<ReservationMemo> memoList = new ArrayList<ReservationMemo>();

    public ReservationResponseDto (Reservation entity) {
        this.id = entity.getId();
        this.rsvDt = entity.getRsvDt();
        this.sno = entity.getSno();
        this.rsvNum = entity.getRsvNum();

        this.roomTypCd = entity.getRoomTypCd();
        this.roomNum = entity.getRoomNum();
        this.arrDt = entity.getArrDt();
        this.arrTime = entity.getArrTime();
        this.depDt = entity.getDepDt();
        this.depTime = entity.getDepTime();
        this.nightCnt = entity.getNightCnt();

        this.adultCnt = entity.getAdultCnt();
        this.childCnt = entity.getChildCnt();

        this.guestId = entity.getGuestId();
        this.guestNm = entity.getGuestNm();
        this.guestNmEng = entity.getGuestNmEng();
        this.guestTel = entity.getGuestTel();
        this.email = entity.getEmail();
        this.langCd = entity.getLangCd();
        this.birth = entity.getBirth();
        this.gender = entity.getGender();

        this.saleTypCd = entity.getSaleTypCd();
        this.sttusCd = entity.getSttusCd();
        this.srcCd = entity.getSrcCd();
        this.payCd = entity.getPayCd();
        this.advnYn = entity.getAdvnYn();
        this.salePrc = entity.getSalePrc();
        this.svcPrc = entity.getSvcPrc();

        this.memoList.addAll(entity.getMemoList());
    }

}
