package com.minji.idusbackend.order;

import com.minji.idusbackend.cart.model.GetCart;
import com.minji.idusbackend.order.model.GetOrder;
import com.minji.idusbackend.order.model.PostOrderReq;
import com.minji.idusbackend.pay.model.PostOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    public String createOrderBy(BigInteger userLoginRes, PostOrderReq postOrderReq) {
        return orderDao.createOrderBy(userLoginRes, postOrderReq);
    }

    public String cancelOrder(BigInteger userLoginRes, int idx) {
        return orderDao.cancelOrder(userLoginRes, idx);
    }

    public List<GetOrder> orderList(BigInteger userLoginRes) throws Exception {
        try {
            List<GetOrder> getOrderList = orderDao.orderList(userLoginRes);
            return getOrderList;
        } catch (Exception exception) {
            System.out.println("service : " + exception);
            throw new Exception();
        }
    }

    public Integer orderPriceCheck(BigInteger productIdx){
        return orderDao.orderPriceCheck(productIdx);
    }

    public PostOrderResponse createOrder(BigInteger productIdx, BigInteger memberIdx, String impUid){
        return orderDao.createOrder(productIdx, memberIdx, impUid);
    }
}
