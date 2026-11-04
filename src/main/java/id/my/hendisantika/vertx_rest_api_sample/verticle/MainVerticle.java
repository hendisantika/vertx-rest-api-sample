package id.my.hendisantika.vertx_rest_api_sample.verticle;

import id.my.hendisantika.vertx_rest_api_sample.util.ApplicationUtils;
import id.my.hendisantika.vertx_rest_api_sample.util.LogUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.ThreadingModel;
import io.vertx.core.Vertx;

import java.util.logging.Logger;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOGGER = Logger.getLogger(MainVerticle.class.getName());

  @Override
  public void start() {
    final long start = System.currentTimeMillis();

    deployMigrationVerticle(vertx)
      .flatMap(migrationVerticleId -> deployApiVerticle(vertx))
      .onSuccess(success -> LOGGER.info(LogUtils.RUN_APP_SUCCESSFULLY_MESSAGE.buildMessage(System.currentTimeMillis() - start)))
      .onFailure(throwable -> LOGGER.severe(throwable.getMessage()));
  }

  private Future<Void> deployMigrationVerticle(Vertx vertx) {
    final DeploymentOptions options = new DeploymentOptions()
      .setThreadingModel(ThreadingModel.WORKER)
      .setWorkerPoolName("migrations-worker-pool")
      .setInstances(1)
      .setWorkerPoolSize(1);

    return vertx.deployVerticle(MigrationVerticle.class.getName(), options)
      .flatMap(vertx::undeploy);
  }

  private Future<String> deployApiVerticle(Vertx vertx) {
    return vertx.deployVerticle(ApiVerticle.class.getName(),
      new DeploymentOptions().setInstances(Math.max(1, ApplicationUtils.numberOfAvailableCores())));
  }
}
