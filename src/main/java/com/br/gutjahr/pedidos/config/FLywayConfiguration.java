package com.br.gutjahr.pedidos.config;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class FLywayConfiguration implements FlywayMigrationStrategy {

    @Override
    public void migrate(Flyway flyway) {
        DataSource dataSource = flyway.getConfiguration().getDataSource();

        Flyway publico = Flyway.configure()
                .schemas("managment")
                .locations("db/migration/managment")
                .table("flyway_history")
                .baselineOnMigrate(true)
                .dataSource(dataSource).load();
        publico.migrate();

        for (Map<String, Object> schema : getSchemas(dataSource)) {
            Flyway cliente = Flyway.configure()
                    .schemas(schema.get("schema").toString())
                    .locations("db/migration/" + getMigrationName(schema))
                    .table("flyway_history")
                    .baselineOnMigrate(true)
                    .dataSource(dataSource).load();
            cliente.migrate();
        }
    }

    private String getMigrationName(Map<String, Object> schema) {
        return schema.get("is_restaurante") == "true" ? "restaurante" : "cliente";
    }

    public List<Map<String, Object>> getSchemas(DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select schema, is_restaurante from managment.usuario");
            ResultSet rs = statement.executeQuery();
            List<Map<String, Object>> list = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> schema = new HashMap<>();
                schema.put("schema", rs.getString(1));
                schema.put("is_restaurante", rs.getBoolean(2));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
