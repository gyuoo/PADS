package com.ssafy.s103.domain.batch.api;

import com.ssafy.s103.domain.batch.application.service.BatchService;
import com.ssafy.s103.domain.batch.dto.response.BatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
public class BatchController {

    private final BatchService batchService;

    @GetMapping
    public ResponseEntity<Page<BatchResponse>> getAllBatchProducts(Pageable pageable) {
        Page<BatchResponse> batchResponse = batchService.getAllBatchProducts(pageable);
        return ResponseEntity.ok(batchResponse);
    }
}
