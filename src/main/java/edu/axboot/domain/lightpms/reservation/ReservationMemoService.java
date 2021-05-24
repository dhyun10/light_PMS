package edu.axboot.domain.lightpms.reservation;

import edu.axboot.domain.BaseService;
import edu.axboot.utils.SessionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
public class ReservationMemoService extends BaseService<ReservationMemo, Long> {

    private ReservationMemoRepository reservationMemoRepository;

    @Inject
    private ReservationService reservationService;

    @Inject
    public ReservationMemoService(ReservationMemoRepository reservationMemoRepository) {
        super(reservationMemoRepository);
        this.reservationMemoRepository = reservationMemoRepository;
    }

    @Transactional
    public void saveUsingQueryDsl(List<ReservationMemo> memoList, String rsvNum) {
        int sno = 1;

        for(ReservationMemo memo : memoList) {
            memo.setRsvNum(rsvNum);
            memo.setSno(sno);
            memo.setMemoMan(SessionUtils.getCurrentLoginUserCd());

            if(memo.is__deleted__()) {
                memo.setDelYn("Y");
                memo.setSno(0);
            }

            sno++;
        }
        this.reservationMemoRepository.save(memoList);
    }

}
