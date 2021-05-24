package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import com.chequer.axboot.core.utils.DateUtils;
import com.chequer.axboot.core.utils.ExcelUtils;
import com.querydsl.core.Tuple;
import com.wordnik.swagger.annotations.ApiOperation;
import edu.axboot.controllers.dto.*;
import edu.axboot.domain.lightpms.reservation.Reservation;
import edu.axboot.domain.lightpms.reservation.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/api/v1/reservation")
public class ReservationController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Inject
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.POST, produces = APPLICATION_JSON)
    @ResponseBody
    public Map<String, Object> save(@RequestBody ReservationRequestDto reservation) {
        String rsvNum = reservationService.saveUsingQueryDsl(reservation);

        Map<String, Object> model = new HashMap<>();
        model.put("ApiStatus.SUCCESS", "success");
        model.put("rsvNum", rsvNum);

        return model;
    }

    @RequestMapping(method = RequestMethod.PUT, produces = APPLICATION_JSON)
    public ApiResponse update(
            @RequestParam List<Long> ids,
            @RequestParam String sttusCd) {
        reservationService.update(ids, sttusCd);
        return ok();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON)
    public ApiResponse updateSttusCd(
            @PathVariable Long id,
            @RequestBody ReservationUpdateRequestDto request) {
        reservationService.update(id, request);
        return ok();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(
            RequestParams requestParams) {
        List<ReservationListResponseDto> list = reservationService.list(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/list/{frontTyp}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse checkInList(
            @PathVariable(value = "frontTyp") String frontTyp,
            RequestParams requestParams) {
        requestParams.put("frontTyp", frontTyp);
        List<ReservationListResponseDto> list = reservationService.list(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ReservationResponseDto select(@PathVariable Long id) {
        ReservationResponseDto reservation = reservationService.selectOne(id);
        return reservation;
    }

    @ApiOperation(value = "엑셀다운로드", notes = "/resources/excel/pms_reservation.xlsx")
    @RequestMapping(value = "/excelDownload/{frontTyp}", method = RequestMethod.POST, produces = APPLICATION_JSON)
    public void excelDownload(
            @PathVariable(value = "frontTyp") String frontTyp,
            RequestParams requestParams,HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ReservationListResponseDto> list = reservationService.list(requestParams);
        ExcelUtils.renderExcel("excel/pms_reservation.xlsx", list, "reservation_"+frontTyp+"_"+ DateUtils.getYyyyMMddHHmmssWithDate(), request, response);
    }

    @RequestMapping(value = "/sales", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public List<ReservationSalesResponseDto> salesList(RequestParams requestParams) {
        List<ReservationSalesResponseDto> list = reservationService.salesList(requestParams);
        return list;
    }

    @ApiOperation(value = "엑셀다운로드", notes = "/resource/excel/pms_sales.xlsx")
    @RequestMapping(value = "/sales/excelDownload", method = RequestMethod.POST, produces = APPLICATION_JSON)
    public void salesExcelDownload(RequestParams requestParams, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ReservationSalesResponseDto> list = reservationService.salesList(requestParams);
        ExcelUtils.renderExcel("excel/pms_sales.xlsx", list, "sales_"+ DateUtils.getYyyyMMddHHmmssWithDate(), request, response);
    }

}