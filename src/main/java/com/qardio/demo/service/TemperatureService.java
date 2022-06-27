package com.qardio.demo.service;

import com.qardio.demo.constant.AggregationType;
import com.qardio.demo.dto.TemperatureDto;
import com.qardio.demo.exception.DataNotFoundException;
import com.qardio.demo.model.request.TemperatureRequest;
import com.qardio.demo.model.response.TemperatureResponse;
import com.qardio.demo.repository.TemperatureRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;

    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    public void save(TemperatureRequest request){
        var temperature = TemperatureDto.builder()
                .deviceId(request.getDeviceId())
                .tempDegree(request.getTempDegree())
                .creationDate(request.getDate())
                .build();

        temperatureRepository.save(temperature);
    }

    public void saveAll(List<TemperatureRequest> temperatureRequestList){
        List<TemperatureDto> temperatures =new ArrayList<>();
        for (var request : temperatureRequestList){
            temperatures.add(TemperatureDto.builder()
                    .deviceId(request.getDeviceId())
                    .tempDegree(request.getTempDegree())
                    .creationDate(request.getDate())
                    .build());
        }
        temperatureRepository.saveAll(temperatures);
    }

    public TemperatureResponse getAggregateResponse(AggregationType aggregationType){
        List<TemperatureDto> temperatureDtoList;

        if (aggregationType.equals(AggregationType.HOURLY)){
            temperatureDtoList = selectHourlyTempData();
        }
        else {
            temperatureDtoList = selectDailyTempData();
        }

        return TemperatureResponse.builder()
                .tempDataCount(temperatureDtoList.size())
                .averageTempDegree(getAverageDegree(temperatureDtoList))
                .minTempDegree(getMinDegree(temperatureDtoList))
                .maxTempDegree(getMaxDegree(temperatureDtoList))
                .build();
    }
    protected List<TemperatureDto> selectHourlyTempData(){
        LocalDateTime aggregationTime=LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        return getTempListForCustomDate(aggregationTime);
    }
    protected List<TemperatureDto> selectDailyTempData(){
        var aggregationTime= LocalDateTime.now()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return getTempListForCustomDate(aggregationTime);
    }

    protected Double getMinDegree(List<TemperatureDto> temperatureDtoList){
        var minDegree= OptionalDouble.of(temperatureDtoList.stream()
                .min(Comparator.comparing(TemperatureDto::getTempDegree))
                .get()
                .getTempDegree());
        return minDegree.isPresent() ? minDegree.getAsDouble() : null;
    }

    protected Double getMaxDegree(List<TemperatureDto> temperatureDtoList){
        var maxDegree= OptionalDouble.of(temperatureDtoList.stream()
                .max(Comparator.comparing(TemperatureDto::getTempDegree))
                .get()
                .getTempDegree());
        return maxDegree.isPresent() ? maxDegree.getAsDouble() : null;
    }

    protected Double getAverageDegree(List<TemperatureDto> temperatureDtoList){
        var averageDegree = temperatureDtoList
                .stream()
                .mapToDouble(TemperatureDto::getTempDegree)
                .average();

        return averageDegree.isPresent() ? averageDegree.getAsDouble() : null;
    }

    protected List<TemperatureDto> getTempListForCustomDate(LocalDateTime localDateTime){
        List<TemperatureDto> tempList=temperatureRepository.findAll();

        if(tempList.size() < 1) {
            throw new DataNotFoundException();
        }

        return tempList.stream()
                .filter(temp->temp.getCreationDate().isEqual(localDateTime) || temp.getCreationDate().isAfter(localDateTime))
                .collect(Collectors.toList());
    }

}
