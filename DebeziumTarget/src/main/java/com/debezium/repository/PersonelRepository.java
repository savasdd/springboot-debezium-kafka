package com.debezium.repository;

import com.debezium.model.Personel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonelRepository extends JpaRepository<Personel, UUID> {
}
