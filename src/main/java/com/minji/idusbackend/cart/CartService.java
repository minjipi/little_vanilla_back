package com.minji.idusbackend.cart;

import com.minji.idusbackend.cart.model.PostCartReq;
import com.minji.idusbackend.order.model.GetOrderList;
import com.minji.idusbackend.order.model.PostOrderReq;
import com.minji.idusbackend.pay.model.PostOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartDao cartDao;

    public String createCart(BigInteger userLoginRes, PostCartReq postCartReq) {
        return cartDao.createCart(userLoginRes, postCartReq);
    }

}
