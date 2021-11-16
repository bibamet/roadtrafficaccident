package com.example.roadtrafficaccident.adapter;

import com.example.roadtrafficaccident.dto.DriverDTO;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class DriversAndCarsAdapter {

    private final RestTemplate restTemplate;

    @Value("${internal.api.driversandcars-url}")
    private String driverAndCarsUrl;

    public DriverDTO getDriversIdByNumberOfCar(String numberOfCar) {

//        String uri = UriComponentsBuilder.fromUriString("http://localhost:42100/driversandcars/driverid/").path("/{numberOfCar}")
//                .buildAndExpand(numberOfCar).toUriString();

        String uri = UriComponentsBuilder.fromUriString(driverAndCarsUrl).path("/{numberOfCar}")
                .buildAndExpand(numberOfCar).toUriString();

        ResponseEntity<DriverDTO> driverDTOResponseEntity = restTemplate.exchange(uri,
                HttpMethod.GET, null, new ParameterizedTypeReference<>(){});

        return driverDTOResponseEntity.getBody();

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

}
