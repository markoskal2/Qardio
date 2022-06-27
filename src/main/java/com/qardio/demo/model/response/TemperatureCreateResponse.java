package com.qardio.demo.model.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TemperatureCreateResponse {
  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();
}
