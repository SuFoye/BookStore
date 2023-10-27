package com.miaomiao.service.impl;

import com.miaomiao.dao.BookDao;
import com.miaomiao.dao.impl.BookDaoImpl;
import com.miaomiao.pojo.Book;
import com.miaomiao.pojo.Page;
import com.miaomiao.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNow, int pageSize) {
        Page<Book> page = new Page<Book>();

        //设置每页显示的数量
        page.setPageSize(pageSize);
        //求总记录数
        Integer pageTotalCount = bookDao.queryPageTotalCount();
        //设置总记录数
        page.setPageTotalCount(pageTotalCount);
        //求总页码
        int pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal += 1;
        }
        //设置总页码
        page.setPageTotal(pageTotal);

        /*数据边界的有效检查*/
        if (pageNow < 1) {
            pageNow = 1;
        }
        if (pageNow > pageTotal) {
            pageNow = pageTotal;
        }
        //设置当前页码
        page.setPageNow(pageNow);

        //求当前页数据的开始索引
        int begin = (page.getPageNow() - 1) * pageSize;
        //求当前页数据
        List<Book> items = bookDao.queryPageItems(begin, pageSize);
        //设置当前页数据
        page.setItems(items);

        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNow, int pageSize, int min, int max) {
        Page<Book> page = new Page<Book>();

        //设置每页显示的数量
        page.setPageSize(pageSize);
        //求总记录数
        Integer pageTotalCount = bookDao.queryPageTotalCountByPrice(min, max);
        //设置总记录数
        page.setPageTotalCount(pageTotalCount);
        //求总页码
        int pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal += 1;
        }
        //设置总页码
        page.setPageTotal(pageTotal);

        /*数据边界的有效检查*/
        if (pageNow < 1) {
            pageNow = 1;
        }
        if (pageNow > pageTotal) {
            pageNow = pageTotal;
        }
        //设置当前页码
        page.setPageNow(pageNow);

        //求当前页数据的开始索引
        int begin = (page.getPageNow() - 1) * pageSize;
        //求当前页数据
        List<Book> items = bookDao.queryPageItemsByPrice(begin, pageSize, min, max);
        //设置当前页数据
        page.setItems(items);

        return page;
    }
}
