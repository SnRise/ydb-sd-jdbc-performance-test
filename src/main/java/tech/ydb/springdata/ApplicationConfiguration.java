package tech.ydb.springdata;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import tech.ydb.data.repository.config.AbstractYdbJdbcConfiguration;
import tech.ydb.data.repository.config.EnableYdbRepositories;

/**
 * @author Madiyar Nurgazin
 */
@Configuration
@EnableYdbRepositories
public class ApplicationConfiguration extends AbstractYdbJdbcConfiguration {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
