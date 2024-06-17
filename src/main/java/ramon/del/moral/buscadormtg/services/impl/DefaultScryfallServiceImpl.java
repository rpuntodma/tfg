package ramon.del.moral.buscadormtg.services.impl;

import org.springframework.stereotype.Service;
import ramon.del.moral.buscadormtg.services.ScryfallService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class DefaultScryfallServiceImpl implements ScryfallService {

    private static final String URL_BASE = "https://api.scryfall.com/cards/";
    private static final String SEARCH = "search?q=";

    @Override
    public String searchCards(String endpoint) throws InterruptedException, IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(URL_BASE + SEARCH + endpoint))
                                         .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Error fetching from Scryfall: " + response.statusCode());
        }

        return response.body();
    }
}