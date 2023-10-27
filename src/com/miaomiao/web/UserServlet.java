package com.miaomiao.web;

import com.miaomiao.pojo.User;
import com.miaomiao.service.UserService;
import com.miaomiao.service.impl.UserServiceImpl;
import com.miaomiao.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;


public class UserServlet extends BasicServlet {

    private UserService userService = new UserServiceImpl();

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求参数
/*      String username = req.getParameter("username");
        String password = req.getParameter("password");*/

        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());

        //2.调用userService.login()登录
        User loginUser = userService.login(user);
        if (loginUser == null) {
            //登录失败，把错误信息和回显表单项信息保存到request域中
            req.setAttribute("msg", "用户名或密码错误！");
            req.setAttribute("username", user.getUsername());
            //跳回登录页面
            req.getRequestDispatcher("pages/user/login.jsp").forward(req, resp);
        } else {
            //登录成功
            //保存用户登录的信息到session域中
            req.getSession().setAttribute("user", loginUser);
            //跳转到登录成功页面
            req.getRequestDispatcher("pages/user/login_success.jsp").forward(req, resp);
        }
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.获取请求的参数
/*      String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");*/

        User user = (User) WebUtils.copyParamToBean(req.getParameterMap(), new User());

        //获取session中的谷歌验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除Session中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //2.检查验证码是否正确
        if (token != null && token.equalsIgnoreCase(req.getParameter("code"))) {
            //正确
            //3.检查用户名是否可用
            if (userService.existsUsername(user.getUsername())) {
                //把回显信息保存到request域中
                req.setAttribute("msg", "用户名已存在！");
                req.setAttribute("username", user.getUsername());
                req.setAttribute("email", user.getEmail());

                //跳回注册页面
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                //可用，调用service保存到数据库
                userService.registUser(user);
                //跳转到注册成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        } else {
            //把回显信息保存到request域中
            req.setAttribute("msg", "验证码错误");
            req.setAttribute("username", user.getUsername());
            req.setAttribute("email", user.getEmail());

            //不正确，跳回注册页面
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }

    /**
     * 注销
     * @param req
     * @param resp
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.销毁session中用户登录的信息（或者销毁session）
        req.getSession().invalidate();
        //2.重定向到首页（或登录页面）
        resp.sendRedirect(req.getContextPath());
    }

    /**
     *
     * @param req
     * @param resp
     * @throws UnsupportedEncodingException
     */
    protected void ajaxExistUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取请求的参数username
        String username= req.getParameter("username");
        //2.调用userService.existUsername();
        boolean existsUsername = userService.existsUsername(username);
        //把返回的结果封装成map对象
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername", existsUsername);
        //转成json对象
        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        //通过响应的字符输出流 输出
        resp.getWriter().write(json);
    }
}
