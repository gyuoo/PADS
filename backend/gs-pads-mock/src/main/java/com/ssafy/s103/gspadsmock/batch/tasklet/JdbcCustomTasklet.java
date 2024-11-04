package com.ssafy.s103.gspadsmock.batch.tasklet;

import com.ssafy.s103.gspadsmock.entity.ProductDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class JdbcCustomTasklet implements CustomTasklet {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<ProductDto> productList = jdbcTemplate.query("SELECT prd_id, anomaly_flag FROM productsample", (rs, rowNum) -> new ProductDto(rs.getString("prd_id"), rs.getBoolean("anomaly_flag")));

        log.info("size : {}", productList.size());

        return RepeatStatus.FINISHED;
    }
}
