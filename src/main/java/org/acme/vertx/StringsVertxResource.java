package org.acme.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.UniHelper;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.acme.strings.StringsCount;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/vertx/")
public class StringsVertxResource {

    @Inject
    EventBus eventBus;

    @GET
    @Path("random/{count}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> generateRandom(@PathParam("count") int count) {
        JsonObject counter = toEventMessage(count);
        Future<Message<String>> request = eventBus.request("vertx-string-generator", counter);
        return UniHelper.toUni(request).map(Message::body);
    }

    private static JsonObject toEventMessage(int count) {
        try {
            return new JsonObject(new ObjectMapper().writeValueAsString(new StringsCount(count)));
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
