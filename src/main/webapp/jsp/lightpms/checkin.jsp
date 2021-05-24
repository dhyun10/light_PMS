<%@ page contentType="text/html; charset=UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="ax"
tagdir="/WEB-INF/tags" %>

<ax:set key="title" value="${pageName}" />
<ax:set key="page_desc" value="${PAGE_REMARK}" />
<ax:set key="page_auto_height" value="true" />

<ax:layout name="base">
    <jsp:attribute name="script">
        <script type="text/javascript" src="<c:url value='/assets/js/view/lightpms/checkin.js' />"></script>
    </jsp:attribute>
    <jsp:body>
        <div data-page-buttons>
            <div class="button-warp">
                <button type="button" class="btn btn-info" data-page-btn="search"><i class="fas fa-search"></i> 검색</button>
                <button type="button" class="btn btn-default" data-page-btn="clear">검색선택초기화</button>
                <button type="button" class="btn btn-default" data-page-btn="excel"><i class="far fa-file-excel"></i> 엑셀다운로드</button>
            </div>
        </div>

        <form name="excelForm" class="js-form" method="post">
        <div role="page-header">
            <ax:form name="searchView0">
                <ax:tbl clazz="ax-search-tbl" minWidth="500px">
                    <ax:tr>
                        <ax:td label="검색어" width="30%">
                            <input type="text" name="filter" class="js-filter form-control" />
                        </ax:td>
                        <ax:td label="예약번호" width="30%">
                            <input type="text" name="rsvNum" class="js-rsvNum form-control" />
                        </ax:td>
                        <ax:td label="도착일" width="30%">
                            <input type="date" name="arrDt" class="js-arrDt form-control" style="width: 150px;">
                        </ax:td>
                    </ax:tr>
                </ax:tbl>
            </ax:form>
            <div class="H10"></div>
        </div>
            <input type="hidden" name="sttusCd" value="RSV_01,RSV_02,RSV_03">
        </form>

        <ax:split-layout name="ax1" orientation="horizontal">
            <ax:split-panel width="*">
                <!-- 목록 -->
                <div class="ax-button-group" data-fit-height-aside="grid-view-01">
                    <div class="left">
                        <h2><i class="cqc-list"></i> 도착 목록</h2>
                    </div>
                </div>
                <div data-ax5grid="grid-view-01" data-fit-height-content="grid-view-01" style="height: 300px"></div>
            </ax:split-panel>
        </ax:split-layout>
    </jsp:body>
</ax:layout>
