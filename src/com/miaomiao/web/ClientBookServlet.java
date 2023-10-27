package com.miaomiao.web;

import com.miaomiao.pojo.Book;
import com.miaomiao.pojo.Page;
import com.miaomiao.service.BookService;
import com.miaomiao.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientBookServlet extends BasicServlet{

    private BookService bookService = new BookServiceImpl();

    /**
     * 处理分页功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数 pageNow 和 pageSize
        int pageNow = req.getParameter("pageNow") == null ? 1 : Integer.parseInt(req.getParameter("pageNow"));
        int pageSize = req.getParameter("pageSize") == null ? Page.PAGE_SIZE : Integer.parseInt(req.getParameter("pageSize"));
        //2.调用bookService.page(pageNow, pageSize)，返回page对象
        Page<Book> page = bookService.page(pageNow, pageSize);
        page.setUrl("client/clientBookServlet?action=page");
        //3.保存page对象到request域中
        req.setAttribute("page", page);
        //4.请求转发到pages/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }

    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数 pageNow 和 pageSize
        int pageNow = req.getParameter("pageNow") == null ? 1 : Integer.parseInt(req.getParameter("pageNow"));
        int pageSize = req.getParameter("pageSize") == null ? Page.PAGE_SIZE : Integer.parseInt(req.getParameter("pageSize"));
        int min = req.getParameter("min") == null ? 0 : Integer.parseInt(req.getParameter("min"));
        int max = req.getParameter("max") == null ? Integer.MAX_VALUE : Integer.parseInt(req.getParameter("max"));
        //2.调用bookService.page(pageNow, pageSize)，返回page对象
        Page<Book> page = bookService.pageByPrice(pageNow, pageSize, min, max);
        StringBuilder sb = new StringBuilder("client/clientBookServlet?action=pageByPrice");
        //如果有最小价格的参数，追加到分页条的地址参数中
        if (req.getParameter("min") != null) {
            sb.append("&min=").append(req.getParameter("min"));
        }
        if (req.getParameter("max") != null) {
            sb.append("&max=").append(req.getParameter("max"));
        }
        page.setUrl(sb.toString());
        //3.保存page对象到request域中
        req.setAttribute("page", page);
        //4.请求转发到pages/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }
}
