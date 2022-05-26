package com.minji.idusbackend.cart;

import com.minji.idusbackend.cart.model.PostCartReq;
import com.minji.idusbackend.member.MemberDao;
import com.minji.idusbackend.member.model.MemberInfo;
import com.minji.idusbackend.order.model.GetOrderList;
import com.minji.idusbackend.order.model.PostOrderReq;
import com.minji.idusbackend.pay.model.PostOrderResponse;
import com.minji.idusbackend.product.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CartDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MemberDao memberDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public String createCart(BigInteger userLoginRes, PostCartReq postCartReq) {
        for (int i = 0; i < postCartReq.getProductAmountList().size(); i++) {
            String createOrderQuery = "insert into `cart` (member_idx, product_idx, amount) VALUES (?, ?, ?)";
            Object[] createOrderParams = new Object[]{userLoginRes, postCartReq.getProductAmountList().get(i).getProduct_idx(), postCartReq.getProductAmountList().get(i).getAmount()};

            this.jdbcTemplate.update(createOrderQuery, createOrderParams);
        }
        return "성공";
    }
}

