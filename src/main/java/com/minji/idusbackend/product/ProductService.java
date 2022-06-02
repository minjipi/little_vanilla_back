package com.minji.idusbackend.product;

import com.minji.idusbackend.cbn.model.GetCbnProductRes;
import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public PostProductRes createProduct(BigInteger useridx, PostProductReq postProductReq) {
        return productDao.createProduct(useridx, postProductReq);
    }

//    public List<GetProductRes> getProducts() throws Exception {
//        try {
//            List<GetProductRes> getProductResList = productDao.getProducts();
//            return getProductResList;
//        } catch (Exception exception) {
//            throw new Exception();
//        }
//    }

    public GetProductRes getProduct(BigInteger idx) throws Exception {
        try {
            GetProductRes getProductRes = productDao.getProduct(idx);
            return getProductRes;
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    public List<ProductImage> getProductImages(BigInteger idx) throws Exception {
        try {
            List<ProductImage> getProductImageResList = productDao.getProductImages(idx);
            return getProductImageResList;
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    public PatchProductRes deleteProduct(BigInteger idx) throws Exception {
        try {
            return productDao.deleteProduct(idx);
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    public void updateProduct(PatchProductReq patchProductReq, BigInteger idx) throws Exception {
        try {
            productDao.updateProduct(patchProductReq, idx);
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    public ProductImage createProductImage(int poductIdx, ProductImage productImage) {
        return productDao.createProductImage(poductIdx, productImage);
    }


    public List<GetProductWithImageAndLikesRes> getProductsWithProductImage() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.toString() == "anonymousUser") {
            try {
                System.out.println("로그인 안됨");
                List<GetProductWithImageAndLikesRes> getProductWithImageResList = productDao.getProductsWithProductImage();
                return getProductWithImageResList;
            } catch (Exception exception) {
                System.out.println("service : " + exception);

                throw new Exception();
            }
        } else {
            UserLoginRes userDetails = (UserLoginRes) principal;
            try {
                System.out.println("getProductsWithProductImageAndLikes");
                List<GetProductWithImageAndLikesRes> getProductWithImageResList = productDao.getProductsWithProductImageAndLikes(userDetails.getIdx());
                return getProductWithImageResList;
            } catch (Exception exception) {
                System.out.println("service : " + exception);

                throw new Exception();
            }
        }
    }

    public List<GetProductRes> getSearchProducts(String word, Integer isDelFree, Integer gte, Integer lte) throws Exception {
        try {
            List<GetProductRes> getProductResList = productDao.getSearchProducts(word, isDelFree, gte, lte);
            return getProductResList;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

            throw new Exception();
        }
    }

    public List<GetCbnProductRes> likeList(BigInteger userLoginResIdx) {
        return productDao.likeList(userLoginResIdx);
    }


    public String likeProduct(BigInteger userLoginResIdx, int idx) {
        return productDao.likeProduct(userLoginResIdx, idx);
    }
}
