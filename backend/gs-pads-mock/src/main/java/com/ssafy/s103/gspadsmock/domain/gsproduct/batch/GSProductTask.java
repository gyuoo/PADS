package com.ssafy.s103.gspadsmock.domain.gsproduct.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTasklet;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductRepository;
import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@Slf4j
public class GSProductTask implements CustomTasklet {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final AnomalyProductRepository anomalyProductRepository;

    public GSProductTask(RestClient restClient, ObjectMapper objectMapper,
                         AnomalyProductRepository anomalyProductRepository) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.anomalyProductRepository = anomalyProductRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String startDt = (String) getObject(chunkContext, "startDt");
        String endDt = (String) getObject(chunkContext, "endDt");

        List<Map<String, Object>> fetchData = fetchProducts(startDt, endDt);

        List<AnomalyProduct> dataList = convertToProductDataList(fetchData).stream().map(
                AnomalyProduct::gsProductConvertOf).toList();

        anomalyProductRepository.saveBulk(dataList);
        return RepeatStatus.FINISHED;
    }

    private static Object getObject(ChunkContext chunkContext, String key) {
        return chunkContext.getStepContext()
                .getJobParameters()
                .get(key);
    }

    public List fetchProducts(String startDt, String endDt) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/products")
                        .queryParam("startDateTime", startDt)
                        .queryParam("endDateTime", endDt)
                        .build())// 외부 API 엔드포인트
                .retrieve()
                .body(List.class);
    }

    private List<GsShopProduct> convertToProductDataList(List<Map<String, Object>> fetchData) throws Exception {
        return fetchData.stream()
                .map(dataMap -> objectMapper.convertValue(dataMap, GsShopProduct.class))
                .collect(Collectors.toList());
    }
}
