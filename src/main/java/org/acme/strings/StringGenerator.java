package org.acme.strings;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class StringGenerator {

    @Inject
    @RestClient
    WordsGeneratorClient wordsGeneratorClient;

    public Uni<String> generateCommaSeparatedListOfWords(int wordsCount) {
        return wordsGeneratorClient.generateWords(wordsCount).map(words -> String.join(",", words));
    }
}
