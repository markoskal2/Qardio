package com.qardio.demo.controller;

import com.qardio.demo.constant.AggregationType;
import com.qardio.demo.model.request.TemperatureRequest;
import com.qardio.demo.model.response.TemperatureCreateResponse;
import com.qardio.demo.model.response.TemperatureResponse;
import com.qardio.demo.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {

    private final Logger logger = LoggerFactory.getLogger(TemperatureController.class);

    private final TemperatureService temperatureService;

    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @PostMapping("/save")
    public ResponseEntity<TemperatureCreateResponse> saveTemperatures(@RequestBody @Valid TemperatureRequest request) {
        logger.info("Service Called for Temperature Saving");
        temperatureService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TemperatureCreateResponse(LocalDateTime.now()));
    }

    @PostMapping("/bulk-save")
    public ResponseEntity<TemperatureCreateResponse> saveTemperatures(@RequestBody @Valid List<TemperatureRequest> request) {
        logger.info("Service Called for Bulk Temperature Saving");
        temperatureService.saveAll(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TemperatureCreateResponse(LocalDateTime.now()));
    }

    @GetMapping("/daily")
    public ResponseEntity<TemperatureResponse> aggregateDaily() {
        logger.info("Daily Aggregate Report");
        return ResponseEntity.ok().body(temperatureService.getAggregateResponse(AggregationType.DAILY));
    }

    @GetMapping("/hourly")
    public ResponseEntity<TemperatureResponse> aggregateHourly() {
        logger.info("Hourly Aggregate Report");
        return ResponseEntity.ok().body(temperatureService.getAggregateResponse(AggregationType.HOURLY));
    }

}
