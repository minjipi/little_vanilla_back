package com.minji.idusbackend.order;

import com.minji.idusbackend.cart.model.GetCart;
import com.minji.idusbackend.member.MemberDao;
import com.minji.idusbackend.member.model.MemberInfo;
import com.minji.idusbackend.order.model.GetOrder;
import com.minji.idusbackend.order.model.PostOrderReq;
import com.minji.idusbackend.pay.model.PostOrderResponse;
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
public class OrderDao {
    private JdbcTemplate jdbcTemplate;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer orderPriceCheck(BigInteger productIdx) {
        String orderPriceCheckQuery = "SELECT salePrice FROM product WHERE idx=?";

        Integer price = this.jdbcTemplate.queryForObject(orderPriceCheckQuery, Integer.class, productIdx);

        return price;
    }

    public PostOrderResponse createOrder(BigInteger productIdx, BigInteger memberIdx, String impUid) {
        String createOrderQuery = "insert into `order` (product_idx, member_idx, imp_uid) VALUES (?, ?, ?)";
        Object[] createOrderParams = new Object[]{
                productIdx,
                memberIdx,
                impUid
        };

        this.jdbcTemplate.update(createOrderQuery, createOrderParams);
        String getLastInsertIdxQuery = "select last_insert_id()";
        Integer lastInsertIdx = this.jdbcTemplate.queryForObject(getLastInsertIdxQuery, Integer.class);
        return new PostOrderResponse(lastInsertIdx, 1);
    }


    public String createOrderBy(BigInteger userLoginRes, PostOrderReq postOrderReq) {

        for (int i = 0; i < postOrderReq.getProductAmountList().size(); i++) {
            String createOrderQuery = "insert into orders (member_idx, product_idx, amount, status) VALUES (?, ?, ?,?) ";
            Object[] createOrderParams = new Object[]{userLoginRes, postOrderReq.getProductAmountList().get(i).getProduct_idx(), postOrderReq.getProductAmountList().get(i).getAmount(), postOrderReq.getStatus()};
            this.jdbcTemplate.update(createOrderQuery, createOrderParams);
        }
        if (postOrderReq.getStatus().equals("????????????")) {
            plusUserPointByGrade(userLoginRes, postOrderReq);
        }
        return "??????";
    }

    public String cancelOrder(BigInteger userLoginRes, int idx) {
        String cancelOrderQuery = "update orders set status = ? where member_idx = ? and idx = ?";
        Object[] cancelOrderParams = new Object[]{"????????????", userLoginRes, idx};
        this.jdbcTemplate.update(cancelOrderQuery, cancelOrderParams);
        return "??????";
    }

    public List<GetOrder> orderList(BigInteger userLoginRes) {
        String orderListQuery = "select * from `order` left outer join product on product.idx=`order`.product_idx left outer join productImage on productImage.productIdx=product.idx where member_idx=? GROUP BY productImage.productIdx";

        return this.jdbcTemplate.query(orderListQuery,
                (rs, rowNum) -> new GetOrder(
                        rs.getObject("idx", BigInteger.class),
                        rs.getObject("productIdx", BigInteger.class),
                        rs.getObject("brandIdx", BigInteger.class),
                        rs.getString("name"),
                        rs.getObject("price", BigInteger.class),
                        rs.getObject("salePrice", BigInteger.class),
                        rs.getString("deliveryType"),
                        rs.getString("isTodayDeal"),
                        rs.getString("filename"),
                        rs.getTimestamp("create_timestamp")
                ), userLoginRes);
    }

    public void plusUserPointByGrade(BigInteger userLoginRes, PostOrderReq postOrderReq) {
        MemberInfo memberinfo = memberDao.getUserGradeAndPoint(userLoginRes);
        int getPoint = memberinfo.getPoint();
        int totalPrice = 0;

        for (int i = 0; i < postOrderReq.getProductAmountList().size(); i++) {
            int productPrice = productDao.getProductPrice(postOrderReq.getProductAmountList().get(i).getProduct_idx());
            int productNum = postOrderReq.getProductAmountList().get(i).getAmount();

            System.out.println("productPrice : " + productPrice);
            System.out.println("productNum : " + productNum);

            totalPrice += productPrice * productNum;
        }

        System.out.println("totalPrice : " + totalPrice);
        System.out.println("getPoint : " + getPoint);

        if (memberinfo.getGrade().equals("VIP")) {
            int totalUserPoint = getPoint + (totalPrice * 5 / 100);
            memberDao.setMemberPoint(userLoginRes, totalUserPoint);

        } else {
            int totalUserPoint = getPoint + (totalPrice * 3 / 100);
            memberDao.setMemberPoint(userLoginRes, totalUserPoint);
        }
    }
}

