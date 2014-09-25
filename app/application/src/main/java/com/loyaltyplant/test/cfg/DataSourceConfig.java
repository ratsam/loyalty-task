package com.loyaltyplant.test.cfg;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.AbstractDataSourceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Configuration
public class DataSourceConfig extends AbstractDataSourceConfiguration {

    @Bean
    public DataSource dataSource(@Value("${spring.datasource.hikari.dsClassName}") String dsClassName) {
        HikariConfig config = new com.zaxxer.hikari.HikariConfig();
        config.setMaximumPoolSize(getMinIdle());
        config.setMaximumPoolSize(getMaxActive());
        config.setDataSourceClassName(dsClassName);
        config.setDataSourceProperties(getDataSourceProperties());
        return new HikariDataSource(config);
    }

    private Properties getDataSourceProperties() {
        final Properties properties = new Properties();
        // H2 DataSource uses 'setURL' method name.
        properties.setProperty("url", getUrl());
        properties.setProperty("user", getUsername());
        if (getPassword() != null) {
            properties.setProperty("password", getPassword());
        }
        return properties;
    }
}
