package com.minji.idusbackend.product;

import com.minji.idusbackend.cbn.model.GetCbnProductRes;
import com.minji.idusbackend.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostProductRes createProduct(BigInteger useridx, PostProductReq postProductReq) {
        System.out.println(postProductReq.toString());

        String createProductQuery = "insert into product (name, brandIdx, categoryIdx, price, salePrice, deliveryType, isTodayDeal) values (?,?,?,?,?,?,?)";

        Object[] createProductParams = new Object[]{postProductReq.getName(), useridx, postProductReq.getCategoryIdx()
                , postProductReq.getPrice(), postProductReq.getSalePrice(), postProductReq.getDeliveryType(), postProductReq.getIsTodayDeal()};

        this.jdbcTemplate.update(createProductQuery, createProductParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        return new PostProductRes(lastInsertIdx, 1);
    }

    public GetProductRes getProduct(BigInteger idx) {
        String getProductQuery = "SELECT * FROM product left JOIN (SELECT productIdx, group_concat(filename) FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx where idx = ?";

        return this.jdbcTemplate.queryForObject(getProductQuery
                , (rs, rowNum) -> new GetProductRes(
                        rs.getObject("idx", BigInteger.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", BigInteger.class),
                        rs.getObject("categoryIdx", BigInteger.class),
                        rs.getObject("price", int.class),
                        rs.getObject("salePrice", int.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("group_concat(filename)")), idx);
    }

    public List<ProductImage> getProductImages(BigInteger idx) {
        String getProductImagesQuery = "select idx, imageUrl from ProductImage\n" +
                "where productIdx = ? and status = 1";

        return this.jdbcTemplate.query(getProductImagesQuery,
                (rs, rowNum) -> new ProductImage(
                        rs.getObject("idx", int.class),
                        rs.getString("imageUrl")
                ), idx);
    }

    public PatchProductRes deleteProduct(BigInteger idx) {
        String deleteProductQuery = "delete from Product where idx = ?";
        this.jdbcTemplate.update(deleteProductQuery, idx);

        String getLastInsertIdxQuery = "select last_insert_id()";
        BigInteger lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, BigInteger.class);

        return new PatchProductRes(lastInsertIdx, 0);
    }


    public PatchProductRes updateProduct(int idx, PostProductReq postProductReq) {

        String updateProductQuery = "update Product set name=?, categoryIdx=?, price=?, salePrice=?, deliveryType=?, isTodayDeal=? where idx = ?";

        Object[] createProductParams = new Object[]{postProductReq.getName(), postProductReq.getCategoryIdx()
                , postProductReq.getPrice(), postProductReq.getSalePrice(), postProductReq.getDeliveryType(), postProductReq.getIsTodayDeal(), idx};

        this.jdbcTemplate.update(updateProductQuery, createProductParams);

        String getLastInsertIdxQuery = "select last_insert_id()";

        BigInteger lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, BigInteger.class);

        return new PatchProductRes(lastInsertIdx, 0);
    }


    public ProductImage createProductImage(int productIdx, ProductImage productImage) {

        String createProductImageQuery = "insert into productImage (filename, productIdx) VALUES (?, ?)";

        Object[] createProductImageParams = new Object[]{productImage.getFilename(), productIdx};

        this.jdbcTemplate.update(createProductImageQuery, createProductImageParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);
        productImage.setIdx(lastInsertIdx);

        return productImage;
    }

    public List<GetProductWithImageAndLikesRes> getProductsWithProductImage() {
        String getProductsQuery = "SELECT * FROM product left JOIN (SELECT productIdx, group_concat(filename) as filenames FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx";

        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductWithImageAndLikesRes(
                        rs.getObject("idx", BigInteger.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", BigInteger.class),
                        rs.getObject("categoryIdx", BigInteger.class),
                        rs.getObject("price", BigInteger.class),
                        rs.getObject("salePrice", BigInteger.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filenames"),
                        false
                ));
    }

    public List<GetProductWithImageAndLikesRes> getProductsWithProductImageAndLikes(BigInteger member_idx) {
        String getProductsQuery = "SELECT *, ifnull(product_idx, false) as like_ckeck FROM product left JOIN (SELECT productIdx, group_concat(filename) as filenames FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx LEFT JOIN (SELECT product_idx FROM likes WHERE member_idx=?) as likes ON likes.product_idx=product.idx";

        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductWithImageAndLikesRes(
                        rs.getObject("idx", BigInteger.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", BigInteger.class),
                        rs.getObject("categoryIdx", BigInteger.class),
                        rs.getObject("price", BigInteger.class),
                        rs.getObject("salePrice", BigInteger.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filenames"),
                        rs.getBoolean("like_ckeck")
                ), member_idx);
    }

    public List<GetProductRes> getSearchProducts(String word, Integer isDelFree, Integer gte, Integer lte) {
        String getProductsQuery = "select * from product left JOIN (SELECT productIdx, group_concat(filename) FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx WHERE name LIKE ?";

        if (gte != -1 && lte != -1) {
            getProductsQuery += "AND salePrice >= " + gte + " AND salePrice <= " + lte;
        }

        if (isDelFree == 1) {
            getProductsQuery += " AND deliveryType='T'";
        }

        System.out.println(getProductsQuery);
        System.out.println("isDelFree" + isDelFree);

        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getObject("idx", BigInteger.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", BigInteger.class),
                        rs.getObject("categoryIdx", BigInteger.class),
                        rs.getObject("price", int.class),
                        rs.getObject("salePrice", int.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("group_concat(filename)")), "%" + word + "%");
    }

    //    public String likeProduct(BigInteger userLoginResIdx, int idx, String cabinetIdx) {
//
//        System.out.println(userLoginResIdx);
//        System.out.println(idx);
//
//        // 좋아요를 누른 적이 있나 확인
//        String getLikeQuery = "select * from likes where member_idx=? and product_idx=?";
//        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(getLikeQuery, userLoginResIdx, idx);
//
//        for(Map<String, Object> row : rows){
//            int member_idx = Integer.parseInt(row.get("member_idx").toString());
//            int product_idx = Integer.parseInt(row.get("product_idx").toString());
//
//            System.out.println(member_idx + " " + product_idx);
//        }
//
//        // 없으면 추가
//        if (rows.size() == 0) {
//            String createProductQuery = "insert into likes (member_idx, product_idx, cabinet_idx) VALUES (?, ?, ?)";
//
//            Object[] createProductParams = new Object[]{userLoginResIdx, idx, cabinetIdx};
//
//            this.jdbcTemplate.update(createProductQuery, createProductParams);
//
//            String getLastInsertIdxQuery = "select last_insert_id()";
//            int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);
//
//            return "added";
//        } else {
//            // 있으면 제거
//            String createProductQuery = "DELETE FROM likes WHERE member_idx=? and product_idx=?";
//
//            Object[] createProductParams = new Object[]{userLoginResIdx, idx};
//
//            this.jdbcTemplate.update(createProductQuery, createProductParams);
//
//            String getLastInsertIdxQuery = "select last_insert_id()";
//            int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);
//
//            return "deleted";
//        }
//    }
    public String likeProduct(BigInteger userLoginResIdx, int idx) {
        System.out.println(userLoginResIdx + ", " + idx);

        String getLikeQuery = "select * from likes where member_idx=? and product_idx=?";
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(getLikeQuery, userLoginResIdx, idx);

        for (Map<String, Object> row : rows) {
            int member_idx = Integer.parseInt(row.get("member_idx").toString());
            int product_idx = Integer.parseInt(row.get("product_idx").toString());
            System.out.println(member_idx + " " + product_idx);
        }

        if (rows.size() == 0) {
            String createProductQuery = "insert into likes (member_idx, product_idx) VALUES (?, ?)";
            Object[] createProductParams = new Object[]{userLoginResIdx, idx};
            this.jdbcTemplate.update(createProductQuery, createProductParams);
            String getLastInsertIdxQuery = "select last_insert_id()";
            int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);
            return "added";

        } else {
            String createProductQuery = "DELETE FROM likes WHERE member_idx=? and product_idx=?";
            Object[] createProductParams = new Object[]{userLoginResIdx, idx};
            this.jdbcTemplate.update(createProductQuery, createProductParams);
            String getLastInsertIdxQuery = "select last_insert_id()";
            int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);
            return "deleted";
        }
    }

    //    수정 해야함 !
    public List<GetCbnProductRes> likeList(BigInteger member_idx) {
        String getCbnQuery = "select * from likes left outer join product on product.idx=likes.product_idx left outer join productImage on productImage.productIdx=product.idx where cabinet_idx is NULL and member_idx=?";

        return this.jdbcTemplate.query(getCbnQuery,
                (rs, rowNum) -> new GetCbnProductRes(
                        rs.getObject("idx", BigInteger.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", BigInteger.class),
                        rs.getObject("categoryIdx", BigInteger.class),
                        rs.getObject("price", BigInteger.class),
                        rs.getObject("salePrice", BigInteger.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filename")
                ), member_idx);
    }


    public int getProductPrice(int idx) {
        String findEmailQuery = "SELECT * FROM product WHERE idx=?";
        return this.jdbcTemplate.queryForObject(findEmailQuery
                , (rs, rowNum) -> new Integer(
                        rs.getObject("salePrice", int.class)
                ), idx);
    }
//    saleP, price 설정 시
//    createOrderWithPoint 포인트로 주문시
}
