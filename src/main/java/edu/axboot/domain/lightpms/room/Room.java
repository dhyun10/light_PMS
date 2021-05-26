package edu.axboot.domain.lightpms.room;

import com.chequer.axboot.core.annotations.ColumnPosition;
import edu.axboot.domain.BaseJpaModel;
import edu.axboot.domain.SimpleJpaModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@NoArgsConstructor
@Table(name = "PMS_ROOM")
public class Room extends BaseJpaModel<Long> {
    @Id
    @Column(name = "ID", precision = 19, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnPosition(1)
    private Long id;

    @Column(name = "ROOM_NUM", length = 10, nullable = false)
    @ColumnPosition(2)
    private String roomNum;

    @Column(name = "ROOM_TYP_CD", length = 20, nullable = false)
    @ColumnPosition(3)
    private String roomTypCd;

    @Column(name = "DND_YN", length = 1, nullable = false)
    @ColumnPosition(4)
    private String dndYn;

    @Column(name = "EB_YN", length = 1, nullable = false)
    @ColumnPosition(5)
    private String ebYn;

    @Column(name = "ROOM_STTUS_CD", length = 20)
    @ColumnPosition(6)
    private String roomSttusCd;

    @Column(name = "CLN_STTUS_CD", length = 20)
    @ColumnPosition(7)
    private String clnSttusCd;

    @Column(name = "SVC_STTUS_CD", length = 20)
    @ColumnPosition(8)
    private String svcSttusCd;

    @Override
    public Long getId() {
        return id;
    }

    @Builder
    public Room(Long id, String roomNum, String roomTypCd, String dndYn, String ebYn,
                String roomSttusCd, String clnSttusCd, String svcSttusCd,
                boolean isCreated, boolean isModified, boolean isDeleted) {
        this.id = id;
        this.roomNum = roomNum;
        this.roomTypCd = roomTypCd;
        this.dndYn = dndYn;
        this.ebYn = ebYn;
        this.roomSttusCd = roomSttusCd;
        this.clnSttusCd = clnSttusCd;
        this.svcSttusCd = svcSttusCd;
        this.__created__ = isCreated;
        this.__modified__ = isModified;
        this.__deleted__ = isDeleted;
    }

}
