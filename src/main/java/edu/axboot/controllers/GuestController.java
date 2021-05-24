package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import com.chequer.axboot.core.utils.DateUtils;
import com.chequer.axboot.core.utils.ExcelUtils;
import com.wordnik.swagger.annotations.ApiOperation;
import edu.axboot.controllers.dto.GuestResponseDto;
import edu.axboot.domain.lightpms.guest.Guest;
import edu.axboot.domain.lightpms.guest.GuestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/v1/guest")
public class GuestController extends BaseController {

    @Inject
    private GuestService guestService;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(RequestParams<Guest> request) {
        List<Guest> list = guestService.list(request);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public GuestResponseDto select(@PathVariable Long id) {
        GuestResponseDto guest = guestService.selectOne(id);
        return guest;
    }

//    @RequestMapping(value = "/{id}/rsvList", method = RequestMethod.GET, produces = APPLICATION_JSON)
//    public Responses.ListResponse rsvList(@PathVariable Long id) {
//        List<Guest> list = guestService.rsvList(id);
//        return Responses.ListResponse.of(list);
//    }

    @RequestMapping(method = RequestMethod.POST, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody Guest guest) {
        guestService.saveUsingQueryDsl(guest);
        return ok();
    }

    @ApiOperation(value = "엑셀다운로드", notes = "/resources/excel/pms_guest.xlsx")
    @RequestMapping(value = "/excelDownload", method = RequestMethod.POST, produces = APPLICATION_JSON)
    public void excelDownload(RequestParams requestParams, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Guest> list = guestService.list(requestParams);
        ExcelUtils.renderExcel("excel/pms_guest.xlsx", list, "guest_"+ DateUtils.getYyyyMMddHHmmssWithDate(), request, response);
    }

}
