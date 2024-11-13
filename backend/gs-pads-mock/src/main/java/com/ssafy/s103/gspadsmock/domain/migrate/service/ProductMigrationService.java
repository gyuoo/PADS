package com.ssafy.s103.gspadsmock.domain.migrate.service;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import com.ssafy.s103.gspadsmock.domain.gsproduct.repository.GsProductDao;
import com.ssafy.s103.gspadsmock.domain.migrate.entity.ProductSample;
import com.ssafy.s103.gspadsmock.domain.migrate.repository.ProductSampleRepository;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductMigrationService implements ApplicationRunner {
    private final ProductSampleRepository productSampleRepository;
    private final GsProductDao productRepository;
    private final Random random = new Random();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProductMigrationService(ProductSampleRepository productSampleRepository, GsProductDao productRepository) {
        this.productSampleRepository = productSampleRepository;
        this.productRepository = productRepository;
    }

    public void migrateProducts() {
        ProductSample productSamples = productSampleRepository.findRandomLimit().orElse(null);
        // 조회된 데이터를 product 테이블에 삽입 후, 삭제
        if (productSamples != null) {
            GsShopProduct gsShopProducts = productSamples.toGsShopProduct();

            productSampleRepository.delete(productSamples); // product_sample 테이블에서 삭제
            productRepository.save(gsShopProducts);
        }
    }

    private int getRandomDelay() {
        return random.nextInt((2000 - 100) + 1) + 100; // min에서 max 사이의 랜덤 값 반환
    }

    public void stop() {
        executorService.shutdownNow();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        executorService.submit(() -> {
            while (true) {
                try {
                    migrateProducts(); // 데이터 마이그레이션
                    int delay = getRandomDelay(); // 100밀리초 ~ 2000밀리초(2초) 사이 랜덤 지연 시간 생성
                    TimeUnit.MILLISECONDS.sleep(delay); // 지정된 밀리초만큼 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace(); // 예외 로그 출력
                }
            }
        });
    }
}
