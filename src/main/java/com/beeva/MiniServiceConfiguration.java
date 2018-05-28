package com.beeva;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MiniServiceConfiguration extends Configuration {

    private DatabaseConfig databaseConfig;

    @JsonProperty("database")
    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    static class DatabaseConfig {

        private String name;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}


