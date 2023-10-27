<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>错误页面</title>
		<%--静态包含 base标签，css样式，jQuery文件--%>
		<%@ include file="/pages/common/head.jsp"%>
	</head>

	<body>
		<img src="static/img/404.jpg">
		<a href="index.jsp">返回首页</a>

		<%--静态包含 页脚信息--%>
		<%@ include file="/pages/common/footer.jsp"%>
	</body>
</html>