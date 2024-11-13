package com.ssafy.s103.domain.gsproduct.application.service;

import com.ssafy.s103.domain.gsproduct.application.repository.GsProductRepository;
import com.ssafy.s103.domain.gsproduct.entity.GsShopProduct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GsProductService {

    private final GsProductRepository gsProductRepository;

    public List<GsShopProduct> getAllProducts() {
        return gsProductRepository.findAll();
    }

    public long getProductCount() {
        return gsProductRepository.count();
    }
}