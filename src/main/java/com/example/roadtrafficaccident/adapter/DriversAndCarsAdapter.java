package com.example.roadtrafficaccident.adapter;

import com.example.roadtrafficaccident.apidto.Response;
import com.example.roadtrafficaccident.dto.DriverDTO;
import com.example.roadtrafficaccident.exceptions.AddressNotFoundException;
import com.example.roadtrafficaccident.exceptions.AreaNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DriversAndCarsAdapter {

    private final RestTemplate restTemplate;

    @Value("${internal.api.key}")
    private String apiKey;

    public Map<String, String> getCoordsFromArea(String area) {

        String uri = UriComponentsBuilder.fromUriString("https://geocode-maps.yandex.ru/1.x/")
                .queryParam("apikey", apiKey)
                .queryParam("results", 1)
                .queryParam("format", "json")
                .queryParam("geocode", area).build().toUriString();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        String responseBody = responseEntity.getBody();

        if (responseBody == null || responseEntity.getBody().contains("\"found\":\"0\"")) {
            throw new AreaNotFoundException(area, true);
        }

        Map<String, String> properties = new HashMap<>();
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

    public DriverDTO getDriversIdByNumberOfCar(String numberOfCar) {

        String uri = UriComponentsBuilder.fromUriString("http://localhost:42100/driversandcars/driverid/").path("/{numberOfCar}")
                .buildAndExpand(numberOfCar).toUriString();

        ResponseEntity<DriverDTO> driverDTOResponseEntity = restTemplate.exchange(uri,
                HttpMethod.GET, null, new ParameterizedTypeReference<>(){});

        return driverDTOResponseEntity.getBody();

    }

    public String getAddress(Double longtitude, Double latitude) {

        String uri = UriComponentsBuilder.fromUriString("https://geocode-maps.yandex.ru/1.x/")
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


//    @PostConstruct
    public void testMethod() {

        String uri = UriComponentsBuilder.fromUriString("https://geocode-maps.yandex.ru/1.x/")
                .queryParam("apikey", apiKey)
                .queryParam("results", 1)
                .queryParam("format", "json")
                .queryParam("geocode", "Россия Рязань Советский район").build().toUriString();

        ResponseEntity<Response> responseEnt = restTemplate.getForEntity(uri, Response.class);

        Response response = responseEnt.getBody();


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
