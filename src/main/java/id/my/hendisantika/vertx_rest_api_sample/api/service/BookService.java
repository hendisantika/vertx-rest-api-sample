package id.my.hendisantika.vertx_rest_api_sample.api.service;

import id.my.hendisantika.vertx_rest_api_sample.api.model.BookGetAllResponse;
import id.my.hendisantika.vertx_rest_api_sample.api.model.BookGetByIdResponse;
import id.my.hendisantika.vertx_rest_api_sample.api.repository.BookRepository;
import id.my.hendisantika.vertx_rest_api_sample.util.LogUtils;
import id.my.hendisantika.vertx_rest_api_sample.util.QueryUtils;
import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.pgclient.PgPool;

import java.util.List;
import java.util.stream.Collectors;

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

  public Future<BookGetAllResponse> readAll(String p, String l) {
    return dbClient.withTransaction(
        connection -> {
          final int page = QueryUtils.getPage(p);
          final int limit = QueryUtils.getLimit(l);
          final int offset = QueryUtils.getOffset(page, limit);

          return bookRepository.count(connection)
            .flatMap(total ->
              bookRepository.selectAll(connection, limit, offset)
                .map(result -> {
                  final List<BookGetByIdResponse> books = result.stream()
                    .map(BookGetByIdResponse::new)
                    .collect(Collectors.toList());

                  return new BookGetAllResponse(total, limit, page, books);
                })
            );
        })
      .onSuccess(success -> LOGGER.info(LogUtils.REGULAR_CALL_SUCCESS_MESSAGE.buildMessage("Read all books", success.getBooks())))
      .onFailure(throwable -> LOGGER.error(LogUtils.REGULAR_CALL_ERROR_MESSAGE.buildMessage("Read all books", throwable.getMessage())));
  }

  public Future<BookGetByIdResponse> readOne(int id) {
    return dbClient.withTransaction(
        connection -> {
          return bookRepository.selectById(connection, id)
            .map(BookGetByIdResponse::new);
        })
      .onSuccess(success -> LOGGER.info(LogUtils.REGULAR_CALL_SUCCESS_MESSAGE.buildMessage("Read one book", success)))
      .onFailure(throwable -> LOGGER.error(LogUtils.REGULAR_CALL_ERROR_MESSAGE.buildMessage("Read one book", throwable.getMessage())));
  }
}
