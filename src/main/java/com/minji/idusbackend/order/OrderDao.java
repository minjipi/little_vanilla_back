package com.minji.idusbackend.order;

import com.minji.idusbackend.member.MemberDao;
import com.minji.idusbackend.member.model.MemberInfo;
import com.minji.idusbackend.order.model.PostOrderReq;
import com.minji.idusbackend.product.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OrderDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MemberDao memberDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public String createOrder(int userLoginRes, PostOrderReq postOrderReq) {

        for (int i = 0; i < postOrderReq.getProductAmountList().size(); i++) {

            String createOrderQuery = "insert into orders (member_idx, product_idx, amount, status) VALUES (?, ?, ?,?) ";
            Object[] createOrderParams = new Object[]{userLoginRes, postOrderReq.getProductAmountList().get(i).getProduct_idx(), postOrderReq.getProductAmountList().get(i).getAmount(), postOrderReq.getStatus()};

            this.jdbcTemplate.update(createOrderQuery, createOrderParams);
        }

        if (postOrderReq.getStatus().equals("결제완료")){
            plusUserPointByGrade(userLoginRes, postOrderReq);
        }

        return "성공";
    }

    public String cancelOrder(int userLoginRes, int idx) {
        String cancelOrderQuery = "update orders set status = ? where member_idx = ? and idx = ?";
        Object[] cancelOrderParams = new Object[]{"주문취소", userLoginRes, idx};
        this.jdbcTemplate.update(cancelOrderQuery, cancelOrderParams);
        return "성공";
    }

    public void plusUserPointByGrade(int userLoginRes, PostOrderReq postOrderReq) {

        MemberInfo memberinfo = memberDao.getUserGradeAndPoint(userLoginRes);
        int getPoint = memberinfo.getPoint();

        int totalPrice = 0;

        for (int i = 0; i < postOrderReq.getProductAmountList().size(); i++) {

            int productPrice = productDao.getProductPrice(postOrderReq.getProductAmountList().get(i).getProduct_idx());
            int productNum = postOrderReq.getProductAmountList().get(i).getAmount();

            System.out.println("productPrice : "+productPrice);
            System.out.println("productNum : "+productNum);


            totalPrice += productPrice * productNum;
        }

        System.out.println("totalPrice : "+totalPrice);
        System.out.println("getPoint : "+getPoint);

        if (memberinfo.getGrade().equals("VIP")) {
            int totalUserPoint = getPoint + (totalPrice * 5 / 100);
            memberDao.setMemberPoint(userLoginRes, totalUserPoint);

        } else {
            int totalUserPoint = getPoint + (totalPrice * 3 / 100);
            memberDao.setMemberPoint(userLoginRes, totalUserPoint);
        }

    }
}

