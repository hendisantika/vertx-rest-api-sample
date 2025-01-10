package id.my.hendisantika.vertx_rest_api_sample.verticle;

import id.my.hendisantika.vertx_rest_api_sample.util.DbUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.35
 * To change this template use File | Settings | File Templates.
 */
public class MigrationVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> promise) {
    final Configuration config = DbUtils.buildMigrationsConfiguration();
    final Flyway flyway = new Flyway(config);

    flyway.migrate();

    promise.complete();
  }
}
