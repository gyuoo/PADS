package com.ssafy.s103.gspadsmock.domain.gsproduct.repository;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class GSProductDao {
    private final JdbcTemplate jdbcTemplate;

    public GSProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(List<GsShopProduct> dataList) {
        String sql = "INSERT INTO gs_shop_product_data (prd_id, view_name, prd_nm, cate1_nm, cate2_nm, cate3_nm, cate4_nm, " +
                "brd_name, supplier_code, class_name, cls_cd, org_item_cd, deal_flag, tv_flag, tempout_flag, price, " +
                "discprice, buy_count, review_score, review_count, prd_discount_date, " +
                "attr_char_val_1, attr_char_val_2, attr_char_val_3, attr_char_val_4, attr_char_val_5, attr_char_val_6, " +
                "attr_char_val_7, attr_char_val_8, coupon_desc, coupon_num, delivery_code, dtct_cd, dlv_pick_mthod_cd, " +
                "prd_adult_flag, create_dt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, dataList, dataList.size(), (ps, argument) -> {
            ps.setString(1, argument.getPrdId());
            ps.setString(2, argument.getViewName());
            ps.setString(3, argument.getPrdNm());
            ps.setString(4, argument.getCate1Nm());
            ps.setString(5, argument.getCate2Nm());
            ps.setString(6, argument.getCate3Nm());
            ps.setString(7, argument.getCate4Nm());
            ps.setString(8, argument.getBrdName());
            ps.setString(9, argument.getSupplierCode());
            ps.setString(10, argument.getClassName());
            ps.setString(11, argument.getClsCd());
            ps.setObject(12, argument.getOrgItemCd());
            ps.setBoolean(13, argument.getDealFlag());
            ps.setBoolean(14, argument.getTvFlag());
            ps.setBoolean(15, argument.getTempoutFlag());
            ps.setObject(16, argument.getPrice());
            ps.setObject(17, argument.getDiscprice());
            ps.setObject(18, argument.getBuyCount());
            ps.setObject(19, argument.getReviewScore());
            ps.setObject(20, argument.getReviewCount());
            ps.setString(21, argument.getPrdDiscountDate());
            ps.setString(22, argument.getAttrCharVal1());
            ps.setString(23, argument.getAttrCharVal2());
            ps.setString(24, argument.getAttrCharVal3());
            ps.setString(25, argument.getAttrCharVal4());
            ps.setString(26, argument.getAttrCharVal5());
            ps.setString(27, argument.getAttrCharVal6());
            ps.setString(28, argument.getAttrCharVal7());
            ps.setString(29, argument.getAttrCharVal8());
            ps.setString(30, argument.getCouponDesc());
            ps.setString(31, argument.getCouponNum());
            ps.setString(32, argument.getDeliveryCode());
            ps.setString(33, argument.getDtctCd());
            ps.setString(34, argument.getDlvPickMthodCd());
            ps.setBoolean(35, argument.getPrdAdultFlag());
            ps.setTimestamp(36, Timestamp.from(OffsetDateTime.now().toInstant()));
        });
    }
    public void save(GsShopProduct data) {

    }

    public void insertGSProduct(List<GsShopProduct> dataList) {
//        jdbcTemplate.query("");
    }

    public List<GsShopProduct> findProductsBetweenDate(LocalDateTime startDt, LocalDateTime endDt) {
        String sql = "SELECT * FROM gs_shop_product_data WHERE create_dt >= ? AND create_dt <= ?";

        return jdbcTemplate.query(sql, new Object[]{Timestamp.valueOf(startDt), Timestamp.valueOf(endDt)},
                (rs, rowNum) -> GsShopProduct.builder()
                        .prdId(rs.getString("prd_id"))
                        .viewName(rs.getString("view_name"))
                        .prdNm(rs.getString("prd_nm"))
                        .cate1Nm(rs.getString("cate1_nm"))
                        .cate2Nm(rs.getString("cate2_nm"))
                        .cate3Nm(rs.getString("cate3_nm"))
                        .cate4Nm(rs.getString("cate4_nm"))
                        .brdName(rs.getString("brd_name"))
                        .supplierCode(rs.getString("supplier_code"))
                        .className(rs.getString("class_name"))
                        .clsCd(rs.getString("cls_cd"))
                        .orgItemCd(rs.getInt("org_item_cd"))
                        .dealFlag(rs.getBoolean("deal_flag"))
                        .tvFlag(rs.getBoolean("tv_flag"))
                        .tempoutFlag(rs.getBoolean("tempout_flag"))
                        .price(rs.getInt("price"))
                        .discprice(rs.getInt("discprice"))
                        .buyCount(rs.getInt("buy_count"))
                        .reviewScore(rs.getInt("review_score"))
                        .reviewCount(rs.getInt("review_count"))
                        .prdDiscountDate(rs.getString("prd_discount_date"))
                        .attrCharVal1(rs.getString("attr_char_val_1"))
                        .attrCharVal2(rs.getString("attr_char_val_2"))
                        .attrCharVal3(rs.getString("attr_char_val_3"))
                        .attrCharVal4(rs.getString("attr_char_val_4"))
                        .attrCharVal5(rs.getString("attr_char_val_5"))
                        .attrCharVal6(rs.getString("attr_char_val_6"))
                        .attrCharVal7(rs.getString("attr_char_val_7"))
                        .attrCharVal8(rs.getString("attr_char_val_8"))
                        .couponDesc(rs.getString("coupon_desc"))
                        .couponNum(rs.getString("coupon_num"))
                        .deliveryCode(rs.getString("delivery_code"))
                        .dtctCd(rs.getString("dtct_cd"))
                        .dlvPickMthodCd(rs.getString("dlv_pick_mthod_cd"))
                        .prdAdultFlag(rs.getBoolean("prd_adult_flag"))
                        .createDt(rs.getObject("create_dt", OffsetDateTime.class))
                        .build()
        );
    }
}
