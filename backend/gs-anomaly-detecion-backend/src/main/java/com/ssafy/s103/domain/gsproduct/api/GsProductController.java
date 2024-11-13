package com.ssafy.s103.domain.gsproduct.api;

import com.ssafy.s103.domain.gsproduct.application.service.GsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class GsProductController {

    private final GsProductService gsProductService;

}
