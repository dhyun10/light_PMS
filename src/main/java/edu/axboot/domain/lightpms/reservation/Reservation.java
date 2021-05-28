package edu.axboot.domain.lightpms.reservation;

import com.chequer.axboot.core.annotations.ColumnPosition;
import edu.axboot.domain.BaseJpaModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "PMS_CHK")
@NoArgsConstructor
public class Reservation extends BaseJpaModel<Long> {
    @Id
    @Column(name = "ID", precision = 19, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnPosition(1)
    private Long id;

    @Column(name = "RSV_DT", precision = 10, nullable = false)
    private String rsvDt;

    @Column(name = "SNO", precision = 10, nullable = false)
    private int sno;

    @Column(name = "RSV_NUM", precision = 20, nullable = false)
    private String rsvNum;

    @Column(name = "GUEST_ID", precision = 19)
    private Long guestId;

    @Column(name = "GUEST_NM", precision = 100)
    private String guestNm;

    @Column(name = "GUEST_NM_ENG", precision = 200)
    private String guestNmEng;

    @Column(name = "GUEST_TEL", precision = 18)
    private String guestTel;

    @Column(name = "EMAIL", precision = 100)
    private String email;

    @Column(name = "LANG_CD", precision = 20)
    private String langCd;

    @Column(name = "ARR_DT", precision = 10, nullable = false)
    private String arrDt;

    @Column(name = "ARR_TIME", precision = 8)
    private String arrTime;

    @Column(name = "DEP_DT", precision = 10, nullable = false)
    private String depDt;

    @Column(name = "DEP_TIME", precision = 8)
    private String depTime;

    @Column(name = "NIGHT_CNT", nullable = false)
    private int nightCnt;


    @Column(name = "ROOM_TYP_CD", precision = 20, nullable = false)
    private String roomTypCd;

    @Column(name = "ROOM_NUM", precision = 10)
    private String roomNum;

    @Column(name = "ADULT_CNT", nullable = false)
    private int adultCnt;

    @Column(name = "CHLD_CNT", nullable = false)
    private int childCnt;


    @Column(name = "SALE_TYP_CD", precision = 20, nullable = false)
    private String saleTypCd;

    @Column(name = "STTUS_CD", precision = 20, nullable = false)
    private String sttusCd;

    @Column(name = "SRC_CD", precision = 20, nullable = false)
    private String srcCd;


    @Column(name = "BRTH", precision = 10)
    private String birth;

    @Column(name = "GENDER", precision = 20)
    private String gender;


    @Column(name = "PAY_CD", precision = 20)
    private String payCd;

    @Column(name = "ADVN_YN", precision = 1, nullable = false)
    private String advnYn;

    @Column(name = "SALE_PRC", precision = 18)
    private BigDecimal salePrc;

    @Column(name = "SVC_PRC", precision = 18)
    private BigDecimal svcPrc;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "RSV_NUM", referencedColumnName = "RSV_NUM", insertable = false, updatable = false)
    private List<ReservationMemo> memoList;

    @Builder
    public Reservation(
            String rsvDt, int sno, String rsvNum, String roomTypCd, String roomNum,
            String arrDt, String arrTime, String depDt, String depTime, int nightCnt, int adultCnt, int childCnt,
            Long guestId, String guestNm, String guestNmEng, String guestTel, String email, String langCd, String birth, String gender,
            String saleTypCd, String sttusCd, String srcCd, String payCd, String advnYn, BigDecimal salePrc, BigDecimal svcPrc) {
        this.rsvDt = rsvDt;
        this.sno = sno;
        this.rsvNum = rsvNum;
        this.roomTypCd = roomTypCd;
        this.roomNum = roomNum;
        this.arrDt = arrDt;
        this.arrTime = arrTime;
        this.depDt = depDt;
        this.depTime = depTime;
        this.nightCnt = nightCnt;
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
        this.sttusCd = sttusCd;
        this.srcCd = srcCd;
        this.payCd = payCd;
        this.advnYn = advnYn;
        this.salePrc = salePrc;
        this.svcPrc = svcPrc;
    }

    @PrePersist
    public void prePersist() {
        this.advnYn = this.advnYn == null ? "N" : this.advnYn;
    }

    public void update(String roomTypCd, String roomNum,
                       String arrDt, String arrTime, String depDt, String depTime, int nightCnt, int adultCnt, int childCnt,
                       Long guestId, String guestNm, String guestNmEng, String guestTel, String email, String langCd, String birth, String gender,
                       String saleTypCd, String sttusCd, String srcCd, String payCd, String advnYn, BigDecimal salePrc, BigDecimal svcPrc) {
        this.roomTypCd = roomTypCd;
        this.roomNum = roomNum;
        this.arrDt = arrDt;
        this.arrTime = arrTime;
        this.depDt = depDt;
        this.depTime = depTime;
        this.nightCnt = nightCnt;
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
        this.sttusCd = sttusCd;
        this.srcCd = srcCd;
        this.payCd = payCd;
        this.advnYn = advnYn;
        this.salePrc = salePrc;
        this.svcPrc = svcPrc;
    }

    @Override
    public Long getId() {
        return id;
    }
}
