package com.miaomiao.dao;

import com.miaomiao.pojo.Book;

import java.util.List;

public interface BookDao {

    public int addBook(Book book);

    public int deleteBookById(Integer id);

    public int updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    public Integer queryPageTotalCount();

    public List<Book> queryPageItems(int begin, int pageSize);

    public Integer queryPageTotalCountByPrice(int min, int max);

    public List<Book> queryPageItemsByPrice(int begin, int pageSize, int min, int max);
}

