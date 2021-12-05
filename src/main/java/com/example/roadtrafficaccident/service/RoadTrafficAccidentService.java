package com.example.roadtrafficaccident.service;

import com.example.roadtrafficaccident.adapter.DriversAndCarsAdapter;
import com.example.roadtrafficaccident.adapter.GeocoderAdapter;
import com.example.roadtrafficaccident.data.AddressRepo;
import com.example.roadtrafficaccident.data.RTARepo;
import com.example.roadtrafficaccident.dto.AddressDto;
import com.example.roadtrafficaccident.dto.DriverDTO;
import com.example.roadtrafficaccident.dto.RtaDto;
import com.example.roadtrafficaccident.entity.AddressEntity;
import com.example.roadtrafficaccident.entity.RTAEntity;
import com.example.roadtrafficaccident.exceptions.parentexception.RTAEntityNotFoundException;
import com.example.roadtrafficaccident.exceptions.RTASNotFoundException;
import com.example.roadtrafficaccident.mapper.AddressMapper;
import com.example.roadtrafficaccident.mapper.RtaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoadTrafficAccidentService {

    private final RTARepo rtaRepo;
    private final AddressRepo addressRepo;
    private final DriversAndCarsAdapter driversAndCarsAdapter;
    private final GeocoderAdapter geocoderAdapter;
    private final RtaMapper rtaMapper;
    private final AddressMapper addressMapper;

    private AddressEntity getAddressEntity(Optional<AddressEntity> addressOptional, AddressEntity address) {
        if (addressOptional.isEmpty()) {
            address.setAddressView(geocoderAdapter.getAddress(address.getLongtitude(), address.getLatitude()));
        } else {
            address = addressOptional.get();
        }
        return address;
    }

    public RtaDto addNewRTA(RtaDto rtaInfo) {
        AddressEntity address  = addressMapper.toAddressEntity(rtaInfo.getAddress());
        Example<AddressEntity> example = Example.of(address);
        Optional<AddressEntity> addressFind = addressRepo.findOne(example);
        address = getAddressEntity(addressFind, address);
        DriverDTO driverDTO = driversAndCarsAdapter.getDriversIdByNumberOfCar(rtaInfo.getNumberOfCar());
        RTAEntity rtaEntity = rtaMapper.toRTAEntity(rtaInfo);
        rtaMapper.updateRTAEntity(driverDTO, rtaEntity);
        rtaEntity.setNumberOfCar(rtaInfo.getNumberOfCar());
        rtaEntity.setAddress(address);
        RTAEntity savedRTA = rtaRepo.save(rtaEntity);
        return rtaMapper.toRtaDto(savedRTA);
    }

    @Transactional
    public RtaDto updateAddress(Long numberOfRTA, String numberOfCar, AddressDto address) {
        RTAEntity rtaEntity = rtaRepo.getByNumberOfCarAndNumberOfRta(numberOfCar, numberOfRTA)
                .orElseThrow(() -> new RTAEntityNotFoundException(numberOfRTA, numberOfCar));
        AddressEntity addressEntityDto = addressMapper.toAddressEntity(address);
        Optional<AddressEntity> addressOptional = addressRepo.findByLongtitudeAndLatitude(address.getLongtitude(), address.getLatitude());
        addressEntityDto = getAddressEntity(addressOptional, addressEntityDto);
        rtaEntity.setAddress(addressEntityDto);
        RTAEntity savedRta = rtaRepo.save(rtaEntity);
        return rtaMapper.toRtaDto(savedRta);
    }

    @Transactional
    public RtaDto updateNumCar(Long numberOfRTA, String oldCarNumber, String newCarNumber) {
        RTAEntity rtaEntity = rtaRepo.getByNumberOfCarAndNumberOfRta(oldCarNumber, numberOfRTA)
                .orElseThrow(() -> new RTAEntityNotFoundException(numberOfRTA, oldCarNumber));
        DriverDTO driverDTO = driversAndCarsAdapter.getDriversIdByNumberOfCar(newCarNumber);
        rtaMapper.updateRTAEntity(driverDTO, rtaEntity);
        rtaEntity.setNumberOfCar(newCarNumber);
        RTAEntity savedRta = rtaRepo.save(rtaEntity);
        return rtaMapper.toRtaDto(savedRta);
    }

    @Transactional
    public List<RtaDto> updateNumRta(Long oldNumRTA, Long newNumRTA) {
        List<RTAEntity> rtaEntities = rtaRepo.findByNumberOfRta(oldNumRTA);
        if (rtaEntities.isEmpty()) {
            throw new RTASNotFoundException(oldNumRTA);
        }
        rtaEntities.forEach(rta -> rta.setNumberOfRta(newNumRTA));
        List<RTAEntity> rtaEntitiesSaved = rtaRepo.saveAll(rtaEntities);
        return rtaEntitiesSaved.stream().map(rtaMapper::toRtaDto).collect(Collectors.toList());
    }

    public List<RtaDto> getByCar(String numberOfCar, LocalDateTime from, LocalDateTime to) {
        List<RTAEntity> rtaEntities = rtaRepo.findByNumberOfCarAndTimeOfAccidentBetweenOrderById(numberOfCar, from, to);
        return rtaEntities.stream().map(rtaMapper::toRtaDto).collect(Collectors.toList());
    }

    public String getRtaFromArea(String area, LocalDateTime from, LocalDateTime to, long ageCount) {
        Map<String, String> findedArea = geocoderAdapter.getCoordsFromArea(area);
        List<AddressEntity> addressEntities = addressRepo.findAddresses(
                Double.valueOf(findedArea.get("lowerLongtitude")),
                Double.valueOf(findedArea.get("upperLongtitude")),
                Double.valueOf(findedArea.get("lowerLatitude")),
                Double.valueOf(findedArea.get("upperLatitude"))
                );
        int sum = 0;
        for (int i = 0; i < ageCount; i++) {
            sum += rtaRepo.findByAddressInAndTimeOfAccidentBetween(addressEntities,
                    LocalDateTime.of(from.getYear() - i, from.getMonthValue(), from.getDayOfMonth(), 0, 0),
                    LocalDateTime.of(to.getYear() - i, to.getMonthValue(), to.getDayOfMonth(), 23, 59));
        }
        return String.format("Среднее количество ДТП по %s с %td.%tm по %td.%tm за %s равно %.2f",
                area, from, from,
                to, to,
                ageCount == 0 ? "текущий год" : ageCount + " лет", (double) sum / (double) ageCount);
    }

    @Transactional
    public RtaDto setGuiltyAndPenalty(Long numrta, String numcar, boolean guilty, Double penalty) {
        RTAEntity rtaEntity = rtaRepo.getByNumberOfCarAndNumberOfRta(numcar, numrta)
                .orElseThrow(() -> new RTAEntityNotFoundException(numrta, numcar));
        rtaEntity.setGuilty(guilty);
        rtaEntity.setPenalty(guilty ? penalty : 0);
        RTAEntity savedRta = rtaRepo.save(rtaEntity);
        return rtaMapper.toRtaDto(savedRta);
    }

}
