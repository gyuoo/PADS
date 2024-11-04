package com.ssafy.s103.gspadsmock.batch;

import com.ssafy.s103.gspadsmock.batch.tasklet.GSProductTask;
import com.ssafy.s103.gspadsmock.batch.tasklet.JpaCustomTasklet;
import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTasklet;
import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTaskletProxy;
import com.ssafy.s103.gspadsmock.batch.tasklet.JdbcCustomTasklet;
import com.ssafy.s103.gspadsmock.schedule.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@Configuration
public class BatchTaskletConfig {

    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;
    private final RestClient restClient;

    public BatchTaskletConfig(JdbcTemplate jdbcTemplate, ProductRepository productRepository, RestClient.Builder restClient) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = productRepository;
        this.restClient = restClient
                .baseUrl("http://localhost:8080/api")
                .build();
    }

    @Bean
    public CustomTasklet gsProductTask() {
        return new CustomTaskletProxy(new GSProductTask(jdbcTemplate, restClient));
    }

//    @Bean
//    public CustomTasklet jdbcCustomTasklet() {
//        return new CustomTaskletProxy(new JdbcCustomTasklet(jdbcTemplate));
//    }
//
//    @Bean
//    public CustomTasklet jpaCustomTasklet() {
//        return new CustomTaskletProxy(new JpaCustomTasklet(productRepository));
//    }
}
