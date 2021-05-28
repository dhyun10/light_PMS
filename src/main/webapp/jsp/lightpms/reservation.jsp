<%@ page contentType="text/html; charset=UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="ax"
tagdir="/WEB-INF/tags" %>

<ax:set key="title" value="${pageName}" />
<ax:set key="page_desc" value="${PAGE_REMARK}" />
<ax:set key="page_auto_height" value="true" />

<ax:layout name="base">
    <jsp:attribute name="script">
        <script type="text/javascript" src="<c:url value='/assets/js/view/lightpms/reservation.js' />"></script>
    </jsp:attribute>
    <jsp:body>
            
        <div data-page-buttons>
            <div class="button-warp">
                <div style="display: inline-block; margin-right: 10px;"><span style="color: red;">* </span>표시는 필수 항목 체크 부분</div>
                <button type="button" class="btn btn-info" data-page-btn="save">
                    저장
                </button>
                <button type="button" class="btn btn-default" data-page-btn="clear">
                    신규등록
                </button>
            </div>
        </div>

        <ax:split-layout name="ax1" orientation="horizontal">
            <ax:split-panel height="*" scroll="scroll">
                <form name="formView01" class="reservationForm">
                    <div role="page-header">
                        <div style="font-weight: bold;">예약번호 : <input style="border: 0; background: none;" data-ax-path="rsvNum" name="rsvNum" class="js-rsvNum" readonly="readonly"></div>
                        <div class="H10"></div>
                    </div>
                    <div data-ax-tbl class="ax-form-tbl">
                        <div data-ax-tr>
                            <div data-ax-td>
                                <div data-ax-td-label><span style="color: red;">* </span>도착일</div>
                                <div data-ax-td-wrap>
                                <div class="input-group" data-ax5picker="arrDt">
                                    <input type="text" data-ax-path="arrDt" name="arrDt" class="js-arrDt form-control" placeholder="yyyy-mm-dd">
                                    <span class="input-group-addon"><i class="fa fa-calendar-o"></i></span>
                                </div>          
                                </div>
                            </div>  
                            <div data-ax-td>
                                <div data-ax-td-label><span style="color: red;">* </span>숙박수</div>
                                <div data-ax-td-wrap>
                                <input type="text" data-ax-path="nightCnt" name="nightCnt" class="js-nightCnt form-control" data-ax-validate="required" style="width: 50px;">        
                                </div>
                            </div>  
                            <div data-ax-td>
                                <div data-ax-td-label><span style="color: red;">* </span>출발일</div>
                                <div data-ax-td-wrap>
                                    <div class="input-group" data-ax5picker="depDt">
                                        <input type="text" data-ax-path="depDt" name="depDt" class="js-depDt form-control" placeholder="yyyy-mm-dd">
                                        <span class="input-group-addon"><i class="fa fa-calendar-o"></i></span>
                                    </div>     
                                </div>
                            </div>  
                        </div>
                        <div data-ax-tr>
                            <div data-ax-td>
                                <div data-ax-td-label><span style="color: red;">* </span>객실타입</div>
                                <div data-ax-td-wrap>
                                    <ax:common-code groupCd="PMS_ROOM_TYPE" name="roomTypCd" dataPath="roomTypCd" clazz="form-control W100" />
                                </div>
                            </div>  
                            <div data-ax-td>
                                <div data-ax-td-label><span style="color: red;">* </span>성인수</div>
                                <div data-ax-td-wrap>
                                <input type="text" data-ax-path="adultCnt" name="adultCnt" class="form-control" data-ax-validate="required" style="width: 50px;" >               
                                </div>
                            </div>  
                            <div data-ax-td>
                                <div data-ax-td-label><span style="color: red;">* </span>아동수</div>
                                <div data-ax-td-wrap>
                                <input type="text" data-ax-path="childCnt" name="childCnt" class="form-control" data-ax-validate="required" style="width: 50px;" >               
                                </div>
                            </div>  
                        </div>
                        <div data-ax-tr>
                            <div data-ax-td style="width: 100%;">
                                <div data-ax-td-label>투숙객
                                    <p style="padding-top: 10px;"><button type="button" class="btn btn-default" data-form-view-01-btn="searchGuest">
                                        <i class="cqc-magnifier"></i>검색</button>
                                    </p>
                                </div>
                                    <div data-ax-tr>
                                        <div data-ax-td style="width:50%">
                                            <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">이름</div>
                                            <div data-ax-td-wrap style="border-right: 1px solid #ccc;">
                                                <input type="hidden" data-ax-path="guestId" name="guestId" />
                                                <input type="text" data-ax-path="guestNm" name="guestNm" class="js-guestNm form-control" title="투숙객 이름" style="width: 150px;" />
                                            </div>
                                        </div> 
                                        <div data-ax-td style="width:50%">
                                            <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">영문</div>
                                            <div data-ax-td-wrap>
                                                <input type="text" data-ax-path="guestNmEng" name="guestNmEng" class="form-control" style="width: 150px;" />
                                            </div>
                                        </div>                                                                      
                                    </div>
                                    <div data-ax-tr>
                                        <div data-ax-td style="width:50%">
                                            <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">연락처</div>
                                            <div data-ax-td-wrap style="border-right: 1px solid #ccc;">
                                                <input type="text" data-ax-path="guestTel" name="guestTel" class="js-guestTel form-control"  title="투숙객 연락처" style="width: 150px;" />
                                            </div>
                                        </div> 
                                        <div data-ax-td style="width:50%">
                                            <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">이메일</div>
                                            <div data-ax-td-wrap>
                                                <input type="text" data-ax-path="email" name="email" class="js-email form-control" style="width: 150px;" />
                                            </div>
                                        </div>                                                                      
                                    </div>
                                    <div data-ax-tr>
                                        <div data-ax-td style="width:50%">
                                            <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">언어</div>
                                            <div data-ax-td-wrap style="border-right: 1px solid #ccc;">
                                                <ax:common-code groupCd="PMS_LANG" name="langCd" dataPath="langCd" clazz="form-control W100" />
                                            </div>
                                        </div> 
                                        <div data-ax-td style="width:50%">
                                            <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">생년월일</div>
                                            <div data-ax-td-wrap >
                                                <div class="input-group" data-ax5picker="birth">
                                                    <input type="text" data-ax-path="birth" name="birth" class="js-birth form-control" placeholder="yyyy-mm-dd">
                                                    <span class="input-group-addon"><i class="fa fa-calendar-o"></i></span>
                                                </div>
                                            </div>
                                            <span>
                                                <input type="radio" value="F" name="gender" data-ax-path="gender" > 여
                                                <input type="radio" value="M" name="gender" data-ax-path="gender" > 남
                                            </span>
                                        </div>                                
                                </div>
                            </div>
                            </div>
                        <div data-ax-tr>
                            <div data-ax-td style="width: 100%;">
                                <div data-ax-td-label>판매/결제</div>
                                        <div data-ax-tr>
                                            <div data-ax-td style="width:50%">
                                                <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">판매유형</div>
                                                <div data-ax-td-wrap style="border-right: 1px solid #ccc;">
                                                    <ax:common-code groupCd="PMS_SALE_TYPE" name="saleTypCd" dataPath="saleTypCd" clazz="form-control W100" />
                                                </div>
                                            </div> 
                                            <div data-ax-td style="width:50%">
                                                <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">예약경로</div>
                                                <div data-ax-td-wrap>
                                                    <ax:common-code groupCd="PMS_RESERVATION_ROUTE" name="srcCd" dataPath="srcCd" clazz="form-control W100" />
                                                </div>
                                            </div>                                                                      
                                        </div>
                                        <div data-ax-tr>
                                            <div data-ax-td style="width:50%">
                                                <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">결제방법</div>
                                                <div data-ax-td-wrap style="border-right: 1px solid #ccc;">
                                                    <ax:common-code groupCd="PMS_PAY_METHOD" name="payCd" dataPath="payCd" clazz="form-control W100" />
                                                </div>
                                            </div> 
                                            <div data-ax-td style="width:50%">
                                                <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">선수금여부</div>
                                                <div data-ax-td-wrap>
                                                    <input type="checkbox" data-ax-path="advnYn" name="advnYn" class="js-advnYn" value="Y" />
                                                </div>
                                            </div>                                                                      
                                        </div>
                                        <div data-ax-tr>
                                            <div data-ax-td style="width:50%">
                                                <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">결제금액</div>
                                                <div data-ax-td-wrap style="border-right: 1px solid #ccc;">
                                                    <input type="text" data-ax-path="salePrc" name="salePrc" class="form-control" data-ax5formatter="money" style="width: 150px;" />
                                                </div>
                                            </div> 
                                            <div data-ax-td style="width:50%">
                                                <div data-ax-td-label style="width:120px; background-color: #fff; background-image: none;">서비스금액</div>
                                                <div data-ax-td-wrap>
                                                    <input type="text" data-ax-path="svcPrc" name="svcPrc" class="form-control" data-ax5formatter="money" style="width: 150px;" />
                                        </div>                         
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div data-ax-tr>
                            <div data-ax-td style="width: 100%;">
                                <div data-ax-td-label>투숙메모</div>
                                <div class="ax-button-group" data-fit-height-aside="grid-view-01">
                                    <div class="left">
                                        <h2>투숙메모 </h2>
                                    </div>
                                    <div class="right">
                                        <button type="button" class="btn btn-default" data-grid-view-01-btn="add"><i class="cqc-circle-with-plus"></i> 추가</button>
                                        <button type="button" class="btn btn-default" data-grid-view-01-btn="delete"><i class="cqc-circle-with-minus"></i> 삭제</button>
                                    </div>
                                </div>
                                <div data-ax5grid="grid-view-01" data-fit-height-content="grid-view-01" style="max-height: 100px;"></div>
                            </div>
                        </div>
                    </div>
                </form>
            </ax:split-panel>
        </ax:split-layout>
    </jsp:body>
</ax:layout>
