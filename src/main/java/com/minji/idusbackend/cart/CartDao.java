package com.minji.idusbackend.cart;

import com.minji.idusbackend.cart.model.GetCartList;
import com.minji.idusbackend.cart.model.PostCartReq;
import com.minji.idusbackend.cart.model.PostCartRes;
import com.minji.idusbackend.member.MemberDao;
import com.minji.idusbackend.product.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartDao {
    private JdbcTemplate jdbcTemplate;

//    private final MemberDao memberDao;
//    private final ProductDao productDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public PostCartRes createCart(BigInteger userLoginRes, PostCartReq postCartReq) {
        String createCartQuery = "insert into `cart` (member_idx, product_idx, amount, status) VALUES (?, ?, ?, ?)";
        Object[] createCartParams = new Object[]{userLoginRes, postCartReq.getProductIdx(), postCartReq.getAmount(), 1 };
        this.jdbcTemplate.update(createCartQuery, createCartParams);

        String getLastInsertIdxQuery = "select last_insert_id()";
        BigInteger lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, BigInteger.class);

        return new PostCartRes(lastInsertIdx, 1);
    }

    public PostCartRes cancelCart(BigInteger userLoginRes, BigInteger idx) {
        String deleteQuery = "update cart set status = 0 where  member_idx=? and idx = ?";
        this.jdbcTemplate.update(deleteQuery, userLoginRes, idx);

        return new PostCartRes(idx, 0);
    }

    public List<GetCartList> cartList(BigInteger userLoginRes) {
        String cartListQuery = "select * from cart left outer join product on product.idx=cart.product_idx left outer join productImage on productImage.productIdx=product.idx where member_idx=? and status=1";

        return this.jdbcTemplate.query(cartListQuery,
                (rs, rowNum) -> new GetCartList(
                        rs.getObject("idx", BigInteger.class),
                        rs.getObject("brandIdx", BigInteger.class),
                        rs.getObject("amount", int.class),
                        rs.getString("name"),
                        rs.getObject("price", BigInteger.class),
                        rs.getObject("salePrice", BigInteger.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filename")
                ), userLoginRes);
    }
}