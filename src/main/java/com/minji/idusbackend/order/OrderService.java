package com.minji.idusbackend.order;

import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.order.model.GetOrderList;
import com.minji.idusbackend.order.model.PostOrderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    public String createOrder(BigInteger userLoginRes, PostOrderReq postOrderReq) {
        return orderDao.createOrder(userLoginRes, postOrderReq);
    }

    public String cancelOrder(BigInteger userLoginRes, int idx) {
        return orderDao.cancelOrder(userLoginRes, idx);
    }

    public List<GetOrderList> orderList(BigInteger userLoginRes) throws Exception {

        try {
            List<GetOrderList> getOrderLists = orderDao.orderList(userLoginRes);
            return getOrderLists;
        } catch (Exception exception) {
            System.out.println("service : " + exception);
            throw new Exception();
        }

    }
}
