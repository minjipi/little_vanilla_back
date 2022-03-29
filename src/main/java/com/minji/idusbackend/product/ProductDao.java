package com.minji.idusbackend.product;

import com.minji.idusbackend.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public PostProductRes createProduct(PostProductReq postProductReq) {
        System.out.println(postProductReq.toString());

        String createProductQuery = "insert into Product (name, brandIdx, categoryIdx, price, salePrice, deliveryType, isTodayDeal) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[] createProductParams = new Object[]{postProductReq.getName(), postProductReq.getBrandIdx(), postProductReq.getCategoryIdx()
                , postProductReq.getPrice(), postProductReq.getSalePrice(), postProductReq.getDeliveryType(), postProductReq.getIsTodayDeal()};

        this.jdbcTemplate.update(createProductQuery, createProductParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        return new PostProductRes(lastInsertIdx, 1);
    }

    public List<GetProductRes> getProducts() {
        String getProductsQuery = "select * from Product";

        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getObject("idx", int.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", int.class),
                        rs.getObject("categoryIdx", int.class),
                        rs.getObject("price", int.class),
                        rs.getObject("salePrice", int.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("group_concat(filename)")));
    }

    public GetProductRes getProduct(int idx) {
        String getProductQuery = "SELECT * FROM product left JOIN (SELECT productIdx, group_concat(filename) FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx where idx = ?";

        return this.jdbcTemplate.queryForObject(getProductQuery
                , (rs, rowNum) -> new GetProductRes(
                        rs.getObject("idx", int.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", int.class),
                        rs.getObject("categoryIdx", int.class),
                        rs.getObject("price", int.class),
                        rs.getObject("salePrice", int.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("group_concat(filename)")), idx);
    }

    public List<ProductImage> getProductImages(int idx) {
        String getProductImagesQuery = "select idx, imageUrl from ProductImage\n" +
                "where productIdx = ? and status = 1";

        return this.jdbcTemplate.query(getProductImagesQuery,
                (rs, rowNum) -> new ProductImage(
                        rs.getObject("idx", int.class),
                        rs.getString("imageUrl")
                ), idx);
    }

    public PatchProductRes deleteProduct(int idx) {
        String deleteProductQuery = "delete from Product where idx = ?";
        this.jdbcTemplate.update(deleteProductQuery, idx);

        String getLastInsertIdxQuery = "select last_insert_id()";
        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

        return new PatchProductRes(lastInsertIdx, 0);
    }


    public PatchProductRes updateProduct(int idx, PostProductReq postProductReq) {

        String updateProductQuery = "update Product set name=?, brandIdx=?, categoryIdx=?, price=?, salePrice=?, deliveryType=?, isTodayDeal=? where idx = ?";
        Object[] createProductParams = new Object[]{postProductReq.getName(), postProductReq.getBrandIdx(), postProductReq.getCategoryIdx()
                , postProductReq.getPrice(), postProductReq.getSalePrice(), postProductReq.getDeliveryType(), postProductReq.getIsTodayDeal(), idx};

        this.jdbcTemplate.update(updateProductQuery, createProductParams);


        String getLastInsertIdxQuery = "select last_insert_id()";

        int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

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
        String getProductsQuery = "SELECT * FROM product left JOIN (SELECT productIdx, group_concat(filename) FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx";

        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductWithImageAndLikesRes(
                        rs.getObject("idx", int.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", int.class),
                        rs.getObject("categoryIdx", int.class),
                        rs.getObject("price", int.class),
                        rs.getObject("salePrice", int.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filename"),
                        0
                ));
    }

    public List<GetProductWithImageAndLikesRes> getProductsWithProductImageAndLikes(int member_idx) {
        String getProductsQuery = "SELECT *, ifnull(product_idx, 0) as like_ckeck FROM product left JOIN (SELECT productIdx, group_concat(filename) as filename FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx LEFT JOIN (SELECT product_idx FROM likes WHERE member_idx=?) as likes ON likes.product_idx=product.idx;";

        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductWithImageAndLikesRes(
                        rs.getObject("idx", int.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", int.class),
                        rs.getObject("categoryIdx", int.class),
                        rs.getObject("price", int.class),
                        rs.getObject("salePrice", int.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filename"),
                        rs.getObject("like_ckeck", int.class)
                        ),member_idx);
    }



    public List<GetProductRes> getSearchProducts(String word, Integer isDelFree, Integer gte, Integer lte) {
        String getProductsQuery = "select * from Product left JOIN (SELECT productIdx, group_concat(filename) FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx WHERE name LIKE ?";

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
                        rs.getObject("idx", int.class),
                        rs.getString("name"),
                        rs.getObject("brandIdx", int.class),
                        rs.getObject("categoryIdx", int.class),
                        rs.getObject("price", int.class),
                        rs.getObject("salePrice", int.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("group_concat(filename)")), "%" + word + "%");
    }

    public String likeProduct(int userLoginResIdx, int idx) {
        // 좋아요를 누른 적이 있나 확인
        String getLikeQuery = "select * from likes where member_idx=? and product_idx=?";
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(getLikeQuery, userLoginResIdx, idx);

        for(Map<String, Object> row : rows){
            int member_idx = Integer.parseInt(row.get("member_idx").toString());
            int product_idx = Integer.parseInt(row.get("product_idx").toString());;

            System.out.println(member_idx + " " + product_idx);
        }

        // 없으면 추가
        if (rows.size() == 0) {
            // 있으면 제거
            String createProductQuery = "insert into likes (member_idx, product_idx) VALUES (?, ?)";

            Object[] createProductParams = new Object[]{userLoginResIdx, idx};

            this.jdbcTemplate.update(createProductQuery, createProductParams);

            String getLastInsertIdxQuery = "select last_insert_id()";
            int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

            return "added";
        } else {
            // 있으면 제거
            String createProductQuery = "DELETE FROM likes WHERE member_idx=? and product_idx=?";

            Object[] createProductParams = new Object[]{userLoginResIdx, idx};

            this.jdbcTemplate.update(createProductQuery, createProductParams);

            String getLastInsertIdxQuery = "select last_insert_id()";
            int lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, int.class);

            return "deleted";

        }
    }

}