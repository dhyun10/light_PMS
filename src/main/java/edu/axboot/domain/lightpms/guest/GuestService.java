package edu.axboot.domain.lightpms.guest;

import com.chequer.axboot.core.parameter.RequestParams;
import com.querydsl.core.BooleanBuilder;
import edu.axboot.controllers.dto.GuestResponseDto;
import edu.axboot.controllers.dto.ReservationListResponseDto;
import edu.axboot.domain.BaseService;
import edu.axboot.domain.lightpms.reservation.Reservation;
import edu.axboot.domain.lightpms.reservation.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService extends BaseService<Guest, Long> {
    private GuestRepository guestRepository;

    @Inject
    private ReservationService reservationService;

    @Inject
    public GuestService(GuestRepository guestRepository) {
        super(guestRepository);
        this.guestRepository = guestRepository;
    }

    public List<Guest> list(RequestParams request) {
        String guestNm = request.getString("guestNm", "");
        String guestTel = request.getString("guestTel", "");
        String email = request.getString("email", "");
        String filter = request.getString("filter", "");

        BooleanBuilder builder = new BooleanBuilder();

        if(isNotEmpty(guestNm)) {
            builder.and(qGuest.guestNm.contains(guestNm));
        }
        if(isNotEmpty(guestTel)) {
            builder.and(qGuest.guestTel.contains(guestTel));
        }
        if(isNotEmpty(email)) {
            builder.and(qGuest.email.contains(email));
        }
        if(isNotEmpty(filter)) {
            builder.and(qGuest.guestTel.contains(filter)).or(qGuest.email.contains(filter));
        }

        List<Guest> list = select()
                .from(qGuest)
                .where(builder)
                .orderBy(qGuest.id.asc())
                .fetch();

        return list;
    }

    public GuestResponseDto selectOne(Long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qGuest.id.eq(id));

        Guest guest = select()
                .from(qGuest)
                .where(builder)
                .fetchOne();

        GuestResponseDto guestDto = new GuestResponseDto(guest);
        guestDto.setRsvList(reservationService.rsvList(id));

        return guestDto;
    }

    @Transactional
    public Long saveUsingQueryDsl(Guest guest) {
        if(guest.getId()==null || guest.getId()==0) {
            Long id = this.guestRepository.save(guest).getId();
            return id;
        } else {
            update(qGuest)
                    .set(qGuest.guestTel, guest.getGuestTel())
                    .set(qGuest.email, guest.getEmail())
                    .set(qGuest.rmk, guest.getRmk())
                    .where(qGuest.id.eq(guest.getId()))
                    .execute();

            return guest.getId();
        }
    }

}
