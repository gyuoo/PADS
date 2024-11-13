package com.ssafy.s103.domain.gsproduct.application.service;

import com.ssafy.s103.domain.gsproduct.application.repository.GsProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GsProductService {

    private final GsProductRepository gsProductRepository;

    public long getProductCount() {
        return gsProductRepository.count();
    }
}