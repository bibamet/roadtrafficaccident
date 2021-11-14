package com.example.roadtrafficaccident.service;

import com.example.roadtrafficaccident.adapter.DriversAndCarsAdapter;
import com.example.roadtrafficaccident.data.AddressRepo;
import com.example.roadtrafficaccident.data.RTARepo;
import com.example.roadtrafficaccident.dto.CreatingRtaDto;
import com.example.roadtrafficaccident.dto.AddressDto;
import com.example.roadtrafficaccident.dto.DriverDTO;
import com.example.roadtrafficaccident.dto.RtaDto;
import com.example.roadtrafficaccident.entity.AddressEntity;
import com.example.roadtrafficaccident.entity.RTAEntity;
import com.example.roadtrafficaccident.exceptions.RTAEntityNotFoundException;
import com.example.roadtrafficaccident.exceptions.RTASNotFoundException;
import com.example.roadtrafficaccident.mapper.AddressMapper;
import com.example.roadtrafficaccident.mapper.RtaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class RoadTrafficAccidentService {

    private final RTARepo rtaRepo;
    private final AddressRepo addressRepo;
    private final DriversAndCarsAdapter driversAndCarsAdapter;
    private final RtaMapper rtaMapper;
    private final AddressMapper addressMapper;

    public RtaDto addNewRTA(CreatingRtaDto rtaInfo) {

        // проверить, если указанный адрес в бд, если нет, то дернуть яндекс по координатам и сформировать представление адреса

        AddressEntity address  = addressMapper.toAddressEntity(rtaInfo.getAddress());

        Example<AddressEntity> example = Example.of(address);

        Optional<AddressEntity> addressFind = addressRepo.findOne(example);

        if (addressFind.isEmpty()) {
            address.setAddressView(driversAndCarsAdapter.getAddress(address.getLongtitude(), address.getLatitude()));
            address = addressRepo.save(address);
        } else {
            address = addressFind.get();
        }

        // может не найти такого водителя, тогда мы пробрасываем соответствующее исключение
        DriverDTO driverDTO = driversAndCarsAdapter.getDriversIdByNumberOfCar(rtaInfo.getNumberOfCar());

        RTAEntity rtaEntity = rtaMapper.toRTAEntity(rtaInfo);

        rtaMapper.updateRTAEntity(driverDTO, rtaEntity);

        rtaEntity.setAddress(address);

        //возможно, вставить обработку исключения при записи в БД
        RTAEntity savedRTA = rtaRepo.save(rtaEntity);

        RtaDto rtaDto = rtaMapper.toRtaDto(savedRTA);

        return rtaDto;

    }

    public RtaDto updateAddress(Long numberOfRTA
            , String numberOfCar
            , AddressDto address) {

        RTAEntity rtaEntity = rtaRepo.getByNumberOfCarAndNumberOfRta(numberOfCar, numberOfRTA)
                .orElseThrow(() -> new RTAEntityNotFoundException(numberOfRTA, numberOfCar));

        Optional<AddressEntity> addressOptional = addressRepo.findByLongtitudeAndLatitude(address.getLongtitude(), address.getLatitude());

        if (addressOptional.isEmpty()) {

            AddressEntity addressEntity = addressMapper.toAddressEntity(address);
            addressEntity.setAddressView(driversAndCarsAdapter.getAddress(addressEntity.getLongtitude(), addressEntity.getLatitude()));
            rtaEntity.setAddress(addressEntity);

        } else {
            rtaEntity.setAddress(addressOptional.get());
        }

        // пробросить исключение при ошибке записи в БД
        RTAEntity savedRta = rtaRepo.save(rtaEntity);

        return rtaMapper.toRtaDto(savedRta);
    }

    public RtaDto updateNumCar(Long numberOfRTA,
            String oldCarNumber, String newCarNumber) {

        RTAEntity rtaEntity = rtaRepo.getByNumberOfCarAndNumberOfRta(oldCarNumber, numberOfRTA)
                .orElseThrow(() -> new RTAEntityNotFoundException(numberOfRTA, oldCarNumber));

        // обработать ситуацию, если не найден такой водитель
        DriverDTO driverDTO = driversAndCarsAdapter.getDriversIdByNumberOfCar(newCarNumber);
        rtaMapper.updateRTAEntity(driverDTO, rtaEntity);
        rtaEntity.setNumberOfCar(newCarNumber);

        // пробросить исключение при ошибке записи в БД
        RTAEntity savedRta = rtaRepo.save(rtaEntity);

        return rtaMapper.toRtaDto(savedRta);

    }

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

    public Double getRtaFromArea(String area, LocalDateTime from, LocalDateTime to, long ageCount) {

        Map<String, String> findedArea = driversAndCarsAdapter.getCoordsFromArea(area);

        List<AddressEntity> addressEntities = addressRepo.findByLongtitudeGreaterThanEqualAndLongtitudeLessThanEqualAndLatitudeGreaterThanEqualAndLatitudeLessThanEqual(
                Double.valueOf(findedArea.get("lowerLongtitude")),
                Double.valueOf(findedArea.get("upperLongtitude")),
                Double.valueOf(findedArea.get("lowerLatitude")),
                Double.valueOf(findedArea.get("upperLatitude"))
                );


        int sum = 0;

        for (int i = 0; i < ageCount; i++) {
            sum += rtaRepo.findByAddressInAndTimeOfAccidentBetween(addressEntities,
                    LocalDateTime.of(from.getYear() - i, from.getMonthValue(), from.getDayOfMonth(), 0, 0),
                    LocalDateTime.of(to.getYear() - i, to.getMonthValue(), to.getDayOfMonth(), 23, 59 ));
        }

        return (double) (ageCount == 0 ? sum : sum / ageCount);

    }

    public RtaDto setGuiltyAndPenalty(Long numrta, String numcar, boolean guilty, Double penalty) {

        RTAEntity rtaEntity = rtaRepo.getByNumberOfCarAndNumberOfRta(numcar, numrta)
                .orElseThrow(() -> new RTAEntityNotFoundException(numrta, numcar));

        rtaEntity.setGuilty(guilty);

        rtaEntity.setPenalty(guilty ? penalty : 0);

        RTAEntity savedRta = rtaRepo.save(rtaEntity);

        return rtaMapper.toRtaDto(savedRta);

    }

//    @PostConstruct
    public void testMethod() {
        Optional<AddressEntity> address = addressRepo.findById(1L);
    }

}
