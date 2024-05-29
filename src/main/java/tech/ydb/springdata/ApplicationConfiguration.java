package tech.ydb.springdata;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import tech.ydb.data.repository.config.AbstractYdbJdbcConfiguration;

/**
 * @author Madiyar Nurgazin
 */
@Configuration
@EnableJdbcRepositories
public class ApplicationConfiguration extends AbstractYdbJdbcConfiguration {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
