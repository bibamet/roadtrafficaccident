package com.example.roadtrafficaccident.controller;

import com.example.roadtrafficaccident.dto.CreatingRtaDto;
import com.example.roadtrafficaccident.dto.getting.GettingRtaByArea;
import com.example.roadtrafficaccident.dto.getting.GettingRtaByCar;
import com.example.roadtrafficaccident.dto.updating.UpdatingAddressDto;
import com.example.roadtrafficaccident.dto.RtaDto;
import com.example.roadtrafficaccident.dto.updating.UpdatingGuiltyDto;
import com.example.roadtrafficaccident.dto.updating.UpdatingNumCarDto;
import com.example.roadtrafficaccident.dto.updating.UpdatingNumRtaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.roadtrafficaccident.service.RoadTrafficAccidentService;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/rta")
@RequiredArgsConstructor
public class RTAController {

    private final RoadTrafficAccidentService roadTrafficAccidentService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/add")
    public RtaDto addNewRTA(@Valid @RequestBody RtaDto rtaInfo) {

        return roadTrafficAccidentService.addNewRTA(rtaInfo);

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping ("/address")
    public RtaDto updateAddress(@Valid @RequestBody UpdatingAddressDto info) {
        //возможно, надо добавить параметры для изменения адреса и номера дтп
        return roadTrafficAccidentService.updateAddress(info.getNumberOfRta(),
                info.getNumberOfCar(),
                info.getAddress());

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/numcar")
    public RtaDto updateNumCar(@Valid @RequestBody UpdatingNumCarDto updatingNumCarDto) {

        return roadTrafficAccidentService.updateNumCar(updatingNumCarDto.getNumberOfRta(),
                updatingNumCarDto.getOldNumberOfCar(),
                updatingNumCarDto.getNewNumberOfCar());

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/numrta")
    public List<RtaDto> changeNumCar(@Valid @RequestBody UpdatingNumRtaDto updatingNumRtaDto) {

        return roadTrafficAccidentService.updateNumRta(updatingNumRtaDto.getNumberOfRta(),
                updatingNumRtaDto.getNewNumberOfRta());

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/guilty")
    public RtaDto setGuiltyAndPenalty(@Valid @RequestBody UpdatingGuiltyDto updatingGuiltyDto) {

        return roadTrafficAccidentService.setGuiltyAndPenalty(updatingGuiltyDto.getNumberOfRta(),
                updatingGuiltyDto.getNumberOfCar(),
                updatingGuiltyDto.isGuilty(),
                updatingGuiltyDto.getPenalty());


    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/car")
    public List<RtaDto> getRtaByCar(@Valid @RequestBody GettingRtaByCar gettingRtaByCar) {

        return roadTrafficAccidentService.getByCar(gettingRtaByCar.getNumberOfCar(),
                gettingRtaByCar.getFrom(),
                gettingRtaByCar.getTo());

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/area")
    public String getRtaFromArea(@Valid @RequestBody GettingRtaByArea gettingRtaByArea) {

        String message = "Среднее количество ДТП по %s с %td.%tm по %td.%tm за %s равно %.2f";

        var geocode = gettingRtaByArea.getGeocode();
        var from = gettingRtaByArea.getFrom();
        var to = gettingRtaByArea.getTo();
        var ageCount = gettingRtaByArea.getAgeCount();

        return String.format(message, geocode, from, from,
                to, to,
                ageCount == 0 ? "текущий год" : ageCount + " лет",
                roadTrafficAccidentService.getRtaFromArea(geocode, from, to, ageCount));
    }

}
