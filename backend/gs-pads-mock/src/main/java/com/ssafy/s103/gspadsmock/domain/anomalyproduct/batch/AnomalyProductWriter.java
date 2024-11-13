package com.ssafy.s103.gspadsmock.domain.anomalyproduct.batch;

import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProduct;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.entity.AnomalyProductBatch;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductBatchRepository;
import com.ssafy.s103.gspadsmock.domain.anomalyproduct.repository.AnomalyProductRepository;
import com.ssafy.s103.gspadsmock.global.service.S3Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class AnomalyProductWriter implements ItemWriter<AnomalyProduct> {
    private final AnomalyProductBatchRepository productBatchRepository;
    private final AnomalyProductRepository productRepository;
    private final S3Service s3Service;
    private final String filePath;
    private final String[] headers;

    public AnomalyProductWriter(AnomalyProductBatchRepository productBatchRepository,
                                AnomalyProductRepository productRepository, S3Service s3Service, String filePath, String[] headers) {
        this.productBatchRepository = productBatchRepository;
        this.productRepository = productRepository;
        this.s3Service = s3Service;
        this.filePath = filePath;
        this.headers = headers;
    }

    @Override
    public void write(Chunk<? extends AnomalyProduct> chunk) throws Exception {
        String batchId = productBatchRepository.saveBatch();
        log.info("Batch : {} , {}, {}, {}", batchId, chunk.getItems().size(),
                chunk.getItems().getFirst().getPrdId(), chunk.getItems().getFirst().getCreateDt());

        productRepository.bulkUpdateBatchId((List<AnomalyProduct>) chunk.getItems(), batchId);

        LocalDate today = LocalDate.now();
        String datePath = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path directoryPath = Paths.get(filePath, datePath);

        // 폴더가 존재하지 않으면 생성
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String filePath = createFilePath(directoryPath, batchId);
        File fileToUpload = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(fileToUpload, true);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter,
                     CSVFormat.DEFAULT.builder().setHeader(headers).build())) {

            for (AnomalyProduct item : chunk.getItems()) {
                csvPrinter.printRecord(itemToRecord(item));
            }

            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }

        String s3FolderPath = "datacsv/" + datePath;
        String s3FileUrl = s3Service.uploadFile(s3FolderPath, fileToUpload);
        log.info("File uploaded to S3: {}", s3FileUrl);
    }

    private String createFilePath(Path directoryPath, String batchId) {
        return directoryPath + "/" + batchId + ".csv";
    }

    private List<Object> itemToRecord(AnomalyProduct item) {
        List<Object> record = new ArrayList<>();
        for (String fieldName : headers) {
            try {
                String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method getterMethod = AnomalyProduct.class.getMethod(getterMethodName);

                Object value = getterMethod.invoke(item);
                record.add(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field: " + fieldName, e);
            } catch (InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return record;
    }
}
