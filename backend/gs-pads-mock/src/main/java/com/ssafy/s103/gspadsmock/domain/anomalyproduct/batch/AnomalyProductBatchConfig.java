package com.ssafy.s103.gspadsmock.domain.anomalyproduct.batch;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProductBatch;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyStatus;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductBatchRepository;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductRepository;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AnomalyProductBatchConfig {
    public static final String PRODUCT_BATCH_NAME = "anomaly-product-batch";
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;
    private final AnomalyProductBatchRepository productBatchRepository;
    private final AnomalyProductRepository anomalyProductRepository;

    @Bean
    public Job anomalyProductJob() throws Exception {
        return new JobBuilder(PRODUCT_BATCH_NAME, jobRepository)
                .start(anomalyProductSchduleStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step anomalyProductSchduleStep() throws Exception {
        return new StepBuilder("schedule-anomaly-product-step", jobRepository)
                .<AnomalyProduct, AnomalyProduct>chunk(100, transactionManager)
                .reader(productReadSchedule())
                .processor(productProcessor())
                .writer(productWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<AnomalyProduct> productReadSchedule() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("status", AnomalyStatus.SCHEDULED.name());
        return new JdbcPagingItemReaderBuilder<AnomalyProduct>()
                .name("jdbcPagingItemReader")
                .pageSize(100)
                .fetchSize(100)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(AnomalyProduct.class))
                .queryProvider(queryProvider())
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    public PagingQueryProvider queryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("SELECT prd_id, create_dt, status");
        queryProvider.setFromClause("FROM anomaly_product ");
        queryProvider.setWhereClause("WHERE status = :status");
        queryProvider.setSortKey("create_dt"); // 페이징을 위해 정렬할 키 설정
        return queryProvider.getObject();
    }

    @Bean
    public ItemProcessor<AnomalyProduct, AnomalyProduct> productProcessor() {
        return new AnomalyProductProcessor(productBatchRepository, anomalyProductRepository);
    }

    @Bean
    public ItemWriter<AnomalyProduct> productWriter() {
        return chunk -> {
        };
    }
}
