package edu.axboot.domain.lightpms.guest;

import com.chequer.axboot.core.annotations.ColumnPosition;
import edu.axboot.controllers.dto.GuestResponseDto;
import edu.axboot.controllers.dto.ReservationListResponseDto;
import edu.axboot.domain.BaseJpaModel;
import edu.axboot.domain.lightpms.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@NoArgsConstructor
@Table(name = "PMS_GUEST")
public class Guest extends BaseJpaModel<Long> {
    @Id
    @Column(name = "ID", precision = 19, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnPosition(1)
    private Long id;

    @Column(name = "GUEST_NM", length = 100, nullable = false)
    @ColumnPosition(2)
    private String guestNm;

    @Column(name = "GUEST_NM_ENG", length = 100)
    @ColumnPosition(3)
    private String guestNmEng;

    @Column(name = "GUEST_TEL", length = 18)
    @ColumnPosition(4)
    private String guestTel;

    @Column(name = "EMAIL", length = 100)
    @ColumnPosition(5)
    private String email;

    @Column(name = "BRTH", length = 10)
    @ColumnPosition(6)
    private String birth;

    @Column(name = "GENDER", length = 20)
    @ColumnPosition(7)
    private String gender;

    @Column(name = "LANG_CD", length = 20)
    @ColumnPosition(8)
    private String langCd;

    @Column(name = "RMK", length = 500)
    @ColumnPosition(9)
    private String rmk;

    @Builder
    public Guest(Long id, String guestNm, String guestNmEng,
                 String guestTel, String email, String birth,
                 String gender, String langCd) {
        this.id = id;
        this.guestNm = guestNm;
        this.guestNmEng = guestNmEng;
        this.guestTel = guestTel;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.langCd = langCd;
    }

    @Override
    public Long getId() {
        return id;
    }

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
//    @JoinColumn(name = "GUEST_ID", referencedColumnName = "ID", insertable = false, updatable = false)
//    private List<Reservation> rsvList;
}
