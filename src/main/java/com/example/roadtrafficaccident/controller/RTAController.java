package com.example.roadtrafficaccident.controller;

import com.example.roadtrafficaccident.dto.RtaDto;
import com.example.roadtrafficaccident.dto.getting.GettingRtaByArea;
import com.example.roadtrafficaccident.dto.getting.GettingRtaByCar;
import com.example.roadtrafficaccident.dto.updating.UpdatingAddressDto;
import com.example.roadtrafficaccident.dto.updating.UpdatingGuiltyDto;
import com.example.roadtrafficaccident.dto.updating.UpdatingNumCarDto;
import com.example.roadtrafficaccident.dto.updating.UpdatingNumRtaDto;
import com.example.roadtrafficaccident.service.RoadTrafficAccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/rta")
@RequiredArgsConstructor
public class RTAController {

    private final RoadTrafficAccidentService roadTrafficAccidentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RtaDto addNewRTA(@Valid @RequestBody RtaDto rtaInfo) {
        return roadTrafficAccidentService.addNewRTA(rtaInfo);
    }

    @PutMapping ("/address")
    public RtaDto updateAddress(@Valid @RequestBody UpdatingAddressDto info) {
        return roadTrafficAccidentService.updateAddress(info.getNumberOfRta(),
                info.getNumberOfCar(),
                info.getAddress());
    }

    @PutMapping("/numcar")
    public RtaDto updateNumCar(@Valid @RequestBody UpdatingNumCarDto updatingNumCarDto) {
        return roadTrafficAccidentService.updateNumCar(updatingNumCarDto.getNumberOfRta(),
                updatingNumCarDto.getOldNumberOfCar(),
                updatingNumCarDto.getNewNumberOfCar());
    }

    @PutMapping("/numrta")
    public List<RtaDto> changeNumCar(@Valid @RequestBody UpdatingNumRtaDto updatingNumRtaDto) {
        return roadTrafficAccidentService.updateNumRta(updatingNumRtaDto.getNumberOfRta(),
                updatingNumRtaDto.getNewNumberOfRta());
    }

    @PutMapping("/guilty")
    public RtaDto setGuiltyAndPenalty(@Valid @RequestBody UpdatingGuiltyDto updatingGuiltyDto) {
        return roadTrafficAccidentService.setGuiltyAndPenalty(updatingGuiltyDto.getNumberOfRta(),
                updatingGuiltyDto.getNumberOfCar(),
                updatingGuiltyDto.isGuilty(),
                updatingGuiltyDto.getPenalty());
    }

    @GetMapping("/car")
    public List<RtaDto> getRtaByCar(@Valid @RequestBody GettingRtaByCar gettingRtaByCar) {
        return roadTrafficAccidentService.getByCar(gettingRtaByCar.getNumberOfCar(),
                gettingRtaByCar.getFrom(),
                gettingRtaByCar.getTo());
    }

    @GetMapping("/area")
    public String getRtaFromArea(@Valid @RequestBody GettingRtaByArea gettingRtaByArea) {
        return roadTrafficAccidentService.getRtaFromArea(gettingRtaByArea.getGeocode()
                , gettingRtaByArea.getFrom()
                , gettingRtaByArea.getTo()
                , gettingRtaByArea.getAgeCount());
    }

}
