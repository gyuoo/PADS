package com.ssafy.s103.domain.anomalylog.application.service;

import com.ssafy.s103.domain.anomalylog.application.repository.AnomalyLogDetailRepository;
import com.ssafy.s103.domain.anomalylog.application.repository.AnomalyLogRepository;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLog;
import com.ssafy.s103.domain.anomalylog.entity.AnomalyLogDetail;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnomalyLogService {

    private final AnomalyLogRepository anomalyLogRepository;
    private final AnomalyLogDetailRepository anomalyLogDetailRepository;

    public AnomalyLog getAnomalyLog(Long prdId) {
        return anomalyLogRepository.findByPrdId(prdId);
    }

    public List<AnomalyLogDetail> getAnomalyLogDetails(AnomalyLog anomalyLog) {
        return anomalyLogDetailRepository.findByAnomalyLog(anomalyLog);
    }

    public Map<Integer, Long> getMonthlyAnomalyCount() {
        List<Object[]> results = anomalyLogRepository.countMonthAnomaly();

        Map<Integer, Long> monthlyCounts = new HashMap<>();
        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long count = (Long) result[1];
            monthlyCounts.put(month, count);
        }
        for (int i = 1; i <= 12; i++) {
            monthlyCounts.putIfAbsent(i, 0L);
        }
        return monthlyCounts;
    }

    public Map<String, Long> getCountsByCodes() {
        List<Object[]> results = anomalyLogDetailRepository.countByCodes();

        return results.stream()
            .collect(Collectors.toMap(
                result -> (String) result[0], // code
                result -> (Long) result[1]    // count
            ));
    }
}
