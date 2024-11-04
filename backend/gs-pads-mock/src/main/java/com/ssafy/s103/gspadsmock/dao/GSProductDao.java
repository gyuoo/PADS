package com.ssafy.s103.gspadsmock.dao;

import com.ssafy.s103.gspadsmock.entity.GsShopProductData;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GSProductDao {
    private final JdbcTemplate jdbcTemplate;

    public GSProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertGSProduct(List<GsShopProductData> dataList) {
//        jdbcTemplate.query("");
    }
}
