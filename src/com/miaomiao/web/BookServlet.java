package com.miaomiao.web;

import com.miaomiao.pojo.Book;
import com.miaomiao.pojo.Page;
import com.miaomiao.service.BookService;
import com.miaomiao.service.impl.BookServiceImpl;
import com.miaomiao.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookServlet extends BasicServlet{

    private BookService bookService = new BookServiceImpl();

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //1.获取请求的参数封装成为Book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());
        //2.调用BookService.addBook()保存图书
        bookService.addBook(book);
        //3.跳到图书列表页面，使用请求重定向，防止用户按F5，表单重复提交
        //请求重定向的地址中 / 表示到端口号 req.getContextPath()=/book,即为http://localhost:8088/book
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNow=" + req.getParameter("pageNow"));
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1.获取请求的参数id
        String id = req.getParameter("id");
        //2.调用bookService.deleteBookById()，删除图书
        bookService.deleteBookById(Integer.parseInt(id));
        //3.重定向图书列表管理页面
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNow=" + req.getParameter("pageNow"));
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取请求的参数封装成为JavaBean对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());
        //2.调用bookService.updateBooK方法修改图书信息
        bookService.updateBook(book);
        //3.重定向回图书列表管理页面，地址 /book/manager/bookServlet?action=list
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNow=" + req.getParameter("pageNow"));
    }

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
        page.setUrl("manager/bookServlet?action=page");
        //3.保存page对象到request域中
        req.setAttribute("page", page);
        //4.请求转发到pages/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) {

        //1.通过BookService查询全部图书
        List<Book> books = bookService.queryBooks();
        //2.把全部图书信息保存到Request域中
        req.setAttribute("books", books);
        //3.请求转发到/pages/manager/book_manager.jsp页面
        try {
            req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求参数的图书编号
        String id = req.getParameter("id");
        //2.调用bookService.queryBookById查询图书
        Book book = bookService.queryBookById(Integer.parseInt(id));
        //3.保存图书信息到request域中
        req.setAttribute("book", book);
        //4.请求转发到 pages/manager/book_edit.jsp页面
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req, resp);
    }
}