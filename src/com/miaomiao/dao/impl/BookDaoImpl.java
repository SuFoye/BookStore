package com.miaomiao.dao.impl;

import com.miaomiao.dao.BookDao;
import com.miaomiao.pojo.Book;

import java.util.List;

public class BookDaoImpl extends BasicDao implements BookDao {

    @Override
    public int addBook(Book book) {

        String sql = "insert into t_book(`name`, `author`, `price`, `sales`, `stock`, `img_path`) " +
                "values(?, ?, ?, ?, ?, ?)";
        return update(sql, book.getName(), book.getAuthor(), book.getPrice(),
                book.getSales(), book.getStock(), book.getImgPath());
    }

    @Override
    public int deleteBookById(Integer id) {

        String sql = "delete from t_book where id = ?";
        return update(sql, id);
    }

    @Override
    public int updateBook(Book book) {

        String sql = "update t_book set `name` = ?, `author` = ?, `price` = ?, `sales` = ?, " +
                "`stock` = ?, `img_path` = ? where `id` = ?";
        return update(sql, book.getName(), book.getAuthor(), book.getPrice(),
                book.getSales(), book.getStock(), book.getImgPath(), book.getId());
    }

    @Override
    public Book queryBookById(Integer id) {

        String sql = "select `id`, `name`, `author`, `price`, `sales`, `stock`, `img_path` imgPath " +
                "from t_book where id = ?";
        return querySingle(sql, Book.class, id);
    }

    @Override
    public List<Book> queryBooks() {

        String sql = "select `id`, `name`, `author`, `price`, `sales`, `stock`, `img_path` imgPath " +
                "from t_book";
        return queryMulti(sql, Book.class);
    }

    @Override
    public Integer queryPageTotalCount() {
        String sql = "select count(*) from t_book";
        Number count = (Number) queryScalar(sql);
        return count.intValue();
    }

    @Override
    public List<Book> queryPageItems(int begin, int pageSize) {
        String sql = "select `id`, `name`, `author`, `price`, `sales`, `stock`, `img_path` imgPath " +
                "from t_book limit ?, ?";
        return queryMulti(sql, Book.class, begin, pageSize);
    }

    @Override
    public Integer queryPageTotalCountByPrice(int min, int max) {
        String sql = "select count(*) from t_book where price between ? and ?";
        Number count = (Number) queryScalar(sql, min, max);
        return count.intValue();
    }

    @Override
    public List<Book> queryPageItemsByPrice(int begin, int pageSize, int min, int max) {
        String sql = "select `id`, `name`, `author`, `price`, `sales`, `stock`, `img_path` imgPath " +
                "from t_book " +
                "where price between ? and ? " +
                "order by price " +
                "limit ?, ?";
        return queryMulti(sql, Book.class, min, max, begin, pageSize);
    }
}
