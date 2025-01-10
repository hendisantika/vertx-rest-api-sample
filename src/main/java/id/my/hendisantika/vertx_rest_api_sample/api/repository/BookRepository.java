package id.my.hendisantika.vertx_rest_api_sample.api.repository;


import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.26
 * To change this template use File | Settings | File Templates.
 */
public class BookRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(BookRepository.class);

  private static final String SQL_SELECT_ALL = "SELECT * FROM books LIMIT #{limit} OFFSET #{offset}";
  private static final String SQL_SELECT_BY_ID = "SELECT * FROM books WHERE id = #{id}";
  private static final String SQL_INSERT = "INSERT INTO books (title) " +
    "VALUES (#{title}) RETURNING id";
  private static final String SQL_UPDATE = "UPDATE books SET title = #{title} WHERE id = #{id}";
  private static final String SQL_DELETE = "DELETE FROM books WHERE id = #{id}";
  private static final String SQL_COUNT = "SELECT COUNT(*) AS total FROM books";

  public BookRepository() {
  }
}
