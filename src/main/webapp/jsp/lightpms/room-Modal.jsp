<%@ page import="com.chequer.axboot.core.utils.RequestUtils" %> <%@ page contentType="text/html; charset=UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="ax" tagdir="/WEB-INF/tags" %> <% RequestUtils requestUtils = RequestUtils.of(request);
request.setAttribute("roomTypCd", requestUtils.getString("roomTypCd")); request.setAttribute("modalView", requestUtils.getString("modalView"));%>
<ax:set key="title" value="${pageName}" />
<ax:set key="page_desc" value="${PAGE_REMARK}" />
<ax:set key="page_auto_height" value="true" />
<ax:set key="axbody_class" value="baseStyle" />

<ax:layout name="modal">
    <jsp:attribute name="script">
        <ax:script-lang key="ax.script" var="LANG" />
        <script>
            var modalParams = { roomTypCd: '${roomTypCd}', modalView: '${modalView}' };
        </script>
        <script type="text/javascript" src="<c:url value='/assets/js/view/lightpms/room-Modal.js' />"></script>
    </jsp:attribute>
    <jsp:body>
        <ax:split-layout name="ax1" orientation="horizontal">
            <ax:split-panel height="*">
                <div class="ax-button-group" data-fit-height-aside="grid-view-01">
                    <div class="left">
                        <h2><i class="cqc-list"></i> 객실 목록</h2>
                    </div>
                </div>
                <div data-ax5grid="grid-view-01" data-fit-height-content="grid-view-01" style="height: 100px; max-height: 250px"></div>
                <div style="text-align: center; margin-top: 10px">
                    <button type="button" class="btn btn-info" data-page-btn="select">선택</button>
                    <button type="button" class="btn btn-default" data-page-btn="close">닫기</button>
                </div>
            </ax:split-panel>
        </ax:split-layout>
    </jsp:body>
</ax:layout>
