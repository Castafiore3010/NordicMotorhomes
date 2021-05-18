package com.example.nordicmotorhomes.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
// https://developer.here.com/blog/how-to-use-geocoding-in-java-with-the-here-geocoding-search-api
public class Geocoder {
    private static final String GEOCODING_RESOURCE = "https://geocode.search.hereapi.com/v1/geocode";
    private static final String API_KEY = "AIzaSyAWYC7pYphver783c0pLORPEY4b-N6aq2A";

    public String GeocodeSync(String query) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query,"UTF-8");
        String requestUri = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + encodedQuery;

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());

        return (String) geocodingResponse.body();
    }
    public double[] getLatitudeFromStreetAdress(String fullAddress) throws IOException, InterruptedException{
        // address example: 10 Burmeistersgade, København, 1429
        double[] coordinates = new double[2];
        ObjectMapper mapper = new ObjectMapper();
        Geocoder geocoder = new Geocoder();

        String response = geocoder.GeocodeSync(fullAddress);
        JsonNode responseJsonNode = mapper.readTree(response);
        JsonNode items = responseJsonNode.get("items");

        for (JsonNode item : items) {
            JsonNode position = item.get("position");
            coordinates[0] = position.get("lat").asDouble();
            coordinates[1] = position.get("lng").asDouble();
        }

        return coordinates;

    }
}
