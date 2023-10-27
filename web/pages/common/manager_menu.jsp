<%--
  Created by IntelliJ IDEA.
  User: vivian
  Date: 2023/10/9
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <%--通过地址(manager)区分前后台，请求参数action=list 表示调用BookServlet中的list方法--%>
    <a href="manager/bookServlet?action=page">图书管理</a>
    <a href="xxx">订单管理</a>
    <a href="index.jsp">返回商城</a>
</div>