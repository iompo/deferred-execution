package org.acme.mutiny;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.acme.strings.StringGenerator;
import org.acme.strings.StringsCount;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.time.Duration;

@Dependent
public class StringGeneratorJob extends AbstractVerticle {

    @Inject
    StringGenerator stringGenerator;

    @Override
    public Uni<Void> asyncStart() {
        return vertx.eventBus().<JsonObject>consumer("string-generator")
                .handler(message -> {
                    StringsCount numberOfStrings = message.body().mapTo(StringsCount.class);
                    String randomStrings = generateRandomString(numberOfStrings.getCount());
                    message.reply(new JsonObject(randomStrings));
                })
                .completionHandler();
    }

    private String generateRandomString(int numberOfString) {
        String generatedString = stringGenerator.generateCommaSeparatedListOfWords(numberOfString).await().atMost(Duration.ofSeconds(10));
        return generatedString;
    }
}
