package com.ssafy.s103.gspadsmock.domain.gsproduct.service;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import com.ssafy.s103.gspadsmock.domain.gsproduct.repository.GSProductDao;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GsProductService {
    private final GSProductDao gsProductRepository;

    public GsProductService(GSProductDao gsProductRepository) {
        this.gsProductRepository = gsProductRepository;
    }

    public List<GsShopProduct> findByBetweenDate(LocalDateTime startDt, LocalDateTime endDt) {
        return gsProductRepository.findProductsBetweenDate(startDt, endDt);
    }
}
