package edu.axboot.controllers.dto;

import edu.axboot.domain.lightpms.guest.Guest;
import edu.axboot.domain.lightpms.reservation.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationListResponseDto {
    private Long id;
    private String rsvDt;
    private String rsvNum;

    private Long guestId;
    private String guestNm;

    private String arrDt;
    private String arrTime;
    private String depDt;
    private String depTime;
    private int nightCnt;

    private String roomTypCd;
    private String roomNum;

    private String saleTypCd;
    private String sttusCd;
    private String srcCd;

    private String salePrc;

    public ReservationListResponseDto (Reservation entity) {
        this.id = entity.getId();
        this.rsvDt = entity.getRsvDt();
        this.rsvNum = entity.getRsvNum();

        this.roomTypCd = entity.getRoomTypCd();
        this.roomNum = entity.getRoomNum();
        this.arrDt = entity.getArrDt();
        this.arrTime = entity.getArrTime();
        this.depDt = entity.getDepDt();
        this.depTime = entity.getDepTime();
        this.nightCnt = entity.getNightCnt();

        this.guestId = entity.getGuestId();
        this.guestNm = entity.getGuestNm();

        this.saleTypCd = entity.getSaleTypCd();
        this.sttusCd = entity.getSttusCd();
        this.srcCd = entity.getSrcCd();
        this.salePrc = entity.getSalePrc();
    }
}
