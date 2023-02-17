package org.acme.vertx;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.acme.strings.StringGenerator;
import org.acme.strings.StringsCount;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.time.Duration;

@Dependent
public class StringGeneratorVertxJob extends AbstractVerticle {

    @Inject
    StringGenerator stringGenerator;

    @ConsumeEvent(value = "vertx-string-generator", blocking = true)
    public void check(Message<JsonObject> message) {
        StringsCount stringsCount = message.body().mapTo(StringsCount.class);
        String randomStrings = stringGenerator.generateCommaSeparatedListOfWords(stringsCount.getCount()).await().atMost(Duration.ofSeconds(10));
        message.reply(randomStrings);
    }
}
