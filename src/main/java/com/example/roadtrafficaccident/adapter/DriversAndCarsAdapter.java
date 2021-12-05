package com.example.roadtrafficaccident.adapter;

import com.example.roadtrafficaccident.dto.DriverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class DriversAndCarsAdapter {

    private final RestTemplate restTemplate;

    @Value("${internal.api.driversandcars-url}")
    private String driverAndCarsUrl;

    public DriverDTO getDriversIdByNumberOfCar(String numberOfCar) {
        String uri = UriComponentsBuilder.fromUriString(driverAndCarsUrl).path("/{carNumber}")
                .buildAndExpand(numberOfCar).toUriString();
        try {
            ResponseEntity<DriverDTO> driverDTOResponseEntity = restTemplate.exchange(uri,
                    HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                    });
            return driverDTOResponseEntity.getBody();
        } catch (HttpClientErrorException.NotFound exception) {
            return null;
        }
    }

}
