package id.my.hendisantika.vertx_rest_api_sample.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.15
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
public class ApplicationUtils {
  private Integer serverPort;


  public static int numberOfAvailableCores() {
    // I divide this in half to save some resources while developing
    return Runtime.getRuntime().availableProcessors() / 2;
  }
}
