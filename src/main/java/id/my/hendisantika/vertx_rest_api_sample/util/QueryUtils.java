package id.my.hendisantika.vertx_rest_api_sample.util;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.18
 * To change this template use File | Settings | File Templates.
 */
public class QueryUtils {
  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_LIMIT = 20;

  private QueryUtils() {

  }

  public static int getPage(String page) {
    return (page == null)
      ? DEFAULT_PAGE
      : Integer.parseInt(page);
  }


  public static int getLimit(String limit) {
    return (limit == null)
      ? DEFAULT_LIMIT
      : Integer.parseInt(limit);
  }


  public static int getOffset(int page,
                              int limit) {
    if ((page - 1) * limit >= 0) {
      return (page - 1) * limit;
    } else {
      throw new NumberFormatException(LogUtils.NULL_OFFSET_ERROR_MESSAGE.buildMessage(page, limit));
    }
  }
}
