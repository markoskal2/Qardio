package com.qardio.demo.service;

import com.qardio.demo.dto.TemperatureDto;
import com.qardio.demo.model.request.TemperatureRequest;
import com.qardio.demo.repository.TemperatureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TemperatureServiceTest {
    @Autowired
    TemperatureService temperatureService;
    @Autowired
    TemperatureRepository temperatureRepository;

    @Test
    public void get_minimum_degree() {
        List<TemperatureDto> temperatures= List.of(
                TemperatureDto.builder()
                        .id(1L)
                        .deviceId("abc")
                        .tempDegree(23.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .id(2L)
                        .deviceId("200L")
                        .tempDegree(24.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .id(3L)
                        .deviceId("100L")
                        .tempDegree(25.0)
                        .creationDate(LocalDateTime.now())
                        .build());

        assertEquals(23.0, temperatureService.getMinDegree(temperatures).doubleValue());
    }
    @Test
    public void get_max_degree() {
        List<TemperatureDto> temperatures=List.of(
                TemperatureDto.builder()
                        .deviceId("abc")
                        .tempDegree(23.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .deviceId("200L")
                        .tempDegree(27.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .deviceId("100L")
                        .tempDegree(45.0)
                        .creationDate(LocalDateTime.now())
                        .build());

        assertEquals(45.0,temperatureService.getMaxDegree(temperatures).doubleValue());
    }
    @Test
    public void get_average_degree() {
        List<TemperatureDto> temperatures = List.of(
                TemperatureDto.builder()
                        .deviceId("abc")
                        .tempDegree(26.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder().
                        deviceId("200L")
                        .tempDegree(24.0)
                        .creationDate(LocalDateTime.now())
                        .build(),
                TemperatureDto.builder()
                        .deviceId("100L")
                        .tempDegree(28.0)
                        .creationDate(LocalDateTime.now())
                        .build()
        );

        assertEquals(26.0, temperatureService.getAverageDegree(temperatures).doubleValue());

    }
    @Test
    public void get_temp_list_for_custom_date_for_record_daily() {
        temperatureRepository.deleteAll();
        assertEquals(0,temperatureRepository.count());
        var temperatures = List.of(
                TemperatureRequest.builder()
                        .deviceId("abc")
                        .tempDegree(23.0)
                        .date(LocalDateTime.now())
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("200L")
                        .tempDegree(27.0)
                        .date(LocalDateTime.now())
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("400L")
                        .tempDegree(24.0)
                        .date(LocalDateTime.now().minusHours(1))
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("400L")
                        .tempDegree(24.0)
                        .date(LocalDateTime.now().minusHours(2))
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("300L")
                        .tempDegree(24.0)
                        .date(LocalDateTime.now().minusHours(1))
                        .build());

        temperatureService.saveAll(temperatures);
        assertEquals(5, temperatureRepository.findAll().size());

        var temperatureList=temperatureService.selectDailyTempData();
        assertEquals(5, temperatureList.size());
    }
    @Test
    public void get_temp_list_for_custom_date_for_record_hourly() {
        temperatureRepository.deleteAll();
        assertEquals(0,temperatureRepository.count());
        var temperatures = List.of(
                TemperatureRequest.builder()
                        .deviceId("abc")
                        .tempDegree(23.0)
                        .date(LocalDateTime.now())
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("200L")
                        .tempDegree(27.0)
                        .date(LocalDateTime.now())
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("400L")
                        .tempDegree(24.0)
                        .date(LocalDateTime.now().minusHours(1))
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("400L")
                        .tempDegree(24.0)
                        .date(LocalDateTime.now().minusHours(2))
                        .build(),
                TemperatureRequest.builder()
                        .deviceId("300L")
                        .tempDegree(24.0)
                        .date(LocalDateTime.now().minusHours(1))
                        .build());

        temperatureService.saveAll(temperatures);
        assertEquals(5, temperatureRepository.findAll().size());

        var temperatureList = temperatureService.selectHourlyTempData();
        assertEquals(2, temperatureList.size());
    }
}