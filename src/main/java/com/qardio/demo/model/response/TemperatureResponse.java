package com.qardio.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TemperatureResponse {
  private int tempDataCount;
  private Double averageTempDegree;
  private Double minTempDegree;
  private Double maxTempDegree;
}
