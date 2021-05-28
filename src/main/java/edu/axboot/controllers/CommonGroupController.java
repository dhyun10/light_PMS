package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import edu.axboot.domain.lightpms.commonGroup.CommonGroup;
import edu.axboot.domain.lightpms.commonGroup.CommonGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/commonGroup")
public class CommonGroupController extends BaseController {
    @Inject
    private CommonGroupService commonGroupService;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(
            RequestParams requestParams
    ) {
        List<CommonGroup> list = commonGroupService.list(requestParams);
        return Responses.ListResponse.of(list);
    }
}
