package com.ssafy.s103.gspadsmock.global.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucket;

    public String uploadFile(String folderName, File file) {
        // S3의 파일 경로 (폴더명/파일명) 생성
        String fileName = file.getName();
        String s3Key = folderName + "/" + fileName;

        try {
            amazonS3.putObject(new PutObjectRequest(bucket, s3Key, file));
//            return amazonS3.getUrl(bucket, s3Key).toString();
            return s3Key;
        } catch (Exception e) {
            log.error("S3 업로드 중 오류 발생: ", e);
            throw new RuntimeException("파일 업로드에 실패했습니다.");
        }
    }
}
