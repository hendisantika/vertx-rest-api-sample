package id.my.hendisantika.vertx_rest_api_sample.api.router;

import id.my.hendisantika.vertx_rest_api_sample.api.handler.BookHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.29
 * To change this template use File | Settings | File Templates.
 */
public class BookRouter {
  private final Vertx vertx;
  private final BookHandler bookHandler;

  public BookRouter(Vertx vertx,
                    BookHandler bookHandler) {
    this.vertx = vertx;
    this.bookHandler = bookHandler;
  }

  public void setRouter(Router router) {
    router.mountSubRouter("/api/v1", buildBookRouter());
  }
}
