package edu.axboot.domain.lightpms.reservation;

import com.chequer.axboot.core.parameter.RequestParams;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import edu.axboot.controllers.dto.*;
import edu.axboot.domain.BaseService;
import edu.axboot.domain.lightpms.guest.Guest;
import edu.axboot.domain.lightpms.guest.GuestService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService extends BaseService<Reservation, Long> {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private ReservationRepository reservationRepository;

    @Inject
    private GuestService guestService;

    @Inject
    private ReservationMemoService memoService;

    @Inject
    public ReservationService(ReservationRepository reservationRepository) {
        super(reservationRepository);
        this.reservationRepository=reservationRepository;
    }

    @Transactional
    public Long saveUsingQueryDsl(ReservationRequestDto requestDto) {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rsvDate = sdf.format(today);

        sdf = new SimpleDateFormat("yyyyMMdd");

        int sno = rsvCnt()+1;
        String rsvNum = "R"+sdf.format(today)+ StringUtils.leftPad(String.valueOf(sno), 3, '0');

        requestDto.setRsvDt(rsvDate);
        requestDto.setRsvNum(rsvNum);
        requestDto.setSno(sno);
        requestDto.setSttusCd("RSV_01");

        if(requestDto.getGuestId() == null || requestDto.getGuestId() == 0) {
            Long guestId = saveGuest(requestDto);
            requestDto.setGuestId(guestId);
        }

        Long id = this.reservationRepository.save(requestDto.toEntity()).getId();

        List<ReservationMemo> memo = requestDto.getMemoList();
        memoService.saveUsingQueryDsl(memo, rsvNum);

        return id;
    }

    public Long saveGuest(ReservationRequestDto requestDto) {
        Guest guest = requestDto.saveGuest();
        Long id = guestService.saveUsingQueryDsl(guest);

        return id;
    }

    public int rsvCnt() {
        BooleanBuilder builder = new BooleanBuilder();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rsvdate = sdf.format(date);

        builder.and(qReservation.rsvDt.eq(rsvdate));

        int cnt = (int) select().from(qReservation).where(builder).fetchCount();
        return cnt;
    }

    public ReservationResponseDto selectOne(Long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qReservation.id.eq(id));

        Reservation reservation = select()
                .from(qReservation)
                .where(builder)
                .fetchOne();

        List<ReservationMemo> memoList = new ArrayList<ReservationMemo>();

        for(ReservationMemo memo : reservation.getMemoList()) {
            if(memo.getDelYn().equals("N")) {
                memoList.add(memo);
            }
        }

        reservation.setMemoList(memoList);

        return new ReservationResponseDto(reservation);
    }

    public List<ReservationListResponseDto> list(RequestParams requestParams) {
        String filter = requestParams.getString("filter", "");
        String rsvNum = requestParams.getString("rsvNum", "");
        String roomTypCd = requestParams.getString("roomTypCd", "");
        String[] sttusCd = requestParams.getString("sttusCd", "").split(",");
        String frontTyp = requestParams.getString("frontTyp", "");

        String rsvDtStart = requestParams.getString("rsvDtStart", "");
        String rsvDtEnd = requestParams.getString("rsvDtEnd", "");
        String arrDt = requestParams.getString("arrDt", "");
        String arrDtStart = requestParams.getString("arrDtStart", "");
        String arrDtEnd = requestParams.getString("arrDtEnd", "");
        String depDtStart = requestParams.getString("depDtStart", "");
        String depDtEnd = requestParams.getString("depDtEnd", "");


        BooleanBuilder builder = new BooleanBuilder();
        BooleanBuilder builder2 = new BooleanBuilder();

        if (isNotEmpty(filter)) {
            builder.or(qReservation.guestNm.contains(filter))
                    .or(qReservation.guestTel.contains(filter))
                    .or(qReservation.guestNmEng.contains(filter));
        }

        if (isNotEmpty(rsvNum)) {
            builder.and(qReservation.rsvNum.contains(rsvNum));
        }

        if (isNotEmpty(rsvDtStart) && isNotEmpty(rsvDtEnd)) {
            builder.and(qReservation.rsvDt.between(rsvDtStart, rsvDtEnd));
        }

        if (isNotEmpty(arrDt)) {
            builder.and(qReservation.arrDt.eq(arrDt));
        }

        if (isNotEmpty(arrDtStart) && isNotEmpty(arrDtEnd)) {
            builder.and(qReservation.arrDt.between(arrDtStart, arrDtEnd));
        }

        if (isNotEmpty(depDtStart) && isNotEmpty(depDtEnd)) {
            builder.and(qReservation.depDt.between(depDtStart, depDtEnd));
        }

        if (isNotEmpty(roomTypCd)) {
            builder.and(qReservation.roomTypCd.eq(roomTypCd));
        }

        if (isNotEmpty(frontTyp)) {
            if(frontTyp.equals("checkIn")) {
                builder2.or(qReservation.sttusCd.eq("RSV_01"))
                        .or(qReservation.sttusCd.eq("RSV_02"))
                        .or(qReservation.sttusCd.eq("RSV_03"));
            } else if(frontTyp.equals("inHouse")) {
                builder2.or(qReservation.sttusCd.eq("CHK_01"));
            } else if(frontTyp.equals("checkOut")) {
                builder2.or(qReservation.sttusCd.eq("CHK_02"))
                        .or(qReservation.sttusCd.eq("CHK_03"));
            }
        }

        if(isNotEmpty(sttusCd[0])) {
            for(int i=0; i<sttusCd.length; i++) {
                builder2.or(qReservation.sttusCd.eq(sttusCd[i]));
            }
        }

        builder.and(builder2);

        List<Reservation> list = select()
                .from(qReservation)
                .where(builder)
                .orderBy(qReservation.id.asc())
                .fetch();

        return  list.stream()
                .map(ReservationListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(List<Long> ids, String sttusCd) {
        for(Long id : ids) {
            if(id != null || id != 0) {
                update(qReservation)
                        .set(qReservation.sttusCd, sttusCd)
                        .where(qReservation.id.eq(id))
                        .execute();
            }
        }
    }

    @Transactional
    public void update(Long id, ReservationUpdateRequestDto request) {
        Reservation reservation = reservationRepository.findOne(id);

        if(reservation == null) {
            throw new IllegalArgumentException("해당 예약건이 없습니다. id=" + id);
        }

        reservation.update(
                request.getRoomTypCd(), request.getRoomNum(),
                request.getArrDt(), request.getArrTime(), request.getDepDt(), request.getDepTime(),
                request.getNightCnt(), request.getAdultCnt(), request.getChildCnt(),
                request.getGuestId(), request.getGuestNm(), request.getGuestNmEng(),
                request.getGuestTel(), request.getEmail(), request.getLangCd(), request.getBirth(), request.getGender(),
                request.getSaleTypCd(), request.getSttusCd(), request.getSrcCd(), request.getPayCd(), request.getAdvnYn(),
                request.getSalePrc(), request.getSvcPrc());

        memoService.saveUsingQueryDsl(request.getMemoList(), reservation.getRsvNum());
    }

    public List<ReservationListResponseDto> rsvList(Long id) {
        BooleanBuilder builder = new BooleanBuilder();

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String today = sdf.format(new Date());

        builder.and(qReservation.guestId.eq(id));
//        builder.and(qReservation.depDt.loe(today));

        List<Reservation> list = select()
                .from(qReservation)
                .where(builder)
                .orderBy(qReservation.arrDt.asc())
                .fetch();

        return list.stream()
                .map(ReservationListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ReservationSalesResponseDto> salesList(RequestParams requestParams) {
        BooleanBuilder builder = new BooleanBuilder();
        String rsvDtStart = requestParams.getString("rsvDtStart", "");
        String rsvDtEnd = requestParams.getString("rsvDtEnd", "");

        if (isNotEmpty(rsvDtStart) && isNotEmpty(rsvDtEnd)) {
            builder.and(qReservation.rsvDt.between(rsvDtStart, rsvDtEnd));
        }

        List<ReservationSalesResponseDto> list = select()
                .select(Projections.fields(
                        ReservationSalesResponseDto.class,
                        qReservation.rsvDt,
                        qReservation.salePrc.castToNum(Integer.class).sum().as("salePrc"),
                        qReservation.svcPrc.castToNum(Integer.class).sum().as("svcPrc"),
                        qReservation.count().as("rsvCnt")))
                .from(qReservation)
                .groupBy(qReservation.rsvDt)
                .where(builder)
                .orderBy(qReservation.rsvDt.asc())
                .fetch();

        List<ReservationSalesResponseDto> sumList = sumList(requestParams);

        List<ReservationSalesResponseDto> saleList = new ArrayList<>();
        saleList.addAll(sumList);
        saleList.addAll(list);

        return saleList;
    }

    public List<ReservationSalesResponseDto> sumList(RequestParams requestParams) {
        BooleanBuilder builder = new BooleanBuilder();
        String rsvDtStart = requestParams.getString("rsvDtStart", "");
        String rsvDtEnd = requestParams.getString("rsvDtEnd", "");

        if (isNotEmpty(rsvDtStart) && isNotEmpty(rsvDtEnd)) {
            builder.and(qReservation.rsvDt.between(rsvDtStart, rsvDtEnd));
        }

        List<ReservationSalesResponseDto> list = select()
                .select(Projections.fields(
                        ReservationSalesResponseDto.class,
                        qReservation.salePrc.castToNum(Integer.class).sum().as("salePrc"),
                        qReservation.svcPrc.castToNum(Integer.class).sum().as("svcPrc"),
                        qReservation.count().as("rsvCnt")))
                .from(qReservation)
                .where(builder)
                .fetch();

        return list;
    }
}
