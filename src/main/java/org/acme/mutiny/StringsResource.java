package org.acme.mutiny;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.acme.strings.StringsCount;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/mutiny")
public class StringsResource {

    @Inject
    EventBus eventBus;

    @GET
    @Path("random/{count}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> generateRandom(@PathParam("count") int count) {
        JsonObject counter = toEventMessage(count);
        return eventBus.<String>request("string-generator", counter)
                .onItem().transform(Message::body);
    }

    private static JsonObject toEventMessage(int count) {
        try {
            return new JsonObject(new ObjectMapper().writeValueAsString(new StringsCount(count)));
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
