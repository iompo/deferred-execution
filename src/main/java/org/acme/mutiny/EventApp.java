package org.acme.mutiny;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.DeploymentOptions;
import io.vertx.mutiny.core.Vertx;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;

@ApplicationScoped
public class EventApp {

    void init(@Observes StartupEvent ev, Vertx vertx, Instance<StringGeneratorJob> verticles) {
        vertx.deployVerticle(verticles::get, new DeploymentOptions().setInstances(6).setWorker(true)).await().indefinitely();
    }
}
