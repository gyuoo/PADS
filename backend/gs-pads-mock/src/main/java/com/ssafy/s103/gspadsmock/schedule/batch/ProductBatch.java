package com.ssafy.s103.gspadsmock.schedule.batch;

import com.ssafy.s103.gspadsmock.schedule.entity.Product;
import com.ssafy.s103.gspadsmock.schedule.repository.ProductRepository;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ProductBatch {
    public static final String PRODUCT_BATCH_NAME = "productBatch";
    private final JobRepository jobRepository;
    private final ProductRepository productRepository;
    private final PlatformTransactionManager platformTransactionManager;

    public ProductBatch(JobRepository jobRepository, ProductRepository productRepository,
                        PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.productRepository = productRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public Job newProductJob() {
        return new JobBuilder(PRODUCT_BATCH_NAME, jobRepository)
                .start(productFirstStep())
                .build();
    }

    @Bean
    public Step productFirstStep() {
        //청크는 대량의 데이터를 사이즈만큼
        //100개의 데이터를 가져와야 할 때 10개씩 가져온다는 의미
        //사이즈 조절을 통해 I/O 최적화 가능
        return new StepBuilder("newProductJobFirstStep", jobRepository)
                .<Product, Product> chunk(100, platformTransactionManager)
                .reader(productReader())
                .processor(createNewProductBatch())
                .writer(flagWrite())
                .build();
    }

    @Bean
    public RepositoryItemReader<Product> productReader() {
        return new RepositoryItemReaderBuilder<Product>()
                .name("readNewProduct")
                .pageSize(100)
                .methodName("findByAnomalyFlagFalse")
                .repository(productRepository)
                .sorts(Map.of("prdId", Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Product, Product> createNewProductBatch() {
        return product -> {
            product.setAnomalyFlag(true);
            return product;
        };
    }

    @Bean
    public RepositoryItemWriter<Product> flagWrite() {
        return new RepositoryItemWriterBuilder<Product>()
                .repository(productRepository)
                .methodName("save")
                .build();
    }
}
