package edu.axboot.controllers.dto;

import com.querydsl.core.Tuple;
import edu.axboot.domain.lightpms.reservation.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationSalesResponseDto {
    private String rsvDt;
    private Long rsvCnt;
    private int salePrc;
    private int svcPrc;

    public ReservationSalesResponseDto(Reservation entity) {
        this.rsvDt = entity.getRsvDt();
        this.rsvCnt = entity.getId();
        this.salePrc = Integer.parseInt(entity.getSalePrc());
        this.svcPrc = Integer.parseInt(entity.getSvcPrc());
    }

}