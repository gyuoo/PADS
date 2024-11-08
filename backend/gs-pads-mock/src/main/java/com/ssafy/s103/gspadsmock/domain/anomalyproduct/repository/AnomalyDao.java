package com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import java.util.List;

public interface AnomalyDao {
    void saveBulk(List<AnomalyProduct> data);
}
