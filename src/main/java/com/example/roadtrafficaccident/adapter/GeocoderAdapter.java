package com.example.roadtrafficaccident.adapter;

import com.example.roadtrafficaccident.exceptions.AddressNotFoundException;
import com.example.roadtrafficaccident.exceptions.AreaNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import io.restassured.RestAssured.*;
import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeocoderAdapter {

    private final RestTemplate restTemplate;

    @Value("${internal.api.key}")
    private String apiKey;

    @Value("${internal.api.geocoder-url}")
    private String geocoderUrl;

    public Map<String, String> getCoordsFromArea(String area) {

        Map<String, String> properties = coordsFromArea(area);

        if (Objects.nonNull(properties)) return properties;

        String uri = UriComponentsBuilder.fromUriString(geocoderUrl)
                .queryParam("apikey", apiKey)
                .queryParam("results", 1)
                .queryParam("format", "json")
                .queryParam("geocode", area).build().toUriString();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        String responseBody = responseEntity.getBody();

        if (responseBody == null || responseEntity.getBody().contains("\"found\":\"0\"")) {
            throw new AreaNotFoundException(area, true);
        }

        properties = new HashMap<>();
        String[] lowerCorner;
        String[] upperCorner;

        try {

            Map<?, ?> response = (Map<?, ?>) new JSONParser(responseBody).parseObject().get("response");
            Map<?, ?> GeoObjectCollection = (Map<?, ?>) response.get("GeoObjectCollection");
            Map<?, ?> GeoObject = (Map<?, ?>) ((Map<?, ?>) ((List<?>) GeoObjectCollection.get("featureMember"))
                    .get(0)).get("GeoObject");

            Map<?, ?> envelope = (Map<?, ?>) ((Map<?, ?>) GeoObject.get("boundedBy")).get("Envelope");

            lowerCorner = ((String) envelope.get("lowerCorner")).split(" ");
            upperCorner = ((String) envelope.get("upperCorner")).split(" ");

        } catch (ParseException e) {
            throw new AreaNotFoundException(area, false);
        }

        properties.put("lowerLongtitude", lowerCorner[0]);
        properties.put("upperLongtitude", upperCorner[0]);

        properties.put("lowerLatitude", lowerCorner[1]);
        properties.put("upperLatitude", upperCorner[1]);

        return properties;

    }

    public String getAddress(Double longtitude, Double latitude) {

        String addressV2 = addressFromJSONV2(longtitude, latitude);

        if (Objects.nonNull(addressV2)) {
            return addressV2;
        }

        String uri = UriComponentsBuilder.fromUriString(geocoderUrl)
                .queryParam("apikey", apiKey)
                .queryParam("results", 1)
                .queryParam("format", "json")
                .queryParam("geocode", longtitude + ", " + latitude)
                .build().toUriString();

        ResponseEntity<String> address = restTemplate.getForEntity(uri,
                String.class);

        if (address.getBody() == null || address.getBody().contains("\"found\":\"0\"")) {
            throw new AddressNotFoundException(longtitude, latitude, true);
        }

        StringBuffer responseBody = new StringBuffer(address.getBody());

        try {
            return addressFromJSON(responseBody);
        } catch (ParseException e) {
            throw new AddressNotFoundException(longtitude, latitude, false);
        }

    }

    public static String addressFromJSON(StringBuffer jsonaddress) throws ParseException {

        StringBuilder components = new StringBuilder();
        components.append("{")
                .append(jsonaddress.substring(jsonaddress.indexOf("\"Components\":"), jsonaddress.indexOf("\"AddressDetails\":") - 2))
                .append("}");
        JSONParser jsonParser = new JSONParser(components.toString());

        components.delete(0, components.length());

        Object jsonObject = jsonParser.parse();

        ArrayList<?> arrayList = (ArrayList<?>) ((LinkedHashMap<?, ?>) jsonObject).get("Components");

        arrayList.forEach(elementOfAddres -> {
            if (components.length() == 0) {
                components.append(((Map<?, ?>) elementOfAddres).get("name"));
            } else {
                components.append(", ").append(((Map<?, ?>) elementOfAddres).get("name"));
            }
        });

        return components.toString();
    }

    public String addressFromJSONV2(Double longtitude, Double latitude) {

        Optional<ArrayList<Map<String, String>>> componentsOptional = Optional.ofNullable(
                RestAssured.given()
                        .baseUri(geocoderUrl)
                        .formParam("apikey", apiKey)
                        .formParam("results", 1)
                        .formParam("format", "json")
                        .formParam("geocode", longtitude + "," + latitude)
                        .contentType(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .jsonPath()
                        .get("response.GeoObjectCollection.featureMember[0].GeoObject.metaDataProperty.GeocoderMetaData.Address.Components"));

        if (componentsOptional.isPresent()) {

            ArrayList<Map<String, String>> components = componentsOptional.get();

            return components.stream().map(component -> component.get("name")).collect(Collectors.joining(", "));

        } else {
            throw new AddressNotFoundException(longtitude, latitude, true);
        }

    }

    public Map<String, String> coordsFromArea(String area) {

        String[] lowerCorner;
        String[] upperCorner;

        Optional<Map<String, String>> envelopeOptional = Optional.ofNullable(
                RestAssured.given()
                        .baseUri(geocoderUrl)
                        .formParam("apikey", apiKey)
                        .formParam("results", 1)
                        .formParam("format", "json")
                        .formParam("geocode", area)
                        .contentType(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .jsonPath()
                        .get("response.GeoObjectCollection.featureMember[0].GeoObject.boundedBy.Envelope"));

        if (envelopeOptional.isPresent()) {

            Map<String, String> envelope = envelopeOptional.get();

            lowerCorner = ((String) envelope.get("lowerCorner")).split(" ");
            upperCorner = ((String) envelope.get("upperCorner")).split(" ");

            envelope.clear();

            envelope.put("lowerLongtitude", lowerCorner[0]);
            envelope.put("upperLongtitude", upperCorner[0]);

            envelope.put("lowerLatitude", lowerCorner[1]);
            envelope.put("upperLatitude", upperCorner[1]);

            return envelope;

        } else {
            throw new AreaNotFoundException(area, false);
        }

    }

//    @PostConstruct
    public void testMethod() {

        String uri = UriComponentsBuilder.fromUriString("https://geocode-maps.yandex.ru/1.x/")
                .queryParam("apikey", apiKey)
                .queryParam("results", 1)
                .queryParam("format", "json")
                .queryParam("geocode", "Россия Рязань Советский район").build().toUriString();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        String responseBody = responseEntity.getBody();

        Gson gson = new Gson();

        JsonElement jsonElement = JsonParser.parseString(responseBody);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonObject finalObject = jsonObject.get("response").getAsJsonObject().get("GeoObjectCollection").getAsJsonObject()
                .get("featureMember").getAsJsonArray().get(0).getAsJsonObject().get("GeoObject").getAsJsonObject();

        JsonObject boundedBy = finalObject.get("boundedBy").getAsJsonObject().get("Envelope").getAsJsonObject();

    }

}
