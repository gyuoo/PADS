package com.ssafy.s103.gspadsmock.domain.gsproduct.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTasklet;
import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTaskletProxy;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductRepository;
import com.ssafy.s103.gspadsmock.domain.gsproduct.service.RedisTimeService;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@Configuration
public class GsProductTaskConfig {
    private final AnomalyProductRepository anomalyProductRepository;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final RedisTimeService redisTimeService;

    public GsProductTaskConfig(AnomalyProductRepository anomalyProductRepository, RestClient.Builder restClient,
                               ObjectMapper objectMapper,
                               RedisTimeService redisTimeService) {
        this.anomalyProductRepository = anomalyProductRepository;
        this.restClient = restClient
                .baseUrl("http://localhost:8080/api")
                .build();
        this.objectMapper = objectMapper;
        this.redisTimeService = redisTimeService;
    }

    @Bean
    public CustomTasklet gsProductTask() {
        return new CustomTaskletProxy(
                new GSProductTask(restClient, objectMapper, anomalyProductRepository, redisTimeService));
    }
}
