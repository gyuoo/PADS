package com.ssafy.s103.domain.gsproduct.api;

import com.ssafy.s103.domain.gsproduct.application.service.GsProductService;
import com.ssafy.s103.domain.gsproduct.entity.GsShopProduct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class GsProductController {

    private final GsProductService gsProductService;

    @GetMapping
    public ResponseEntity<List<GsShopProduct>> getAllProducts() {
        List<GsShopProduct> result = gsProductService.getAllProducts();
        return ResponseEntity.ok(result);
    }
}
