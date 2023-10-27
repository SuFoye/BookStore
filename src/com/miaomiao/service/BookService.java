package com.miaomiao.service;

import com.miaomiao.pojo.Book;
import com.miaomiao.pojo.Page;

import java.util.List;

public interface BookService {

    public void addBook(Book book);

    public void deleteBookById(Integer id);

    public void updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    public Page<Book> page(int pageNow, int pageSize);

    public Page<Book> pageByPrice(int pageNow, int pageSize, int min, int max);
}
