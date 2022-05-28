package com.minji.idusbackend.cart;

import com.minji.idusbackend.cart.model.GetCartList;
import com.minji.idusbackend.cart.model.PostCartReq;
import com.minji.idusbackend.cart.model.PostCartRes;
import com.minji.idusbackend.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

import static com.minji.idusbackend.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartDao cartDao;

    public PostCartRes createCart(BigInteger userLoginRes, PostCartReq postCartReq) {
        return cartDao.createCart(userLoginRes, postCartReq);
    }

    public PostCartRes cancelCart(BigInteger userLoginRes, BigInteger idx) throws BaseException {

        try {
            PostCartRes postCartRes = cartDao.cancelCart(userLoginRes, idx);
            return postCartRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



    public List<GetCartList> cartList(BigInteger userLoginRes) throws Exception {
        try {
            List<GetCartList> getCartLists = cartDao.cartList(userLoginRes);
            System.out.println("CartService: ");
            return getCartLists;
        } catch (Exception exception) {
            System.out.println("service : " + exception);
            throw new Exception();
        }
    }

}
