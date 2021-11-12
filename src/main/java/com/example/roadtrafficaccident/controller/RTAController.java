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
import java.util.List;

@RestController()
@RequestMapping("/rta")
@RequiredArgsConstructor
public class RTAController {

    private final RoadTrafficAccidentService roadTrafficAccidentService;

    @PostMapping("/add")
    public ResponseEntity<RtaDto> addNewRTA(@RequestBody CreatingRtaDto rtaInfo) {

        return new ResponseEntity<>(roadTrafficAccidentService.addNewRTA(rtaInfo), HttpStatus.OK);

    }

    @PutMapping ("/address")
    public ResponseEntity<RtaDto> updateAddress(@RequestBody UpdatingAddressDto info) {
        //возможно, надо добавить параметры для изменения адреса и номера дтп
        RtaDto rtaDto = roadTrafficAccidentService.updateAddress(info.getNumberOfRta(),
                info.getNumberOfCar(),
                info.getAddress());

        return new ResponseEntity<>(rtaDto, HttpStatus.OK);

    }

    @PutMapping("/numcar")
    public ResponseEntity<RtaDto> updateNumCar(@RequestBody UpdatingNumCarDto updatingNumCarDto) {

        RtaDto rtaDto = roadTrafficAccidentService.updateNumCar(updatingNumCarDto.getNumberOfRta(),
                updatingNumCarDto.getOldNumberOfCar(),
                updatingNumCarDto.getNewNumberOfCar());

        return new ResponseEntity<>(rtaDto, HttpStatus.OK);

    }

    @PutMapping("/numrta")
    public ResponseEntity<List<RtaDto>> changeNumCar(@RequestBody UpdatingNumRtaDto updatingNumRtaDto) {

        List<RtaDto> rtaDtos = roadTrafficAccidentService.updateNumRta(updatingNumRtaDto.getNumberOfRta(),
                updatingNumRtaDto.getNewNumberOfRta());

        return new ResponseEntity<>(rtaDtos, HttpStatus.OK);

    }

    @PutMapping("/guilty")
    public ResponseEntity<RtaDto> setGuiltyAndPenalty(@RequestBody UpdatingGuiltyDto updatingGuiltyDto) {

        RtaDto rtaDto = roadTrafficAccidentService.setGuiltyAndPenalty(updatingGuiltyDto.getNumberOfRta(),
                updatingGuiltyDto.getNumberOfCar(),
                updatingGuiltyDto.isGuilty(),
                updatingGuiltyDto.getPenalty());

        return new ResponseEntity<>(rtaDto, HttpStatus.OK);

    }

    @GetMapping("/car")
    public ResponseEntity<List<RtaDto>> getRtaByCar(@RequestBody GettingRtaByCar gettingRtaByCar) {

        return new ResponseEntity<>(roadTrafficAccidentService.getByCar(gettingRtaByCar.getNumberOfCar(),
                gettingRtaByCar.getFrom(),
                gettingRtaByCar.getTo()), HttpStatus.OK);

    }

    @GetMapping("/area")
    public ResponseEntity<String> getRtaFromArea(@RequestBody GettingRtaByArea gettingRtaByArea) {

        String message = "Среднее количество ДТП по %s с %td.%tm по %td.%tm за %s равно %.2f";

        var geocode = gettingRtaByArea.getGeocode();
        var from = gettingRtaByArea.getFrom();
        var to = gettingRtaByArea.getTo();
        var ageCount = gettingRtaByArea.getAgeCount();

        return new ResponseEntity<>(String.format(message, geocode, from, from,
                to, to,
                ageCount == 0 ? "текущий год" : ageCount + " лет",
                roadTrafficAccidentService.getRtaFromArea(geocode, from, to, ageCount)), HttpStatus.OK);
    }

}
