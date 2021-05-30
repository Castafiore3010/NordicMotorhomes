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

// https://developer.here.com/blog/how-to-use-geocoding-in-java-with-the-here-geocoding-search-api
public class Geocoder {
    private static final String GEOCODING_RESOURCE = "https://geocode.search.hereapi.com/v1/geocode";
    private static final String API_KEY = "I9Vw4VKW8x0PGkQqh8FqSeQcos01iuSbV4ALe3icc3k"; //Update API

    public String geocodeSync(String query) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient(); // HttpClient object

        String encodedQuery = URLEncoder.encode(query,"UTF-8"); //Encryption
        String requestUri = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + encodedQuery; // complete URI request

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri)) // build request with URI
                .timeout(Duration.ofMillis(2000)).build(); //Hvis den ikke får svar, så smider den interrupt-exception

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString()); // httpClient sends HttpRequest which is mapped to HttpResponse geocodingResponse

        return (String) geocodingResponse.body(); // response body is returned as String
    }
    public double[] getLatLngFromStreetAdress(String fullAddress) throws IOException, InterruptedException{
        // address example: 10 Burmeistersgade, København, 1429
        double[] coordinates = new double[2]; // Array to hold coordinate values
        ObjectMapper mapper = new ObjectMapper(); //Object mapper, comparable to RowMapper
        Geocoder geocoder = new Geocoder();

        String response = geocoder.geocodeSync(fullAddress); // get response
        JsonNode responseJsonNode = mapper.readTree(response); // read response
        JsonNode items = responseJsonNode.get("items"); // get response items

        for (JsonNode item : items) {
            JsonNode position = item.get("position"); // get positional data
            coordinates[0] = position.get("lat").asDouble(); // latitude as double assigned to coordinates[0]
            coordinates[1] = position.get("lng").asDouble(); // longitude as double assigned to coordinates[1]
        }

        return coordinates;

    }

    public static void main(String[] args) throws IOException, InterruptedException{
        Geocoder geocoder = new Geocoder();

        double[] coordinates = geocoder.getLatLngFromStreetAdress("C.T Barfoedsvej 4, Frederiksberg, 2000");

        System.out.println("Latitude = " + coordinates[0] + " Longitude = " + coordinates[1]);
    }
}

