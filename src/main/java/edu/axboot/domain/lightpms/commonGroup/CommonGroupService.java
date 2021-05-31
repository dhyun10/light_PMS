package edu.axboot.domain.lightpms.commonGroup;

import com.chequer.axboot.core.parameter.RequestParams;
import com.querydsl.core.BooleanBuilder;
import edu.axboot.domain.BaseService;
import org.apache.xpath.operations.Bool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonGroupService extends BaseService<CommonGroup, Long> {
    private final CommonGroupRepository commonGroupRepository;

    @Inject
    public CommonGroupService(CommonGroupRepository commonGroupRepository) {
        super(commonGroupRepository);
        this.commonGroupRepository = commonGroupRepository;
    }

    public List<CommonGroup> list(RequestParams requestParams) {
        String rootCd = requestParams.getString("rootCd", "SYSTEM");
        boolean menuOpen = requestParams.getBoolean("menuOpen", true);

        BooleanBuilder builder = new BooleanBuilder();

        if(isNotEmpty(rootCd)) {
            builder.and(qCommonGroup.rootCd.eq(rootCd));
        }

        List<CommonGroup> list = select()
                .from(qCommonGroup)
                .where(builder)
                .orderBy(qCommonGroup.level.asc(), qCommonGroup.sort.asc())
                .fetch();

        List<CommonGroup> parentList = new ArrayList<>();

        for (CommonGroup commonGroup : list) {
            commonGroup.setOpen(menuOpen);

            CommonGroup parent = getParent(parentList, commonGroup);

            if (parent == null) {
                parentList.add(commonGroup);
            } else {
                parent.addChildren(commonGroup);
            }
        }

        return parentList;
    }

    @Transactional
    public Long saveGroup(CommonGroup commonGroup) {
        Long id = this.commonGroupRepository.save(commonGroup).getGroupId();
        return id;
    }

    public CommonGroup getParent(List<CommonGroup> list, CommonGroup commonGroup) {
        CommonGroup parent = list.stream().filter(m -> m.getId().equals(commonGroup.getParentId())).findAny().orElse(null);

        if (parent == null) {
            for (CommonGroup commonGroup1 : list) {
                parent = getParent(commonGroup1.getChildren(), commonGroup);

                if (parent != null)
                    break;
            }
        }
        return parent;
    }

}
