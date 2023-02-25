package com.debezium.repository;

import com.debezium.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<Parameter,Long> {
}
