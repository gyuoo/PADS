package com.ssafy.s103.gspadsmock.domain.gsproduct.repository;

import com.ssafy.s103.gspadsmock.domain.gsproduct.entity.GsShopProduct;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class GsProductDao {
    private final JdbcTemplate jdbcTemplate;

    public GsProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_SQL = "INSERT INTO gs_shop_product_data (prd_id, view_name, prd_nm, cate1_nm, cate2_nm, cate3_nm, cate4_nm, " +
            "brd_name, supplier_code, class_name, cls_cd, org_item_cd, deal_flag, tv_flag, tempout_flag, price, " +
            "discprice, buy_count, review_score, review_count, prd_discount_date, " +
            "attr_char_val_1, attr_char_val_2, attr_char_val_3, attr_char_val_4, attr_char_val_5, attr_char_val_6, " +
            "attr_char_val_7, attr_char_val_8, coupon_desc, coupon_num, delivery_code, dtct_cd, dlv_pick_mthod_cd, " +
            "prd_adult_flag, create_dt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void saveAll(List<GsShopProduct> dataList) {
        jdbcTemplate.batchUpdate(INSERT_SQL, dataList, dataList.size(), this::setPreparedStatement);
    }

    public void save(GsShopProduct data) {
        jdbcTemplate.update(INSERT_SQL, ps -> setPreparedStatement(ps, data));
    }

    private void setPreparedStatement(PreparedStatement ps, GsShopProduct data) throws SQLException {
        ps.setString(1, data.getPrdId());
        ps.setString(2, data.getViewName());
        ps.setString(3, data.getPrdNm());
        ps.setString(4, data.getCate1Nm());
        ps.setString(5, data.getCate2Nm());
        ps.setString(6, data.getCate3Nm());
        ps.setString(7, data.getCate4Nm());
        ps.setString(8, data.getBrdName());
        ps.setString(9, data.getSupplierCode());
        ps.setString(10, data.getClassName());
        ps.setString(11, data.getClsCd());
        ps.setObject(12, data.getOrgItemCd());
        ps.setBoolean(13, data.getDealFlag());
        ps.setBoolean(14, data.getTvFlag());
        ps.setBoolean(15, data.getTempoutFlag());
        ps.setObject(16, data.getPrice());
        ps.setObject(17, data.getDiscprice());
        ps.setObject(18, data.getBuyCount());
        ps.setObject(19, data.getReviewScore());
        ps.setObject(20, data.getReviewCount());
        ps.setString(21, data.getPrdDiscountDate());
        ps.setString(22, data.getAttrCharVal1());
        ps.setString(23, data.getAttrCharVal2());
        ps.setString(24, data.getAttrCharVal3());
        ps.setString(25, data.getAttrCharVal4());
        ps.setString(26, data.getAttrCharVal5());
        ps.setString(27, data.getAttrCharVal6());
        ps.setString(28, data.getAttrCharVal7());
        ps.setString(29, data.getAttrCharVal8());
        ps.setString(30, data.getCouponDesc());
        ps.setString(31, data.getCouponNum());
        ps.setString(32, data.getDeliveryCode());
        ps.setString(33, data.getDtctCd());
        ps.setString(34, data.getDlvPickMthodCd());
        ps.setBoolean(35, data.getPrdAdultFlag());
        ps.setTimestamp(36, Timestamp.from(OffsetDateTime.now().toInstant()));
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
