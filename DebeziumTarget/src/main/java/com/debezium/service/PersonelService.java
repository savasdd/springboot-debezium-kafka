package com.debezium.service;

import com.debezium.service.impl.PersonelServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class PersonelService {

    private final PersonelServiceImpl personelService;
}
