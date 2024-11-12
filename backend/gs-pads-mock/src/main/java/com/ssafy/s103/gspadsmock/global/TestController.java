package com.ssafy.s103.gspadsmock.global;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import com.ssafy.s103.gspadsmock.domain.gsproduct.service.GsProductService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {
    private final GsProductService gsProductService;

    public TestController(GsProductService gsProductService) {
        this.gsProductService = gsProductService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<GsShopProduct>> findNewProduct(@RequestParam("startDateTime") String startDt,
                                                              @RequestParam("endDateTime") String endDt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDt, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDt, formatter);
        log.info("start : {} , end : {}", startDateTime, endDateTime);
        return ResponseEntity.ok(gsProductService.findByBetweenDate(startDateTime, endDateTime));
    }
}
