package id.my.hendisantika.vertx_rest_api_sample.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 * Time: 06.25
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookGetByIdResponse implements Serializable {

  @JsonProperty(value = "id")
  private int id;

  @JsonProperty(value = "title")
  private String title;


  public BookGetByIdResponse(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
  }
}
