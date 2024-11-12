package com.ssafy.s103.gspadsmock.domain.anomalyproduct.batch;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AnomalyProductBatchConfig {
    public static final String PRODUCT_BATCH_NAME = "productBatch";
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final DataSource dataSource;

//    @Bean
//    public Step jdbcChunkStep() throws Exception {
//        return new StepBuilder("jdbcChunkStep", jobRepository)
//                .<ProductDto, ProductDto>chunk(100, platformTransactionManager)
//                .reader(customJdbcItemReader())
//                .processor(productProcessor())
//                .writer(productWriter())
//                .build();
//    }


//    @Bean
//    public JdbcPagingItemReader<ProductDto> customJdbcItemReader() throws Exception {
//        Map<String, Object> parameterValues = new HashMap<>();
//
//        return new JdbcPagingItemReaderBuilder<ProductDto>()
//                .name("jdbcPagingItemReader")
//                .pageSize(100)
//                .fetchSize(100)
//                .dataSource(dataSource)
//                .rowMapper(new BeanPropertyRowMapper<>(ProductDto.class))
//                .queryProvider(queryProvider())
//                .parameterValues(parameterValues)
//                .build();
//    }
//
//    @Bean
//    public PagingQueryProvider queryProvider() throws Exception {
//        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
//        queryProvider.setDataSource(dataSource);
//        queryProvider.setSelectClause("SELECT prd_Id, anomaly_flag"); // 필요한 컬럼 선택
//        queryProvider.setFromClause("FROM productsample");
//        queryProvider.setWhereClause("WHERE anomaly_flag = :anomalyFlag");
//        queryProvider.setSortKey("prd_id"); // 페이징을 위해 정렬할 키 설정
//
//        return queryProvider.getObject();
//    }
//
//    @Bean
//    public ItemProcessor<ProductDto, ProductDto> productProcessor() {
//        return item -> {
//            return item;
//        };
//    }
//
//    @Bean
//    public ItemWriter<ProductDto> productWriter() {
//        return chunk -> {
//            log.info("{} , {}", chunk.getItems().size(), chunk.getItems().getFirst().getPrdId());
//        };
//    }
}
