package com.debezium.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class DebeziumConnectorConfig {

    @Value("${debezium.datasource.host}")
    private String sourceDbHost;

    @Value("${debezium.datasource.database}")
    private String sourceDbName;

    @Value("${debezium.datasource.port}")
    private String sourceDbPort;

    @Value("${debezium.datasource.username}")
    private String sourceDbUsername;

    @Value("${debezium.datasource.password}")
    private String sourceDbPassword;

    @Bean
    public io.debezium.config.Configuration customerConnector() throws IOException {
        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        File dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");
        return io.debezium.config.Configuration.create()
                .with("name", "source-postgres-connector")
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", sourceDbHost)
                .with("database.port", sourceDbPort)
                .with("plugin.name", "pgoutput")
                .with("database.user", sourceDbUsername)
                .with("database.password", sourceDbPassword)
                .with("database.dbname", sourceDbName)
                .with("database.include.list", sourceDbName)
                .with("include.schema.changes", "false")
                .with("database.allowPublicKeyRetrieval", "true")
                .with("database.server.id", "10181")
                .with("database.server.name", "source-postgres-db-server")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", dbHistoryTempFile.getAbsolutePath())
                .build();
    }
}
