package id.my.hendisantika.vertx_rest_api_sample.util;

import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.17
 * To change this template use File | Settings | File Templates.
 */
public class DbUtils {
  private static final String HOST_CONFIG = "datasource.host";
  private static final String PORT_CONFIG = "datasource.port";
  private static final String DATABASE_CONFIG = "datasource.database";
  private static final String USERNAME_CONFIG = "datasource.username";
  private static final String PASSWORD_CONFIG = "datasource.password";

  private DbUtils() {
  }

  public static PgPool buildDbClient(Vertx vertx) {
    final Properties properties = ConfigUtils.getInstance().getProperties();

    final PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(Integer.parseInt(properties.getProperty(PORT_CONFIG)))
      .setHost(properties.getProperty(HOST_CONFIG))
      .setDatabase(properties.getProperty(DATABASE_CONFIG))
      .setUser(properties.getProperty(USERNAME_CONFIG))
      .setPassword(properties.getProperty(PASSWORD_CONFIG));

    final PoolOptions poolOptions = new PoolOptions().setMaxSize(ApplicationUtils.numberOfAvailableCores());

    return PgPool.pool(vertx, connectOptions, poolOptions);
  }

}
