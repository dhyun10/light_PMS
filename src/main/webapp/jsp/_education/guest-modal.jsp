<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ax" tagdir="/WEB-INF/tags" %>
<%@ page import="com.chequer.axboot.core.utils.RequestUtils" %>
<%
    RequestUtils requestUtils = RequestUtils.of(request);
    request.setAttribute("guestNm", requestUtils.getString("guestNm"));
    request.setAttribute("guestTel", requestUtils.getString("guestTel"));
    request.setAttribute("modalView", requestUtils.getString("modalView"));
%>

<ax:set key="title" value="${pageName}"/>
<ax:set key="page_desc" value="${PAGE_REMARK}"/>
<ax:set key="page_auto_height" value="true"/>

<ax:layout name="modal">
    <jsp:attribute name="script">
        <ax:script-lang key="ax.script" var="LANG" />
        <ax:script-lang key="ax.base" var="COL" />
        <script>
            var modalParams = {guestNm: "${guestNm}", guestTel: "${guestTel}", modalView: "${modalView}"};
        </script>
        <script type="text/javascript" src="<c:url value='/assets/js/view/base/guest-modal.js' />"></script>
    </jsp:attribute>
    <jsp:attribute name="header">
        <div class="ax-base-title" role="page-title">
            <h1 class="title"><i class="cqc-browser"></i> 투숙객 조회</h1>
        </div>
    </jsp:attribute>
    <jsp:body>

        <div data-page-buttons="">
            <div class="button-warp">
                <button type="button" class="btn btn-info" data-page-btn="search"> 조회</button>
                <button type="button" class="btn btn-info" data-page-btn="choice"> 선택</button>
                <button type="button" class="btn btn-default" data-page-btn="close"> 닫기</button>
            </div>
        </div>

        <div role="page-header">
            <form name="searchForm" method="post" class="js-search-form" onsubmit="return false;">
                <div data-ax-tbl class="ax-search-tbl">
                    <div data-ax-tr>
                        <div data-ax-td style="width:360px;">
                            <div data-ax-td-label>이름</div>
                            <div data-ax-td-wrap>
                                <input type="text" name="guestNm" class="form-control js-guestNm" />
                            </div>
                        </div>
                        <div data-ax-td style="width:360px;">
                            <div data-ax-td-label>전화번호</div>
                            <div data-ax-td-wrap>
                                <input type="text" name="guestTel" class="form-control js-guestTel" />
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div class="H10"></div>
        </div>

        <div class="container-fluid" style="padding: 0;">
            <div class="row">
                <div class="col-sm-12">
                    <!-- 목록 -->
                    <div class="ax-button-group">
                        <div class="left">
                            <h2><i class="cqc-list"></i> 투숙객 목록 </h2>
                        </div>
                        <div class="right">

                        </div>
                    </div>

                    <div data-ax5grid="grid-view-01" style="height: 200px;"></div>
                </div>
            </div>

            <div class="H10"></div>

            <div class="row">
                <div class="col-sm-12">
                    <form name="form" class="js-form" onsubmit="return false;">
                        <input type="hidden" name="id" data-ax-path="id" class="form-control" readonly />
                        <div data-ax-tbl class="ax-form-tbl">
                            <div data-ax-tr>
                                <div data-ax-td style="width:50%">
                                    <div data-ax-td-label style="width:120px;">이름</div>
                                    <div data-ax-td-wrap>
                                        <input type="text" name="guestNm" data-ax-path="guestNm" data-ax-validate="required" title="이름" class="form-control" />
                                    </div>
                                </div>
                                <div data-ax-td style="width:50%">
                                    <div data-ax-td-label style="width:120px;">영문</div>
                                    <div data-ax-td-wrap>
                                        <input type="text" name="guestNmEng" data-ax-path="guestNmEng" class="form-control" />
                                    </div>
                                </div>
                            </div>

                            <div data-ax-tr>
                                <div data-ax-td style="width:50%">
                                    <div data-ax-td-label style="width:120px;">연락처</div>
                                    <div data-ax-td-wrap>
                                        <input type="text" name="guestTel" data-ax-path="guestTel" class="form-control" />
                                    </div>
                                </div>
                                <div data-ax-td style="width:50%">
                                    <div data-ax-td-label style="width:120px;">이메일</div>
                                    <div data-ax-td-wrap>
                                        <input type="text" name="email" data-ax-path="email" class="form-control" placeholder="x@x.xx" />
                                    </div>
                                </div>
                            </div>

                            <div data-ax-tr>
                                <div data-ax-td style="width:50%">
                                    <div data-ax-td-label style="width:120px;">언어</div>
                                    <div data-ax-td-wrap>
                                        <ax:common-code groupCd="PMS_LANG" dataPath="langCd" emptyText=" " />
                                    </div>
                                </div>
                                <div data-ax-td style="width:50%">
                                    <div data-ax-td-label style="width:120px;">생년월일</div>
                                    <div data-ax-td-wrap>
                                        <div class="form-inline">
                                            <div class="form-group W120">
                                                <div class="input-group" data-ax5picker="brth">
                                                    <input type="text" name="brth" data-ax-path="brth" class="form-control" placeholder="yyyy-mm-dd" />
                                                    <span class="input-group-addon"><i class="cqc-calendar js-brth-open"></i></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="gender-M" class="radio-inline">
                                                    <input type="radio" data-ax-path="gender" name="gender" id="gender-M" value="M"/>남
                                                </label>
                                                <label for="gender-F" class="radio-inline">
                                                    <input type="radio" data-ax-path="gender" name="gender" id="gender-F" value="F"/>여
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div data-ax-tr>
                                <div data-ax-td style="width:100%">
                                    <div data-ax-td-label style="width:120px;">비고</div>
                                    <div data-ax-td-wrap>
                                        <textarea name="rmk" data-ax-path="rmk" rows="5" class="form-control"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </jsp:body>
</ax:layout>