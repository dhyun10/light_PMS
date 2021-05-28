package edu.axboot.controllers.dto;

import edu.axboot.domain.lightpms.reservation.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class SalesResponseDto {
    private String rsvDt;
    private Long rsvCnt = 0L;
    private BigDecimal salePrc = BigDecimal.ZERO;
    private BigDecimal svcPrc = BigDecimal.ZERO;
    private BigDecimal sumPrc = BigDecimal.ZERO;

    private BigDecimal getSumPrc() {
        if (salePrc == null) this.salePrc = BigDecimal.ZERO;
        if (svcPrc == null) this.svcPrc = BigDecimal.ZERO;
        return this.salePrc.add(this.svcPrc);
    }

    public void setRsvDt(String rsvDt) {
        this.rsvDt = rsvDt;
    }

    public SalesResponseDto(Reservation entity) {
        this.rsvDt = entity.getRsvDt();
        this.rsvCnt = entity.getId();
        this.salePrc = entity.getSalePrc();
        this.svcPrc = entity.getSvcPrc();
        sumPrc = getSumPrc();
    }

    public void add(SalesResponseDto dto) {
        if (dto == null) return;
        this.rsvCnt += dto.getRsvCnt();
        if (dto.getSalePrc() != null) this.salePrc.add(dto.getSalePrc());
        if (dto.getSvcPrc() != null) this.svcPrc.add(dto.getSvcPrc());
    }

}
