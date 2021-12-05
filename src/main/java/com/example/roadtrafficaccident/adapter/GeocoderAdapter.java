package com.example.roadtrafficaccident.adapter;

import com.example.roadtrafficaccident.adapter.constants.GeocoderConstant;
import com.example.roadtrafficaccident.exceptions.parentexception.AddressNotFoundException;
import com.example.roadtrafficaccident.exceptions.parentexception.AreaNotFoundException;
import com.example.roadtrafficaccident.exceptions.WrongApiUrlException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.exception.JsonPathException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeocoderAdapter {

    @Value("${internal.api.key}")
    private String apiKey;

    @Value("${internal.api.geocoder-url}")
    private String geocoderUrl;

    public Map<String, String> getCoordsFromArea(String area) {
        try {
            return coordsFromArea(area);
        } catch (JsonPathException exception) {
            throw (WrongApiUrlException) new WrongApiUrlException(String.format("API недоступен или указан неверный URL: %s", geocoderUrl)).initCause(exception);
        }
    }

    public String getAddress(Double longtitude, Double latitude) {
        try {
            return addressFromJSONV2(longtitude, latitude);
        } catch (JsonPathException exception) {
            throw (WrongApiUrlException) new WrongApiUrlException(String.format("API недоступен или указан неверный URL: %s", geocoderUrl)).initCause(exception);
        }
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
            throw new AddressNotFoundException(longtitude, latitude);
        }
    }

    public Map<String, String> coordsFromArea(String area) {
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
            GeocoderConstant geocoderConstant = GeocoderConstant.builder()
                    .lowerCorner((envelope.get("lowerCorner")).split(" "))
                    .upperCorner((envelope.get("upperCorner")).split(" "))
                    .build();
            envelope.clear();
            envelope.put("lowerLongtitude", geocoderConstant.getLowerCorner()[0]);
            envelope.put("upperLongtitude", geocoderConstant.getUpperCorner()[0]);
            envelope.put("lowerLatitude", geocoderConstant.getLowerCorner()[1]);
            envelope.put("upperLatitude", geocoderConstant.getUpperCorner()[1]);
            return envelope;
        } else {
            throw new AreaNotFoundException(area);
        }
    }

}
