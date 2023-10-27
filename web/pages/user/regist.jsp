<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>妙妙会员注册页面</title>
        <%--静态包含 base标签，css样式，jQuery文件--%>
        <%@ include file="/pages/common/head.jsp"%>
        <script type="text/javascript">
            //页面加载完成
            $(function () {

                $("#username").blur(function () {

                    //1.获取用户名
                    var username = this.value;
                    $.getJSON("${bathPath}userServlet", "action=ajaxExistUsername&username=" + username, function (data) {
                        // console.log(data);
                        if (data.existsUsername) {
                            $("span.errorMsg").text("用户名已存在！");
                        } else {
                            $("span.errorMsg").text("用户名可用！");
                        }
                    });
                });

                //给注册绑定单击事件
                $("#sub_btn").click(function () {

                    //验证用户名：必须由字母，数字，下划线组成，并且长度为5到12位
                    //1.获取用户输入框里的内容
                    var usernameText = $("#username").val();
                    //2.创建正则表达式对象
                    var usernamePatt = /^\w{5,12}$/;
                    //3.使用test方法验证
                    if (!usernamePatt.test(usernameText)) {
                        //4.提示用户结果
                        $("span.errorMsg").text("用户不合法!");

                        return false;
                    }

                    //验证密码：必须由字母，数字，下划线组成，并且长度为5到12位
                    //1.获取用户输入框里的内容
                    var passwordText = $("#password").val();
                    //2.创建正则表达式对象
                    var passwordPatt = /^\w{5,12}$/;
                    //3.使用test方法验证
                    if (!passwordPatt.test(passwordText)) {
                        //提示用户结果
                        $("span.errorMsg").text("密码不合法");

                        return false;
                    }

                    //验证确认密码，和密码相同
                    //1.获取确认密码内容
                    var repwdText = $("#repwd").val();
                    //2.和密码比较
                    if (repwdText != passwordText) {
                        //3.提示用户
                        $("span.errorMsg").text("确认密码不一致");

                        return false;
                    }

                    //邮箱验证：xxxxx@xxx.com
                    //1.获取邮箱里的内容
                    var emailText = $("#email").val();
                    //2.创建正则表达式对象
                    var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
                    //3.使用test方法验证
                    if (!emailPatt.test(emailText)) {
                        //4.提示用户
                        $("span.errorMsg").text("邮箱格式不合法");

                        return false;
                    }

                    //验证码
                    var codeText = $("#code").val();
                    //去掉验证码前后空格
                    var codeText = $.trim(codeText);
                    if (codeText == null || codeText == "") {
                        $("span.errorMsg").text("验证码不能为空");

                        return false;
                    }

                    $("span.errorMsg").text();
                });

                //给验证码的图片，绑定单击事件，点击验证码刷新
                $("#code_img").click(function () {
                    //在事件响应的function函数中有一个this对象，这个this对象，是当前正在响应事件的dom对象
                    //src属性表示验证码img标签的图片路径。它可读可写
                    //加个时间戳，解决浏览器缓存带来的验证码不刷新问题
                    this.src = "${basePath}kaptchaServlet.jpg?d" + new Date();
                });

                confirm("用户名格式：字母，数字，下划线组成，长度为5-12");

            });
        </script>

        <style type="text/css">
            .login_form {
                height: 420px;
                margin-top: 25px;
            }

        </style>
    </head>
    <body>

        <div id="login_header">
            <img class="logo_img" alt="" src="static/img/logo.gif">
        </div>

        <div class="login_banner">

            <div id="l_content">
                <span class="login_word">欢迎注册</span>
            </div>

            <div id="content">
                <div class="login_form">
                    <div class="login_box">
                        <div class="tit">
                            <h1>注册妙妙会员</h1>
                            <span class="errorMsg">
                                <%-- <%=request.getAttribute("msg") == null
                                        ? "" : request.getAttribute("msg")%> --%>
                                ${ requestScope.msg }
                            </span>
                        </div>
                        <div class="form">
                            <form action="userServlet" method="post">
                                <input type="hidden" name="action" value="regist">
                                <label>用户名称：</label>
                                <input class="itxt" type="text" placeholder="请输入用户名"
                                       autocomplete="off" tabindex="1" name="username"
                                       id="username" <%--value="<%=request.getAttribute("username") == null
                                            ? "" : request.getAttribute("username")%>"--%>
                                       value="${ requestScope.username }"/>
                                <br/>
                                <br/>
                                <label>用户密码：</label>
                                <input class="itxt" type="password" placeholder="请输入密码"
                                       autocomplete="off" tabindex="1" name="password" id="password"
                                       value="<%=request.getAttribute("password") == null
                                            ? "" : request.getAttribute("password")%>"/>
                                <br/>
                                <br/>
                                <label>确认密码：</label>
                                <input class="itxt" type="password" placeholder="确认密码"
                                       autocomplete="off" tabindex="1" name="repwd" id="repwd"/>
                                <br/>
                                <br/>
                                <label>电子邮件：</label>
                                <input class="itxt" type="text" placeholder="请输入邮箱地址"
                                       autocomplete="off" tabindex="1" name="email" id="email"
                                       <%-- value="<%=request.getAttribute("email") == null
                                            ? "" : request.getAttribute("email")%>" --%>
                                       value="${ requestScope.email }"/>
                                <br/>
                                <br/>
                                <label>验证码：</label>
                                <input class="itxt" type="text" name="code" style="width: 100px;" id="code"/>
                                <img id="code_img" alt="" src="kaptchaServlet.jpg"
                                     style="float: right; margin-right: 40px; width: 110px; height: 30px">
                                <br/>
                                <br/>
                                <input type="submit" value="注册" id="sub_btn"/>

                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <%--静态包含 页脚信息--%>
        <%@ include file="/pages/common/footer.jsp"%>

    </body>
</html>