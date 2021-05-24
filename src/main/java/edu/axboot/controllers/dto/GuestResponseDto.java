package edu.axboot.controllers.dto;

import edu.axboot.domain.lightpms.guest.Guest;
import edu.axboot.domain.lightpms.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GuestResponseDto {
    private Long id;
    private String guestNm;
    private String guestNmEng;
    private String guestTel;
    private String email;
    private String birth;
    private String gender;
    private String langCd;
    private String rmk;

    private List<ReservationListResponseDto> rsvList = new ArrayList<ReservationListResponseDto>();

    public GuestResponseDto (Guest entity) {
        this.id = entity.getId();
        this.guestNm = entity.getGuestNm();
        this.guestNmEng = entity.getGuestNmEng();
        this.guestTel = entity.getGuestTel();
        this.email = entity.getEmail();
        this.birth = entity.getBirth();
        this.gender = entity.getGender();
        this.langCd = entity.getLangCd();
        this.rmk = entity.getRmk();
    }
}
