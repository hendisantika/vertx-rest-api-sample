package id.my.hendisantika.vertx_rest_api_sample;

import io.vertx.core.Vertx;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 10/01/25
 * Time: 17.15
 * To change this template use File | Settings | File Templates.
 */
public class Main {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(MainVerticle.class.getName())
      .onFailure(throwable -> System.exit(-1))
      .onSuccess(res -> System.out.println("Success"));
  }
}
