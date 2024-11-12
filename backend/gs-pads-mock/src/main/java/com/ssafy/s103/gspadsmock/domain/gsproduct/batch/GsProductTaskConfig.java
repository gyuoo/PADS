package com.ssafy.s103.gspadsmock.domain.gsproduct.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTasklet;
import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTaskletProxy;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@Configuration
public class GsProductTaskConfig {
    private final AnomalyProductRepository anomalyProductRepository;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public GsProductTaskConfig(AnomalyProductRepository anomalyProductRepository, RestClient.Builder restClient, ObjectMapper objectMapper) {
        this.anomalyProductRepository = anomalyProductRepository;
        this.restClient = restClient
                .baseUrl("http://localhost:8080/api")
                .build();
        this.objectMapper = objectMapper;
    }

    @Bean
    public CustomTasklet gsProductTask() {
        return new CustomTaskletProxy(new GSProductTask(restClient, objectMapper, anomalyProductRepository));
    }
}
