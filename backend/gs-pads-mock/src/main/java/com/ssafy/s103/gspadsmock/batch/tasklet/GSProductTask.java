package com.ssafy.s103.gspadsmock.batch.tasklet;

import com.ssafy.s103.gspadsmock.entity.ProductDto;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@Slf4j
public class GSProductTask implements CustomTasklet {
    private final JdbcTemplate jdbcTemplate;
    private final RestClient restClient;

    public GSProductTask(JdbcTemplate jdbcTemplate, RestClient restClient) {
        this.jdbcTemplate = jdbcTemplate;
        this.restClient = restClient;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Map<String, Object> fetchData = fetchProducts();


        return RepeatStatus.FINISHED;
    }

    public Map fetchProducts() {
        return restClient.get()
                .uri("/products")  // 외부 API 엔드포인트
                .retrieve()
                .body(Map.class);
    }
}
