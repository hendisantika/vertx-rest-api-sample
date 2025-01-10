package id.my.hendisantika.vertx_rest_api_sample.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.23
 * To change this template use File | Settings | File Templates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
  private Integer id;
  private String title;
}
