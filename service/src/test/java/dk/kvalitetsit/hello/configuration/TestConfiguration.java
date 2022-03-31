package dk.kvalitetsit.hello.configuration;

import dk.kvalitetsit.hello.dao.TrivyDao;
import dk.kvalitetsit.hello.dao.TrivyDaoImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class TestConfiguration {
    // Configure beans used for test

    @Bean
    public TrivyDao helloDao(DataSource dataSource) {
        return new TrivyDaoImpl(dataSource);
    }
}
