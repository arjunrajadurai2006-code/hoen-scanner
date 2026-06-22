package com.skyscanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyscanner.core.SearchResult;
import com.skyscanner.resources.SearchResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hoen-scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {
    }

    @Override
    public void run(final HoenScannerConfiguration configuration,
                    final Environment environment) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        List<SearchResult> searchResults = new ArrayList<>();

        // Load hotels
        InputStream hotelsStream =
                getClass().getClassLoader().getResourceAsStream("hotels.json");

        List<JsonNode> hotels = mapper.readValue(
                hotelsStream,
                new TypeReference<List<JsonNode>>() {}
        );

        for (JsonNode hotel : hotels) {
            searchResults.add(
                    new SearchResult(
                            hotel.get("city").asText(),
                            "hotel",
                            hotel.get("title").asText()
                    )
            );
        }

        // Load rental cars
        InputStream carsStream =
                getClass().getClassLoader().getResourceAsStream("rental_cars.json");

        List<JsonNode> cars = mapper.readValue(
                carsStream,
                new TypeReference<List<JsonNode>>() {}
        );

        for (JsonNode car : cars) {
            searchResults.add(
                    new SearchResult(
                            car.get("city").asText(),
                            "car",
                            car.get("title").asText()
                    )
            );
        }

        environment.jersey().register(
                new SearchResource(searchResults)
        );
    }
}