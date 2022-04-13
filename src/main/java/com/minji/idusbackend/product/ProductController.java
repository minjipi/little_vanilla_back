package com.minji.idusbackend.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minji.idusbackend.config.BaseResponse;

import static com.minji.idusbackend.config.BaseResponseStatus.*;

import com.minji.idusbackend.member.model.UserLoginRes;
import com.minji.idusbackend.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

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
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{idx}")
    public BaseResponse<PatchProductRes> deleteProduct(@PathVariable BigInteger idx) {
        if (idx == null) {
            return new BaseResponse<>(EMPTY_IDX);
        }
        if (!isValidatedIdx(idx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            PatchProductRes patchProductRes = productService.deleteProduct(idx);
            return new BaseResponse<>(patchProductRes);
        } catch (Exception exception) {
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @PatchMapping("/update/{idx}")
    public BaseResponse<PatchProductRes> updateProduct(@PathVariable int idx, @RequestBody PostProductReq postProductReq) {

        try {
            PatchProductRes patchProductRes = productService.updateProduct(idx, postProductReq);
            return new BaseResponse<>(patchProductRes);
        } catch (Exception exception) {
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/images/{idx}")
    public BaseResponse<List<ProductImage>> getProductImages(@PathVariable int idx) {
        try {
            List<ProductImage> getProductImageResList = productService.getProductImages(idx);
            return new BaseResponse<>(getProductImageResList);
        } catch (Exception exception) {
            return new BaseResponse<>(FAIL);
        }
    }

    @Value("/Users/minz/Desktop/upload")
    private String uploadPath;

    @ResponseBody
    @PostMapping("/create")
    public BaseResponse<PostProductRes> createProduct(String body, MultipartFile[] uploadFiles) {
        System.out.println("body : "+body);

        try {
            PostProductReq postProductReq = new ObjectMapper().readValue(body, PostProductReq.class);
//            일반적으로 controller가 dto를 통해서 (dto에 값을 담아서)받는데, 이건 파일을 전달 받아야하기 때문에
//            body, uploadFiles 이런 파일을 전달 받음. 여기의 body에서 파일 이름이나 이런걸 postProductReq에 저장하는 것임.


            if (postProductReq.getBrandIdx() == null) {
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
            } else {

                PostProductRes postProductRes = productService.createProduct(postProductReq);
                List<ProductImage> resultDTOList = new ArrayList<>();

                for (MultipartFile uploadFile : uploadFiles) {
                    if (uploadFile.getContentType().startsWith("image") == false) {
                        return new BaseResponse<>(FAIL);
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
            }

        } catch (Exception exception) {
            System.out.println("ex :"+exception);
            return new BaseResponse<>(FAIL);
        }
    }


//    @PostMapping("/imgUpload/{productIdx}")
//    public BaseResponse<List<ProductImage>> uploadFile(@PathVariable int productIdx, MultipartFile[] uploadFiles) {
//
//        List<ProductImage> resultDTOList = new ArrayList<>();
//
//        for (MultipartFile uploadFile : uploadFiles) {
//
//            if (uploadFile.getContentType().startsWith("image") == false) {
//                return new BaseResponse<>(FAIL);
//            }
//
//            String originalName = uploadFile.getOriginalFilename();
//            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//
//            String saveName = uploadPath + File.separator + fileName;
//            Path savePath = Paths.get(saveName);
//
//            try {
//                uploadFile.transferTo(savePath);
//                ProductImage productImage = new ProductImage(fileName);
//                resultDTOList.add(productImage);
//                productService.createProductImage(productIdx, productImage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return new BaseResponse<>(resultDTOList);
//    }

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
    public BaseResponse<List<GetProductRes>> getProducts() {
        try {
            List<GetProductRes> getProductResList = productService.getProducts();
            return new BaseResponse<>(getProductResList);
        } catch (Exception exception) {
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/lists")
    public BaseResponse<List<GetProductWithImageAndLikesRes>> getProductsWithProductImage() {
        try {
            List<GetProductWithImageAndLikesRes> getProductWithImageAndLikesResList = productService.getProductsWithProductImage();
            return new BaseResponse<>(getProductWithImageAndLikesResList);
        } catch (Exception exception) {
            return new BaseResponse<>(FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<List<GetProductRes>> getSearchProducts(String word,
                                                               @RequestParam(required = false, defaultValue = "0") Integer isDelFree,
                                                               @RequestParam(required = false, defaultValue = "-1") Integer gte,
                                                               @RequestParam(required = false, defaultValue = "-1") Integer lte) {
        try {
            List<GetProductRes> getProductResList = productService.getSearchProducts(word, isDelFree, gte, lte);
            return new BaseResponse<>(getProductResList);
        } catch (Exception exception) {
            return new BaseResponse<>(FAIL);
        }
    }

//    @ResponseBody
//    @GetMapping("/like/{idx}")
//    public String likeProduct(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable int idx, @Nullable String cabinetIdx) {
//        System.out.println("cabinetIdx: "+cabinetIdx);
//        return productService.likeProduct(userLoginRes.getIdx(),idx, cabinetIdx);
//    }

    @ResponseBody
    @GetMapping("/like/{idx}")
    public String likeProduct(@AuthenticationPrincipal UserLoginRes userLoginRes, @PathVariable int idx) {
        return productService.likeProduct(userLoginRes.getIdx(), idx);
    }
}
