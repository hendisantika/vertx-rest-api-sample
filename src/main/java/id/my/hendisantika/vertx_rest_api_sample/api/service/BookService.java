package id.my.hendisantika.vertx_rest_api_sample.api.service;

import id.my.hendisantika.vertx_rest_api_sample.api.model.Book;
import id.my.hendisantika.vertx_rest_api_sample.api.model.BookGetAllResponse;
import id.my.hendisantika.vertx_rest_api_sample.api.model.BookGetByIdResponse;
import id.my.hendisantika.vertx_rest_api_sample.api.repository.BookRepository;
import id.my.hendisantika.vertx_rest_api_sample.util.LogUtils;
import id.my.hendisantika.vertx_rest_api_sample.util.QueryUtils;
import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;

import java.util.List;
import java.util.stream.Collectors;

import java.util.logging.Logger;

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
  private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());

  private final Pool dbClient;
  private final BookRepository bookRepository;

  public BookService(Pool dbClient,
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
      .onFailure(throwable -> LOGGER.severe(LogUtils.REGULAR_CALL_ERROR_MESSAGE.buildMessage("Read all books", throwable.getMessage())));
  }

  public Future<BookGetByIdResponse> readOne(int id) {
    return dbClient.withTransaction(
        connection -> {
          return bookRepository.selectById(connection, id)
            .map(BookGetByIdResponse::new);
        })
      .onSuccess(success -> LOGGER.info(LogUtils.REGULAR_CALL_SUCCESS_MESSAGE.buildMessage("Read one book", success)))
      .onFailure(throwable -> LOGGER.severe(LogUtils.REGULAR_CALL_ERROR_MESSAGE.buildMessage("Read one book", throwable.getMessage())));
  }

  public Future<BookGetByIdResponse> create(Book book) {
    return dbClient.withTransaction(
        connection -> {
          return bookRepository.insert(connection, book)
            .map(BookGetByIdResponse::new);
        })
      .onSuccess(success -> LOGGER.info(LogUtils.REGULAR_CALL_SUCCESS_MESSAGE.buildMessage("Create one book", success)))
      .onFailure(throwable -> LOGGER.severe(LogUtils.REGULAR_CALL_ERROR_MESSAGE.buildMessage("Create one book", throwable.getMessage())));
  }

  public Future<BookGetByIdResponse> update(int id, Book book) {
    book.setId(id);

    return dbClient.withTransaction(
        connection -> {
          return bookRepository.update(connection, book)
            .map(BookGetByIdResponse::new);
        })
      .onSuccess(success -> LOGGER.info(LogUtils.REGULAR_CALL_SUCCESS_MESSAGE.buildMessage("Update one book", success)))
      .onFailure(throwable -> LOGGER.severe(LogUtils.REGULAR_CALL_ERROR_MESSAGE.buildMessage("Update one book", throwable.getMessage())));
  }

  public Future<Void> delete(Integer id) {
    return dbClient.withTransaction(
        connection -> {
          return bookRepository.delete(connection, id);
        })
      .onSuccess(success -> LOGGER.info(LogUtils.REGULAR_CALL_SUCCESS_MESSAGE.buildMessage("Delete one book", id)))
      .onFailure(throwable -> LOGGER.severe(LogUtils.REGULAR_CALL_ERROR_MESSAGE.buildMessage("Delete one book", throwable.getMessage())));
  }
}
