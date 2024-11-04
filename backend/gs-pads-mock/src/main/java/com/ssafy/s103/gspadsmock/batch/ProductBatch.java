package com.ssafy.s103.gspadsmock.batch;

import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTasklet;
import com.ssafy.s103.gspadsmock.entity.ProductDto;
import jakarta.persistence.EntityManagerFactory;
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
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductBatch {
    public static final String PRODUCT_BATCH_NAME = "productBatch";
    public static final String GS_PRODUCT_BATCH = "fetchGsProduct";
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final CustomTasklet gsProductTask;
    private final DataSource dataSource;

    @Bean
    public Job gsProductJob() {
        return new JobBuilder(GS_PRODUCT_BATCH, jobRepository)
                .start(gsProductStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Job newProductJob() throws Exception {
        return new JobBuilder(PRODUCT_BATCH_NAME, jobRepository)
                .start(jdbcChunkStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step gsProductStep() {
        return new StepBuilder(GS_PRODUCT_BATCH + "Step1", jobRepository)
                .tasklet(gsProductTask, platformTransactionManager)
                .build();
    }

    @Bean
    public Step jdbcChunkStep() throws Exception {
        return new StepBuilder("jdbcChunkStep", jobRepository)
                .<ProductDto, ProductDto>chunk(100, platformTransactionManager)
                .reader(customJdbcItemReader())
                .processor(productProcessor())
                .writer(productWriter())
                .build();
    }

//    @Bean
//    public JpaPagingItemReader<Product> jpaPagingItemReader() {
//        return new JpaPagingItemReaderBuilder<Product>()
//                .name("jpaPagingItemReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT p FROM Product p WHERE p.anomalyFlag = false")
//                .pageSize(50)
//                .build();
//    }

    @Bean
    public JdbcPagingItemReader<ProductDto> customJdbcItemReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("anomalyFlag", false);
        return new JdbcPagingItemReaderBuilder<ProductDto>()
                .name("jdbcPagingItemReader")
                .pageSize(100)
                .fetchSize(100)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(ProductDto.class))
                .queryProvider(queryProvider())
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    public PagingQueryProvider queryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("SELECT prd_Id, anomaly_flag"); // 필요한 컬럼 선택
        queryProvider.setFromClause("FROM productsample");
        queryProvider.setWhereClause("WHERE anomaly_flag = :anomalyFlag");
        queryProvider.setSortKey("prd_id"); // 페이징을 위해 정렬할 키 설정

        return queryProvider.getObject();
    }

    @Bean
    public ItemProcessor<ProductDto, ProductDto> productProcessor() {
        return item -> {
            // Processing logic here (e.g., setting a flag)
            return item;
        };
    }

    @Bean
    public ItemWriter<ProductDto> productWriter() {
        return chunk -> {
            log.info("{} , {}", chunk.getItems().size(), chunk.getItems().getFirst().getPrdId());
        };
    }



//    @Bean
//    public Step jpaPagingStep() {
//        return new StepBuilder("jpaPagingStep", jobRepository)
//                .<Product, Product>chunk(100, platformTransactionManager)
//                .reader(jpaPagingItemReader())
//                .processor(productProcessor())
//                .writer(productWriter())
//                .build();
//    }

//    @Bean
//    public Step productFirstStep() {
//        //청크는 대량의 데이터를 사이즈만큼
//        //100개의 데이터를 가져와야 할 때 10개씩 가져온다는 의미
//        //사이즈 조절을 통해 I/O 최적화 가능
//        return new StepBuilder("newProductJobFirstStep", jobRepository)
//                .<Product, Product> chunk(100, platformTransactionManager)
//                .reader(productReader())
//                .processor(createNewProductBatch())
//                .writer(flagWrite())
//                .build();
//    }
//
//    @Bean
//    public RepositoryItemReader<Product> productReader() {
//        return new RepositoryItemReaderBuilder<Product>()
//                .name("readNewProduct")
//                .pageSize(50)
//                .methodName("findByAnomalyFlagFalse")
//                .repository(productRepository)
//                .sorts(Map.of("prdId", Direction.ASC))
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Product, Product> createNewProductBatch() {
//        return product -> {
//            product.setAnomalyFlag(true);
//            return product;
//        };
//    }
//
//    @Bean
//    public RepositoryItemWriter<Product> flagWrite() {
//        return new RepositoryItemWriterBuilder<Product>()
//                .repository(productRepository)
//                .methodName("save")
//                .build();
//    }
}
