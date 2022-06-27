package com.qardio.demo.repository;

import com.qardio.demo.dto.TemperatureDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TemperatureRepository extends JpaRepository<TemperatureDto, String> {
}
