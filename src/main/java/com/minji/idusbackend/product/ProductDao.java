package com.minji.idusbackend.product;

import com.minji.idusbackend.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    public List<GetProductWithImageRes> getProductsWithProductImage() {
        String getProductsQuery = "SELECT * FROM product left JOIN (SELECT productIdx, group_concat(filename) FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx";

        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductWithImageRes(
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

    public List<GetProductRes> getSearchProducts(String word, Integer isDelFree, Integer gte, Integer lte) {
        String getProductsQuery = "select * from Product left JOIN (SELECT productIdx, group_concat(filename) FROM productImage group by productIdx) as pi ON product.idx=pi.productIdx WHERE name LIKE ?";

        if (gte != -1 && lte != -1) {
            getProductsQuery += "AND salePrice >= "+gte+" AND salePrice <= "+lte;
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


}