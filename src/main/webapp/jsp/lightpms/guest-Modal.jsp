<%@ page import="com.chequer.axboot.core.utils.RequestUtils" %> <%@ page contentType="text/html; charset=UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="ax" tagdir="/WEB-INF/tags" %> 
<% 
    RequestUtils requestUtils = RequestUtils.of(request);
    request.setAttribute("guestNm", requestUtils.getString("guestNm")); 
    request.setAttribute("guestTel", requestUtils.getString("guestTel"));
    request.setAttribute("email", requestUtils.getString("email"));
    request.setAttribute("modalView", requestUtils.getString("modalView"));
%>
<ax:set key="title" value="${pageName}" />
<ax:set key="page_desc" value="${PAGE_REMARK}" />
<ax:set key="page_auto_height" value="true" />
<ax:set key="axbody_class" value="baseStyle" />

<ax:layout name="modal">
    <jsp:attribute name="script">
        <ax:script-lang key="ax.script" var="LANG" />
        <script>
            var modalParams = { 
                guestNm: '${guestNm}', 
                guestTel: '${guestTel}', 
                email: '${email}', 
                modalView: '${modalView}'
            };
        </script>
        <script type="text/javascript" src="<c:url value='/assets/js/view/lightpms/guest-Modal.js' />"></script>
    </jsp:attribute>
    <jsp:body>
        <ax:split-layout name="ax1" orientation="horizontal">
            <ax:split-panel height="*">
                <div class="ax-button-group" data-fit-height-aside="grid-view-01">
                    <div class="left">
                        <h2><i class="cqc-list"></i> 투숙객 목록</h2>
                    </div>
                    <div style="float: right;">
                        <div style="display: inline-block;">
                            <input type="text" class="js-guestNm form-control" style="width:120px;" placeholder="이름">
                        </div>
                        <div style="display: inline-block;">
                            <input type="text" class="js-filter form-control" style="width:120px;" placeholder="연락처/이메일">
                        </div>
                        <span><button type="button" class="btn btn-default" data-page-btn="search"> 검색 </button></span>
                    </div>
                </div>
                <div data-ax5grid="grid-view-01" data-fit-height-content="grid-view-01" style="height: 100px"></div>
            </ax:split-panel>
            <ax:split-panel height="*" style="padding-top: 5px;">
                <div data-fit-height-aside="form-view-01">
                    <form name="form" class="js-form">
                        <ax:tbl clazz="ax-form-tbl" minWidth="200px">
                            <ax:tr labelWidth="120px">
                                <ax:td label="이름" width="50%">
                                    <input type="text" name="guestNm" data-ax-path="guestNm" class="form-control" data-ax-validate="required" readonly="readonly">
                                </ax:td>
                                <ax:td label="영문" width="50%"><input type="text" name="guestNmEng" data-ax-path="guestNmEng" class="form-control" >
                                </ax:td>
                            </ax:tr>

                            <ax:tr labelWidth="120px">
                                <ax:td label="연락처" width="50%">
                                    <input type="text" name="guestTel" data-ax-path="guestTel" class="form-control" />
                                </ax:td>
                                <ax:td label="이메일" width="50%">
                                    <input type="text" name="email" data-ax-path="email" class="form-control"  />
                                </ax:td>
                            </ax:tr>

                            <ax:tr labelWidth="120px">
                                <ax:td label="언어" width="50%">
                                    <ax:common-code groupCd="PMS_LANG" name="langCd" dataPath="langCd" clazz="form-control W100" />
                                </ax:td>
                                <ax:td label="생년월일" width="50%">
                                    <div style="display: inline-block;">
                                        <input type="date" name="birth" data-ax-path="birth" class="form-control" style="width: 150px;" />
                                    </div>
                                    <span>
                                        <input type="radio" value="F" name="gender" data-ax-path="gender" > 여
                                        <input type="radio" value="M" name="gender" data-ax-path="gender" > 남
                                    </span>
                                </ax:td>
                            </ax:tr>

                            <ax:tr labelWidth="120px">
                                <ax:td label="비고" width="100%">
                                    <textarea name="rmk" data-ax-path="rmk" class="form-control"></textarea>
                                </ax:td>
                            </ax:tr>
                        </ax:tbl>
                    </form>
                    <div style="text-align: center; margin-top: 10px;">
                      <button type="button" class="btn btn-info" data-page-btn="select"> 선택 </button>
                      <button type="button" class="btn btn-default" data-page-btn="close"> 닫기 </button>
                   </div>
                </div>
            </ax:split-panel>
        </ax:split-layout>
    </jsp:body>
</ax:layout>
