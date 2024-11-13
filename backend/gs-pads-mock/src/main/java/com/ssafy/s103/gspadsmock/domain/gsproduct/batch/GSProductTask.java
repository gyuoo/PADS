package com.ssafy.s103.gspadsmock.domain.gsproduct.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.s103.gspadsmock.batch.tasklet.CustomTasklet;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductRepository;
import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import com.ssafy.s103.gspadsmock.domain.gsproduct.service.RedisTimeService;
import com.ssafy.s103.gspadsmock.global.util.TimeFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Slf4j
public class GSProductTask implements CustomTasklet {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final AnomalyProductRepository anomalyProductRepository;
    private final RedisTimeService redisTimeService;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDt = redisTimeService.getStartTime();
        LocalDateTime endDt = now.withMinute((now.getMinute() / 5) * 5).withSecond(0).withNano(0);

        log.info("Redis Start Time : {}", startDt);
        if (startDt == null) {
            AnomalyProduct product = anomalyProductRepository.findLastFetchProduct().orElse(null);
            if (product == null) {
                LocalDateTime tmpNow = LocalDateTime.now();
                startDt = tmpNow.withMinute((tmpNow.getMinute() / 5) * 5).withSecond(0).withNano(0).minusMinutes(5);
            } else {
                startDt = product.getCreateDt().plusMinutes(5);
            }
        } else if (startDt.isAfter(LocalDateTime.now())) {
            LocalDateTime tmpNow = LocalDateTime.now();
            startDt = tmpNow.withMinute((tmpNow.getMinute() / 5) * 5).withSecond(0).withNano(0)
                    .minusMinutes(5);
        } else if (startDt.isEqual(endDt)) {
            return RepeatStatus.FINISHED;
        }

        redisTimeService.updateStartTime(endDt);
        log.info("Redis Start Time : {} / Redis End Time : {}", startDt, endDt);
        endDt = now.withMinute((now.getMinute() / 5) * 5).withSecond(0).withNano(0).minusSeconds(1L);

        List<Map<String, Object>> fetchData = fetchProducts(startDt.format(TimeFormat.ISO_DATETIME_FORMATTER),
                endDt.format(TimeFormat.ISO_DATETIME_FORMATTER));

        List<AnomalyProduct> dataList = convertToProductDataList(fetchData).stream().map(
                        (GsShopProduct data) -> AnomalyProduct.gsProductConvertOf(data, data.getCreateDt().toLocalDateTime()))
                .toList();

        anomalyProductRepository.saveBulk(dataList);
        return RepeatStatus.FINISHED;
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
