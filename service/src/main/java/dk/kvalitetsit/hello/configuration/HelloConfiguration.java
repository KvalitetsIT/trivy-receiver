package dk.kvalitetsit.hello.configuration;

import dk.kvalitetsit.hello.dao.TrivyDao;
import dk.kvalitetsit.hello.service.TrivyService;
import dk.kvalitetsit.hello.service.TrivyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfiguration{
    @Bean
    public TrivyService helloService(TrivyDao trivyDao) {
        return new TrivyServiceImpl(trivyDao);
    }
}
