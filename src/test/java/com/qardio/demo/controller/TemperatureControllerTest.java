package com.qardio.demo.controller;

import com.qardio.demo.dto.TemperatureDto;
import com.qardio.demo.model.request.TemperatureRequest;
import com.qardio.demo.repository.TemperatureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TemperatureControllerTest {

    @Autowired
    TemperatureController temperatureController;
    @Autowired
    TemperatureRepository temperatureRepository;

    @Test
    public void save_record() {
        temperatureRepository.deleteAll();
        assertEquals(0,temperatureRepository.count());

        var request = new TemperatureRequest("abc",27.0, LocalDateTime.now());
        temperatureController.saveTemperatures(request);

        assertEquals(1, temperatureRepository.count());
    }

    @Test
    public void save_bulk_records() {
        temperatureRepository.deleteAll();
        assertEquals(0,temperatureRepository.count());
        var temperatures= List.of(
                TemperatureRequest.builder()
                        .deviceId("abc")
                        .tempDegree(21.0)
                        .date(LocalDateTime.now())
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("300L")
                        .tempDegree(24.0)
                        .date(LocalDateTime.now())
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("200L")
                        .tempDegree(25.0)
                        .date(LocalDateTime.now())
                        .build()
        );

        temperatureController.saveTemperatures(temperatures);
        assertEquals(3,temperatureRepository.count());
    }

    @Test
    public void daily_aggregate_response() {
        temperatureRepository.deleteAll();
        assertEquals(0,temperatureRepository.count());
        var temperatures = List.of(
                TemperatureDto.builder()
                        .deviceId("abc")
                        .tempDegree(21.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .deviceId("100L")
                        .tempDegree(29.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .deviceId("200L")
                        .tempDegree(22.0)
                        .creationDate(LocalDateTime.now().minusHours(1))
                        .build()
        );

        temperatureRepository.saveAll(temperatures);
        assertEquals(3, temperatureRepository.findAll().size());

        var temperatureResponse=temperatureController.aggregateDaily();
        assertEquals(3, temperatureResponse.getBody().getTempDataCount());
        assertEquals(24.0,temperatureResponse.getBody().getAverageTempDegree().doubleValue());
        assertEquals(21.0,temperatureResponse.getBody().getMinTempDegree().doubleValue());
        assertEquals(29.0,temperatureResponse.getBody().getMaxTempDegree().doubleValue());
    }

    @Test
    public void hourly_aggregate_response() {
        temperatureRepository.deleteAll();
        assertEquals(0,temperatureRepository.count());
        var temperatures= List.of(
                TemperatureDto.builder()
                        .deviceId("abc")
                        .tempDegree(34.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .deviceId("abc")
                        .tempDegree(30.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .deviceId("abc")
                        .tempDegree(20.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .deviceId("200L")
                        .tempDegree(24.0)
                        .creationDate(LocalDateTime.now().minusHours(1))
                        .build()
        );

        temperatureRepository.saveAll(temperatures);
        assertEquals(4, temperatureRepository.findAll().size());

        var temperatureResponse=temperatureController.aggregateHourly();
        assertEquals(3,temperatureResponse.getBody().getTempDataCount());
        assertEquals(28.0,temperatureResponse.getBody().getAverageTempDegree().doubleValue());
        assertEquals(34.0,temperatureResponse.getBody().getMaxTempDegree().doubleValue());
        assertEquals(20.0, temperatureResponse.getBody().getMinTempDegree().doubleValue());
    }
}