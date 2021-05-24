<%@ page contentType="text/html; charset=UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="ax"
tagdir="/WEB-INF/tags" %>

<ax:set key="title" value="${pageName}" />
<ax:set key="page_desc" value="${PAGE_REMARK}" />
<ax:set key="page_auto_height" value="true" />

<ax:layout name="base">
    <jsp:attribute name="script">
        <script type="text/javascript" src="<c:url value='/assets/js/view/lightpms/sales.js' />"></script>
    </jsp:attribute>
    <jsp:body>
        <ax:page-buttons></ax:page-buttons>

        <form name="excelForm" class="js-form" method="post">
            <div role="page-header">
                <form name="searchView0" id="searchView0" method="post" onsubmit="return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);">
                    <div data-ax-tbl class="ax-search-tbl">
                        <div data-ax-tr>
                            <div data-ax-td style="width: 100%">
                                <div data-ax-td-label>조회날짜</div>
                                <div data-ax-td-wrap style="display: flex">
                                    <button type="button" class="btn btn-default" name="period" data-value="d-0">오늘</button>
                                    <button type="button" class="btn btn-default" name="period" data-value="d-1">어제</button>
                                    <button type="button" class="btn btn-default" name="period" data-value="d-3">3일</button>
                                    <button type="button" class="btn btn-default" name="period" data-value="d-7">7일</button>
                                    <button type="button" class="btn btn-default" name="period" data-value="M-1">1개월</button>
                                    <button type="button" class="btn btn-default" name="period" data-value="M-3">3개월</button>
                                    <button type="button" class="btn btn-default" name="period" data-value="M-6">6개월</button>
                                    <button type="button" class="btn btn-default" name="period" data-value="y-1">1년</button>
                                    <div class="form-group" data-ax5picker="rsvDt" style="width: 300px">
                                        <div class="input-group">
                                            <input
                                                type="text"
                                                class="js-rsvDtStart form-control"
                                                name="rsvDtStart"
                                                data-ax-path="rsvDtStart"
                                                data-ax5formatter="date"
                                                placeholder="yyyy-mm-dd"
                                            />
                                            <span class="input-group-addon">~</span>
                                            <input
                                                type="text"
                                                class="js-rsvDtEnd form-control"
                                                name="rsvDtEnd"
                                                data-ax-path="rsvDtEnd"
                                                data-ax5formatter="date"
                                                placeholder="yyyy-mm-dd"
                                            />
                                            <span class="input-group-addon"><i class="cqc-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="H10"></div>
            </div>
        </form>
        <ax:split-layout name="ax1" orientation="horizontal">
            <ax:split-panel width="*">
                <!-- 목록 -->
                <div class="ax-button-group" data-fit-height-aside="grid-view-01">
                    <div class="left">
                        <h2><i class="cqc-list"></i> 보고서</h2>
                    </div>
                </div>
                <div data-ax5grid="grid-view-01" data-fit-height-content="grid-view-01" style="height: 300px"></div>
            </ax:split-panel>
        </ax:split-layout>
    </jsp:body>
</ax:layout>
