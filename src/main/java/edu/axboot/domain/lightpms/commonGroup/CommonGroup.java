package edu.axboot.domain.lightpms.commonGroup;

import com.chequer.axboot.core.annotations.ColumnPosition;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.axboot.domain.BaseJpaModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@NoArgsConstructor
@Table(name = "COMMON_GROUP")
public class CommonGroup extends BaseJpaModel<Long> {
    @Id
    @Column(name = "GROUP_ID", precision = 19, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnPosition(1)
    private Long groupId;

    @Column(name = "GROUP_CD", length = 100, nullable = false)
    @ColumnPosition(2)
    private String groupCd;

    @Column(name = "GROUP_NM", length = 100, nullable = false)
    @ColumnPosition(3)
    private String groupNm;

    @Column(name = "PARENT_ID", length = 100)
    @ColumnPosition(4)
    private Long parentId;

    @Column(name = "LEVEL", nullable = false)
    @ColumnPosition(5)
    private int level;

    @Column(name = "SORT", nullable = false)
    @ColumnPosition(6)
    private int sort;

    @Column(name = "ROOT_CD", length = 100, nullable = false)
    @ColumnPosition(7)
    private String rootCd;

    @Column(name = "RMK", length = 255)
    @ColumnPosition(8)
    private String rmk;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @ColumnPosition(9)
    private String useYn;

    @Transient
    private boolean open = false;

    @Transient
    List<CommonGroup> children = new ArrayList<>();

    @JsonProperty("name")
    public String label() {
        return groupNm;
    }

    @JsonProperty("id")
    public Long id() {
        return groupId;
    }

    @JsonProperty("open")
    public boolean isOpen() {
        return open;
    }

//    @OneToMany
//    @JoinColumn(name = "GROUP_CD")
//    private List<CommonCode> codes;

    @Override
    public Long getId() {
        return groupId;
    }

    @Builder
    public CommonGroup(Long groupId, String groupCd, String groupNm, Long parentId,
                       int level, int sort, String rootCd, String rmk, String useYn) {
        this.groupId = groupId;
        this.groupCd = groupCd;
        this.groupNm = groupNm;
        this.parentId = parentId;
        this.level = level;
        this.sort = sort;
        this.rootCd = rootCd;
        this.rmk = rmk;
        this.useYn = useYn;
    }
}
