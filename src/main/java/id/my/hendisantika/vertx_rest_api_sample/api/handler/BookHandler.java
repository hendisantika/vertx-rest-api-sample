package id.my.hendisantika.vertx_rest_api_sample.api.handler;

import id.my.hendisantika.vertx_rest_api_sample.util.ResponseUtils;
import io.vertx.ext.web.RoutingContext;

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

  public Future<BookGetAllResponse> readAll(RoutingContext rc) {
    final String page = rc.queryParams().get(PAGE_PARAMETER);
    final String limit = rc.queryParams().get(LIMIT_PARAMETER);

    return bookService.readAll(page, limit)
      .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

  public Future<BookGetByIdResponse> readOne(RoutingContext rc) {
    final String id = rc.pathParam(ID_PARAMETER);

    return bookService.readOne(Integer.parseInt(id))
      .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

}
