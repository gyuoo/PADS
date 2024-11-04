package com.ssafy.s103.gspadsmock.schedule.job;

import com.ssafy.s103.gspadsmock.entity.GsShopProductData;
import com.ssafy.s103.gspadsmock.schedule.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.client.RestClient;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Getter
@Setter
public class GsShopProductJob extends QuartzJobBean {

    private final RestClient restClient;

    public GsShopProductJob(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://localhost:8080/api")
                .build();
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    }

    public Map fetchProducts() {
        return restClient.get()
                .uri("/products")  // 외부 API 엔드포인트
                .retrieve()
                .body(Map.class);
    }
}
