package com.br.gutjahr.despesas.config;
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

        /*Flyway padrao = Flyway.configure()
                .schemas("padrao")
                .locations("db/migration/cliente")
                .table("flyway_history")
                .baselineOnMigrate(true)
                .dataSource(dataSource).load();
        padrao.migrate();*/

        for (String schema : getSchemas(dataSource)) {
            Flyway cliente = Flyway.configure()
                    .schemas(schema)
                    .locations("db/migration/cliente")
                    .table("flyway_history")
                    .baselineOnMigrate(true)
                    .dataSource(dataSource).load();
            cliente.migrate();
        }
    }

    public List<String> getSchemas(DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("select schema from managment.usuario");
            ResultSet rs = statement.executeQuery();
            List<String> list = new ArrayList<>();
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
