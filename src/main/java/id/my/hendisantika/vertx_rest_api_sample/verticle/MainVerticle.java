package id.my.hendisantika.vertx_rest_api_sample.verticle;

import id.my.hendisantika.vertx_rest_api_sample.util.LogUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start() {
    final long start = System.currentTimeMillis();

    deployMigrationVerticle(vertx)
      .flatMap(migrationVerticleId -> deployApiVerticle(vertx))
      .onSuccess(success -> LOGGER.info(LogUtils.RUN_APP_SUCCESSFULLY_MESSAGE.buildMessage(System.currentTimeMillis() - start)))
      .onFailure(throwable -> LOGGER.error(throwable.getMessage()));
  }

  private Future<Void> deployMigrationVerticle(Vertx vertx) {
    final DeploymentOptions options = new DeploymentOptions()
      .setWorker(true)
      .setWorkerPoolName("migrations-worker-pool")
      .setInstances(1)
      .setWorkerPoolSize(1);

    return vertx.deployVerticle(MigrationVerticle.class.getName(), options)
      .flatMap(vertx::undeploy);
  }
}
