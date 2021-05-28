package edu.axboot.domain.lightpms.commonGroup;

import com.chequer.axboot.core.parameter.RequestParams;
import com.querydsl.core.BooleanBuilder;
import edu.axboot.domain.BaseService;
import org.apache.xpath.operations.Bool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
        return list;
    }

    @Transactional
    public Long saveGroup(CommonGroup commonGroup) {
        Long id = this.commonGroupRepository.save(commonGroup).getGroupId();
        return id;
    }

}
