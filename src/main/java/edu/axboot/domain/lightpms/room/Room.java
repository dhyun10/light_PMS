package edu.axboot.domain.lightpms.room;

import com.chequer.axboot.core.annotations.ColumnPosition;
import edu.axboot.domain.BaseJpaModel;
import edu.axboot.domain.SimpleJpaModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
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

}
