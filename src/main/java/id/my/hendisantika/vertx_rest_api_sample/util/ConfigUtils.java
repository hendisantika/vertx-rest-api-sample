package id.my.hendisantika.vertx_rest_api_sample.util;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-rest-api-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/01/25
 * Time: 06.16
 * To change this template use File | Settings | File Templates.
 */
@Getter
public class ConfigUtils {

  private static ConfigUtils instance;

  private final Properties properties;

  private ApplicationUtils applicationUtils;

  private ConfigUtils() {
    this.properties = readProperties();
  }

  public static ConfigUtils getInstance() {
    if (instance == null) {
      instance = new ConfigUtils();
    }

    return instance;
  }

  private Properties readProperties() {
    Properties properties = new Properties();

    try {
      try (InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
        try {
          properties.load(is);
          applicationUtils = new ApplicationUtils(Integer.parseInt(properties.getProperty("server.port")));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return properties;
  }
}
