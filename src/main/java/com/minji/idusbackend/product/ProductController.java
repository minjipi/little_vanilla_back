package com.minji.idusbackend.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minji.idusbackend.cart.CartService;
import com.minji.idusbackend.cbn.model.GetCbnProductRes;
import com.minji.idusbackend.config.BaseResponse;

import static com.minji.idusbackend.config.BaseResponseStatus.*;

import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.product.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.minji.idusbackend.utils.Validation.isValidatedIdx;
import static com.minji.idusbackend.utils.Validation.isValidatedSearchWord;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @ResponseBody
    @GetMapping("/{idx}")
    public BaseResponse<GetProductRes> getProducts(@PathVariable BigInteger idx) {

        if (idx == null) {
            return new BaseResponse<>(EMPTY_IDX);
        }

        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            GetProductRes getProductRes = productService.getProduct(idx);
            return new BaseResponse<>(getProductRes);
        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{idx}")
    public BaseResponse<PatchProductRes> deleteProduct(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable BigInteger idx) {
        if (idx == null) {
            return new BaseResponse<>(EMPTY_IDX);
        }
        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        BigInteger userIdx = userLoginRes.getIdx();
        if (Integer.parseInt(String.valueOf(userIdx)) != (Integer.parseInt(String.valueOf(idx)))) {
            System.out.println(userIdx + " " + idx);
            return new BaseResponse<>(USER_NOT_MATCH);
        }

        try {
            PatchProductRes patchProductRes = productService.deleteProduct(idx);
            return new BaseResponse<>(patchProductRes);
        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @PatchMapping("/update/{idx}")
    public BaseResponse<String> updateProduct(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable BigInteger idx, @RequestBody PatchProductReq patchProductReq) {

        try {
            BigInteger userIdx = userLoginRes.getIdx();
            System.out.println("글 수정 확인: " + patchProductReq.getBrandIdx() + "," + userIdx);

            if (Integer.parseInt(String.valueOf(userIdx)) != patchProductReq.getBrandIdx()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            productService.updateProduct(patchProductReq, idx);
            String result = idx + " 변경 완료.";
            return new BaseResponse<>(result);
        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/images/{idx}")
    public BaseResponse<List<ProductImage>> getProductImages(@PathVariable BigInteger idx) {
        if (idx == null) {
            return new BaseResponse<>(EMPTY_IDX);
        }

        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            List<ProductImage> getProductImageResList = productService.getProductImages(idx);
            return new BaseResponse<>(getProductImageResList);
        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @Value("/Users/minz/Desktop/upload")
    private String uploadPath;

    @ResponseBody
    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public BaseResponse<PostProductRes> createProduct(@AuthenticationPrincipal UserLoginRes userLoginRes, @RequestPart PostProductReq postProductReq, @RequestPart MultipartFile[] uploadFiles) {

        try {
            if (userLoginRes.getIdx() == null) {
                return new BaseResponse<>(POST_PRODUCTS_EMPTY_BRANDIDX);
            }
            if (postProductReq.getCategoryIdx() == 0) {
                return new BaseResponse<>(POST_PRODUCTS_EMPTY_CATEGORYIDX);
            }
            if (postProductReq.getName() == null) {
                return new BaseResponse<>(POST_PRODUCTS_EMPTY_NAME);
            }
            if (postProductReq.getPrice() == 0) {
                return new BaseResponse<>(POST_PRODUCTS_EMPTY_PRICE);
            }
            if (postProductReq.getSalePrice() == 0) {
                return new BaseResponse<>(POST_PRODUCTS_EMPTY_PRICE);
            }
            if (postProductReq.getDeliveryType() == null) {
                return new BaseResponse<>(POST_PRODUCTS_EMPTY_DELIVERYTYPE);
            }
            if (postProductReq.getIsTodayDeal() == null) {
                return new BaseResponse<>(POST_PRODUCTS_EMPTY_ISTODAYDEAL);
            }
            if (postProductReq.getContents() == null) {
                return new BaseResponse<>(POST_PRODUCTS_EMPTY_CONTENTS);
            }

            System.out.println("postProductReq : " + postProductReq.toString());
            System.out.println("userLoginRes : " + userLoginRes.toString());

            PostProductRes postProductRes = productService.createProduct(userLoginRes.getIdx(), postProductReq);
            List<ProductImage> resultDTOList = new ArrayList<>();

            for (MultipartFile uploadFile : uploadFiles) {
                if (uploadFile.getContentType().startsWith("image") == false) {
                    return new BaseResponse<>(POST_PRODUCTS_INVALID_FILES);
                }

                String originalName = uploadFile.getOriginalFilename();
                String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

                String saveName = uploadPath + File.separator + fileName;
                Path savePath = Paths.get(saveName);

                try {
                    uploadFile.transferTo(savePath);
                    ProductImage productImage = new ProductImage(fileName);
                    resultDTOList.add(productImage);
                    productService.createProductImage(postProductRes.getIdx(), productImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new BaseResponse<>(postProductRes);


        } catch (Exception exception) {
            System.out.println("exception :" + exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            File file = new File("/Users/minz/Desktop/upload" + File.separator + srcFileName);

            if (size != null && size.equals("1")) {
                file = new File(file.getParent(), file.getName().substring(2));
            }

            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/list")
    public BaseResponse<List<GetProductWithImageAndLikesRes>> getProductsWithProductImage() {
        try {
            List<GetProductWithImageAndLikesRes> getProductWithImageAndLikesResList = productService.getProductsWithProductImage();
            return new BaseResponse<>(getProductWithImageAndLikesResList);
        } catch (Exception exception) {
            System.out.println("controller : " + exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<List<GetProductRes>> getSearchProducts(String word,
                                                               @RequestParam(required = false, defaultValue = "0") Integer isDelFree,
                                                               @RequestParam(required = false, defaultValue = "-1") Integer gte,
                                                               @RequestParam(required = false, defaultValue = "-1") Integer lte) {
        if (!isValidatedSearchWord(word)) {
            return new BaseResponse<>(NULL_STRING);
        }

        try {
            if (word == null) {
                System.out.println("EMPTY_IDX");
                return new BaseResponse<>(EMPTY_IDX);
            }

            if (word == " ") {
                System.out.println("EMPTY_IDX");
                return new BaseResponse<>(EMPTY_IDX);
            }

            System.out.println("word: " + word);
            List<GetProductRes> getProductResList = productService.getSearchProducts(word, isDelFree, gte, lte);
            return new BaseResponse<>(getProductResList);
        } catch (Exception exception) {
            System.out.println(exception);
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/like/{idx}")
    public String likeProduct(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable int idx) {
        System.out.println("userLoginRes: " + userLoginRes);
        String user = String.valueOf(userLoginRes.getIdx());
        if (user == null) {
            System.out.println("user is NULL.");
        }
        return productService.likeProduct(userLoginRes.getIdx(), idx);
    }

    @ResponseBody
    @GetMapping("/likelist")
    public BaseResponse<List<GetCbnProductRes>> likeList(@AuthenticationPrincipal UserLoginRes userLoginRes) {
        List<GetCbnProductRes> getCbnProductResList = productService.likeList(userLoginRes.getIdx());
        return new BaseResponse<>(getCbnProductResList);
    }
}
