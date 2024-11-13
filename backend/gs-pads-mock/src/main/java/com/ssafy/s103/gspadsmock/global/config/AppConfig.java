package com.ssafy.s103.gspadsmock.global.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Configuration
public class AppConfig {
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}
