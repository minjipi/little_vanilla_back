package com.minji.idusbackend.order;

import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.order.model.PostOrderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    public String createOrder(int userLoginRes, PostOrderReq postOrderReq){

        return orderDao.createOrder(userLoginRes, postOrderReq);
    }

    public String cancelOrder(int userLoginRes, int idx){
        return orderDao.cancelOrder(userLoginRes, idx);
    }
}