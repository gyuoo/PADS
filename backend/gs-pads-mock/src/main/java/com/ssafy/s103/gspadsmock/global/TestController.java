package com.ssafy.s103.gspadsmock.global;

import com.ssafy.s103.gspadsmock.batch.ProductBatch;
import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import com.ssafy.s103.gspadsmock.domain.gsproduct.service.GsProductService;
import com.ssafy.s103.gspadsmock.entity.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
