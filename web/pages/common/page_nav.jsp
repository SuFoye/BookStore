<%--
  Created by IntelliJ IDEA.
  User: vivian
  Date: 2023/10/14
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 分页条的开始 --%>
<div id="page_nav">
    <a href="${requestScope.page.url}&pageNow=1">首页</a>
    <%--大于首页才显示”上一页“--%>
    <c:if test="${requestScope.page.pageNow > 1}">
        <a href="${requestScope.page.url}&pageNow=${requestScope.page.pageNow - 1}">上一页</a>
    </c:if>

    <%--分页条页码输出效果--%>
    <c:choose>
        <%-- 情况1：如果总页码小于等于5的情况，页码的范围是1-总页码 --%>
        <c:when test="${requestScope.page.pageTotal <= 5}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${requestScope.page.pageTotal}"/>
        </c:when>
        <%-- 情况2：总页码大于5的情况--%>
        <c:when test="${requestScope.page.pageTotal > 5}">
            <c:choose>
                <%-- 小情况1：当前页面为前面3个，页码范围是1-5 --%>
                <c:when test="${requestScope.page.pageNow <= 3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                </c:when>
                <%-- 小情况2：当前页码为最后3个，页码范围是：总页码减4-总页码 --%>
                <c:when test="${requestScope.page.pageNow > requestScope.page.pageTotal - 3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal - 4}"/>
                    <c:set var="end" value="${requestScope.page.pageTotal}"/>
                </c:when>
                <%-- 小情况3：其余页码，页码范围是：当前页码减2-当前页码加2 --%>
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNow - 2}"/>
                    <c:set var="end" value="${requestScope.page.pageNow + 2}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
    <%--上面代码的逻辑循环模板--%>
    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i == requestScope.page.pageNow}">
            【${i}】
        </c:if>
        <c:if test="${i != requestScope.page.pageNow}">
            <a href="${requestScope.page.url}&pageNow=${i}">${i}</a>
        </c:if>
    </c:forEach>

    <%--如果已经是最后一页则不显示”下一页“--%>
    <c:if test="${requestScope.page.pageNow < requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNow=${requestScope.page.pageNow + 1}">下一页</a>
    </c:if>
    <a href="${requestScope.page.url}&pageNow=${requestScope.page.pageTotal}">末页</a>

    共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录 到第<input value="${requestScope.page.pageNow}" name="pn" id="pn_input"/>页
    <input id="searchPageBtn" type="button" value="确定">
</div>

<script type="text/javascript">
    $(function () {
        /*给查询页码输入框绑定单击事件，跳到指定页码*/
        $("#searchPageBtn").click(function () {
            var pageNow = $("#pn_input").val();

            var pageTotal = ${requestScope.page.pageTotal};
            if (pageNow <= pageTotal  && pageNow >= 1) {
                //js语言中的location对象，其href属性可以获取浏览器的当前地址栏信息，该属性可读可写
                location.href = "${requestScope.page.url}&pageNow=" + pageNow;
            }
        });
    });
</script>
<%-- 分页条的结束 --%>