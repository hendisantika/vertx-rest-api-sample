package id.my.hendisantika.vertx_rest_api_sample.api.service;

import id.my.hendisantika.vertx_rest_api_sample.api.repository.BookRepository;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.pgclient.PgPool;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.30
 * To change this template use File | Settings | File Templates.
 */
public class BookService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

  private final PgPool dbClient;
  private final BookRepository bookRepository;

  public BookService(PgPool dbClient,
                     BookRepository bookRepository) {
    this.dbClient = dbClient;
    this.bookRepository = bookRepository;
  }
}
