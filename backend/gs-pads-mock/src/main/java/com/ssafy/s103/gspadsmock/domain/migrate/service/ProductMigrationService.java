package com.ssafy.s103.gspadsmock.domain.migrate.service;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import com.ssafy.s103.gspadsmock.domain.gsproduct.repository.GSProductDao;
import com.ssafy.s103.gspadsmock.domain.migrate.entity.ProductSample;
import com.ssafy.s103.gspadsmock.domain.migrate.repository.ProductSampleRepository;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ProductMigrationService implements ApplicationRunner {

    private final ProductSampleRepository productSampleRepository;

    private final GSProductDao productRepository;

    private final Random random = new Random();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public ProductMigrationService(ProductSampleRepository productSampleRepository, GSProductDao productRepository) {
        this.productSampleRepository = productSampleRepository;
        this.productRepository = productRepository;
    }

    // 무한히 실행하는 메서드

    @Transactional
    public void migrateProducts() {
        // 1~10개의 데이터를 랜덤으로 조회
        List<ProductSample> productSamples = productSampleRepository.findRandomLimit(random.nextInt(20) + 1);

        // 조회된 데이터를 product 테이블에 삽입 후, 삭제
        List<GsShopProduct> gsShopProducts = new ArrayList<>();
        for (ProductSample sample : productSamples) {
            gsShopProducts.add(sample.toGsShopProduct());// product 테이블에 저장
            productSampleRepository.delete(sample); // product_sample 테이블에서 삭제
        }
        productRepository.saveAll(gsShopProducts);
    }

    private int getRandomDelay(int min, int max) {
        return random.nextInt((max - min) + 1) + min; // min에서 max 사이의 랜덤 값 반환
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        executorService.submit(() -> {
            while (true) {
                try {
                    migrateProducts(); // 데이터 마이그레이션
                    int delay = getRandomDelay(1, 180); // 1초 ~ 180초(3분) 사이 랜덤 지연 시간 생성
                    TimeUnit.SECONDS.sleep(delay); // 지정된 분만큼 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace(); // 예외 로그 출력
                }
            }
        });
    }

    public void stop() {
        executorService.shutdownNow();
    }
}
