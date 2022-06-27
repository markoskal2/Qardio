package com.qardio.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TemperatureRequest {
    @NotBlank
    private String deviceId;
    @NotNull
    private Double tempDegree;
    @NotNull
    private LocalDateTime date;
}
