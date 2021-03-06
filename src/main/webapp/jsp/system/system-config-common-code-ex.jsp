<%@ page contentType="text/html; charset=UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="ax"
tagdir="/WEB-INF/tags" %>

<ax:set key="title" value="${pageName}" />
<ax:set key="page_desc" value="${PAGE_REMARK}" />
<ax:set key="page_auto_height" value="true" />

<ax:layout name="base">
    <jsp:attribute name="script">
        <script type="text/javascript" src="<c:url value='/assets/js/view/system/system-config-common-code-ex.js' />"></script>
    </jsp:attribute>
    <jsp:body>
        <ax:page-buttons></ax:page-buttons>

        <ax:split-layout name="ax1" orientation="vertical">
            <ax:split-panel width="300">
                <!-- 목록 -->
                <div class="ax-button-group" data-fit-height-aside="tree-view-01">
                    <div class="left">
                        <h2><i class="cqc-list"></i> 코드</h2>
                    </div>
                    <div class="right">
                        <button type="button" class="btn btn-default" data-tree-view-01-btn="add"><i class="cqc-circle-with-plus"></i> 추가</button>
                        <button type="button" class="btn btn-default" data-tree-view-01-btn="delete">
                            <i class="cqc-circle-with-plus"></i> 삭제
                        </button>
                    </div>
                </div>
                <div data-z-tree="tree-view-01" data-fit-height-content="tree-view-01" style="height: 300px"></div>
            </ax:split-panel>

            <ax:splitter></ax:splitter>
            <ax:split-panel width="*" style="padding-left: 10px" id="split-panel-form">
                <div data-fit-height-aside="form-view-01">
                    <div class="ax-button-group">
                        <div class="left">
                            <h2>
                                <i class="cqc-news">코드 설정</i>
                            </h2>
                        </div>
                        <div class="right"></div>
                    </div>

                    <ax:form name="formView01">
                        <ax:tbl clazz="ax-form-tbl" minWidth="500px">
                            <ax:tr labelWidth="150px">
                                <ax:td label="분류코드" width="50%">
                                    <input type="text" data-ax-path="progCd" class="form-control" value="" readonly="readonly" />
                                </ax:td>
                                <ax:td label="분류명" width="50%">
                                    <input type="text" data-ax-path="progCd" class="form-control" value="" />
                                </ax:td>
                            </ax:tr>
                            <ax:tr labelWidth="150px">
                                <ax:td label="사용여부" width="50%">
                                    <ax:common-code groupCd="USE_YN" dataPath="userYn" />
                                </ax:td>
                            </ax:tr>
                            <ax:tr labelWidth="150px">
                                <ax:td label="코드설명" width="80%">
                                    <textarea class="form-control"></textarea>
                                </ax:td>
                            </ax:tr>
                        </ax:tbl>
                    </ax:form>

                    <div class="H20"></div>
                    <!-- 목록 -->
                    <div class="ax-button-group">
                        <div class="left">
                            <h2><i class="cqc-list"></i> 코드 목록</h2>
                        </div>
                        <div class="right">
                            <button type="button" class="btn btn-default" data-grid-view-01-btn="add"><i class="fas fa-plus-circle"></i> 추가</button>
                            <button type="button" class="btn btn-default" data-grid-view-01-btn="delete">
                                <i class="fas fa-minus-circle"></i> 삭제
                            </button>
                            <button type="button" class="btn btn-default" data-grid-view-01-btn="save"><i class="fas fa-save"></i> 저장</button>
                        </div>
                    </div>
                </div>

                <div data-ax5grid="grid-view-01" data-fit-height-content="form-view-01" style="height: 300px"></div>
            </ax:split-panel>
        </ax:split-layout>
    </jsp:body>
</ax:layout>
