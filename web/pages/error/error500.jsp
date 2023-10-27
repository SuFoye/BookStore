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
		很抱歉，您访问的后台程序出现了错误，程序猿小哥正在努力为您抢修！！！<br>
		<img src="static/img/500.jpg">
		<a href="index.jsp">返回首页</a>

		<%--静态包含 页脚信息--%>
		<%@ include file="/pages/common/footer.jsp"%>
	</body>
</html>