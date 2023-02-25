package com.debezium.listener;

import com.debezium.service.PersonelService;
import com.debezium.service.UnitService;
import com.debezium.utils.DebeziumUtil;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.*;
import static io.debezium.data.Envelope.Operation;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class DebeziumListener {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final UnitService unitService;
    private final PersonelService personelService;
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

    public DebeziumListener(Configuration configuration, UnitService unitService,PersonelService personelService) {
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(configuration.asProperties())
                .notifying(this::handleChangeEvent)
                .build();

        this.unitService = unitService;
        this.personelService = personelService;
    }

    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
     try {
         SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
         log.info("Key = '" + sourceRecord.key() + "' value = '" + sourceRecord.value() + "'");

         Struct sourceRecordChangeValue= (Struct) sourceRecord.value();

         if (sourceRecordChangeValue != null) {
             Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));
             var source=(Struct)sourceRecordChangeValue.get("source");
             var dbName=source.get("db").toString();
             var tableName=source.get("table").toString();

             if(operation != Operation.READ) {
                 String record = operation == Operation.DELETE ? BEFORE : AFTER; // Handling Update & Insert operations.

                 Struct struct = (Struct) sourceRecordChangeValue.get(record);
                 Map<String, Object> payload = struct.schema().fields().stream()
                         .map(Field::name)
                         .filter(fieldName -> struct.get(fieldName) != null)
                         .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                         .collect(toMap(Pair::getKey, Pair::getValue));

                 execute(tableName,payload,operation);
             }
         }
     }catch (Exception e){
         log.info("==>DEBEZIUM EXCEPTIONS!<==");
         e.printStackTrace();
     }
    }

    private void execute(String tableName,Map<String, Object> payload ,Operation operation){

        switch (tableName) {
            case DebeziumUtil.UNIT:{
                this.unitService.getUnitService().replicateData(payload, operation);
                log.info("Updated Unit Data: {} with Operation: {}", payload, operation.name());
                break;
            }
            case DebeziumUtil.PERSONEL:{
                this.personelService.getPersonelService().replicateData(payload, operation);
                log.info("Updated Personel Data: {} with Operation: {}", payload, operation.name());
                break;
            }
            default:
                log.info("Not Table: with Operation: {}",tableName, operation.name());
                break;
        }
    }



    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }
}
