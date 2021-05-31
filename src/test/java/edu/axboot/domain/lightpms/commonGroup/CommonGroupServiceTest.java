package edu.axboot.domain.lightpms.commonGroup;
import com.chequer.axboot.core.parameter.RequestParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.axboot.AXBootApplication;
import lombok.extern.java.Log;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AXBootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommonGroupServiceTest {
    public static Logger logger = LoggerFactory.getLogger(CommonGroupServiceTest.class);

    @Autowired
    private CommonGroupService service;

    @Test
    public void test1_공통코드그룹_추가() {
        CommonGroup commonGroup = CommonGroup.builder()
                .groupCd("SYSTEM")
                .groupNm("공통2")
                .parentId(2L)
                .level(1)
                .sort(2)
                .rootCd("SYSTEM")
                .useYn("Y")
                .build();

        Long id = service.saveGroup(commonGroup);

        assertTrue(id > 0);
    }

    @Test
    public void test2_공통코드그룹_목록() {
        RequestParams params = new RequestParams();
        params.put("rootCd", "SYSTEM");

        List<CommonGroup> list = service.list(params);

        assertTrue(list.size() > 0);
    }

    @Test
    public void test3_JsonProperty() throws JsonProcessingException {
        CommonGroup commonGroup = new CommonGroup();
        commonGroup.setGroupId(1L);
        commonGroup.setGroupNm("테스트");

        logger.info(new ObjectMapper().writeValueAsString(commonGroup));
    }

}
