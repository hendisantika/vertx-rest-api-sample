package id.my.hendisantika.vertx_rest_api_sample.api.handler;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.21
 * To change this template use File | Settings | File Templates.
 */
public class BookHandler {
  private static final String ID_PARAMETER = "id";
  private static final String PAGE_PARAMETER = "page";
  private static final String LIMIT_PARAMETER = "limit";

  private final BookService bookService;

  public BookHandler(BookService bookService) {
    this.bookService = bookService;
  }
}
