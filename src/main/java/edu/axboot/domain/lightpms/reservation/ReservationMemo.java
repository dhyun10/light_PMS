package edu.axboot.domain.lightpms.reservation;


import com.chequer.axboot.core.annotations.ColumnPosition;
import edu.axboot.domain.BaseJpaModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Entity
@Table(name = "PMS_CHK_MEMO")
public class ReservationMemo extends BaseJpaModel<Long> {

    @Id
    @Column(name = "ID", precision = 19, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnPosition(1)
    private Long id;

    @Column(name = "RSV_NUM", precision = 20)
    @ColumnPosition(2)
    private String rsvNum;

    @Column(name = "SNO", nullable = false)
    @ColumnPosition(3)
    private int sno;

    @Column(name = "MEMO_CN", precision = 4000, nullable = false)
    @ColumnPosition(4)
    private String memoCn;

    @Column(name = "MEMO_DTTI", nullable = false)
    @ColumnPosition(5)
    private String memoDtti;

    @Column(name = "MEMO_MAN", precision = 100, nullable = false)
    @ColumnPosition(6)
    private String memoMan;

    @Column(name = "DEL_YN", precision = 1, nullable = false)
    @ColumnPosition(7)
    private String delYn;

    @PrePersist
    public void prePersist() {
        this.delYn = this.delYn == null ? "N" : this.delYn;
    }

    @Builder
    public ReservationMemo(String rsvNum, int sno, String memoCn, String memoDtti, String memoMan, String delYn) {
        this.rsvNum = rsvNum;
        this.sno = sno;
        this.memoCn = memoCn;
        this.memoDtti = memoDtti;
        this.memoMan = memoMan;
        this.delYn = delYn;
    }
}
