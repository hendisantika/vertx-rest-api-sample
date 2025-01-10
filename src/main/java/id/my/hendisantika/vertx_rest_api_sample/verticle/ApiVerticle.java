package id.my.hendisantika.vertx_rest_api_sample.verticle;

import id.my.hendisantika.vertx_rest_api_sample.api.handler.BookHandler;
import id.my.hendisantika.vertx_rest_api_sample.api.handler.ErrorHandler;
import id.my.hendisantika.vertx_rest_api_sample.api.repository.BookRepository;
import id.my.hendisantika.vertx_rest_api_sample.api.router.BookRouter;
import id.my.hendisantika.vertx_rest_api_sample.api.service.BookService;
import id.my.hendisantika.vertx_rest_api_sample.util.ConfigUtils;
import id.my.hendisantika.vertx_rest_api_sample.util.DbUtils;
import id.my.hendisantika.vertx_rest_api_sample.util.LogUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.pgclient.PgPool;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.32
 * To change this template use File | Settings | File Templates.
 */
public class ApiVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiVerticle.class);

  @Override
  public void start(Promise<Void> promise) {
    final PgPool dbClient = DbUtils.buildDbClient(vertx);

    final BookRepository bookRepository = new BookRepository();
    final BookService bookService = new BookService(dbClient, bookRepository);
    final BookHandler bookHandler = new BookHandler(bookService);
    final BookRouter bookRouter = new BookRouter(vertx, bookHandler);

    final Router router = Router.router(vertx);
    ErrorHandler.buildHandler(router);
    bookRouter.setRouter(router);

    buildHttpServer(vertx, promise, router);
  }

  private void buildHttpServer(Vertx vertx,
                               Promise<Void> promise,
                               Router router) {
    int port = ConfigUtils.getInstance().getApplicationUtils().getServerPort();

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, http -> {
        if (http.succeeded()) {
          promise.complete();
          LOGGER.info(LogUtils.RUN_HTTP_SERVER_SUCCESS_MESSAGE.buildMessage(port));
        } else {
          promise.fail(http.cause());
          LOGGER.info(LogUtils.RUN_HTTP_SERVER_ERROR_MESSAGE.buildMessage());
        }
      });
  }
}
