package com.debezium.repository;

import com.debezium.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit,Long> {
}
