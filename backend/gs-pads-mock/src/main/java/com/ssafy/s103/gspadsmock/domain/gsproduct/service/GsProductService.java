package com.ssafy.s103.gspadsmock.domain.gsproduct.service;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import com.ssafy.s103.gspadsmock.domain.gsproduct.repository.GsProductDao;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GsProductService {
    private final GsProductDao gsProductRepository;

    public GsProductService(GsProductDao gsProductRepository) {
        this.gsProductRepository = gsProductRepository;
    }

    public List<GsShopProduct> findByBetweenDate(LocalDateTime startDt, LocalDateTime endDt) {
        return gsProductRepository.findProductsBetweenDate(startDt, endDt);
    }
}
