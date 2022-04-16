package com.minji.idusbackend.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    SUCCESS(true, 1000, "요청 성공."),

    //    2000 : Request 오류
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_USER_STATUS(false,2004,"삭제되거나 휴면인 계정의 접근입니다."),
    INVALID_USER_PASSWORD(false,2005,"잘못된 비밀번호입니다."),

    // [PATCH] /users
    PATCH_PRE_DELETED_USER(false,2040,"이미 탈퇴한 회원입니다."),

    // [GET] /products
    EMPTY_IDX(false,2050,"IDX 값을 입력해주세요."),
    EMPTY_OFFSET(false,2051,"Offset(페이지번호)를 입력해주세요."),

    // [POST] /products
    POST_PRODUCTS_EMPTY_BRANDIDX(false,2100,"브랜드 IDX 를 입력해주세요."),
    POST_PRODUCTS_EMPTY_CATEGORYIDX(false,2101,"카테고리 IDX 를 입력해주세요."),
    POST_PRODUCTS_EMPTY_NAME(false,2102,"상품이름을 입력해주세요."),
    POST_PRODUCTS_EMPTY_PRICE(false,2103,"가격을 입력해주세요."),
    POST_PRODUCTS_EMPTY_SALEPRICE(false,2104,"할인가격을 입력해주세요."),
    POST_PRODUCTS_EMPTY_DELIVERYTYPE(false,2105,"배송유형을 입력해주세요."),
    POST_PRODUCTS_EMPTY_ISTODAYDEAL(false,2106,"오늘의딜 여부를 입력해주세요."),

    POST_PRODUCTS_INVALID_DELIVERYTYPE(false,2110,"잘못된 배송유형이 입력되었습니다."),
    POST_PRODUCTS_INVALID_ISTODAYDEAL(false,2111,"잘못된 오늘의딜 여부가 입력되었습니다."),

    POST_PRODUCTS_PRE_EXIST_PRODUCT(false,2120,"이미 존재하는 상품의 이름이 입력되었습니다."),



    // 5000
    LOGOUT_JWT(false, 5000, "이미 로그아웃 된 JWT 입니다."),
    INVALID_ACCESS_TOKEN(false, 5001, "유효하지 않은 토큰입니다."),
    KAKAO_LOGIN_REQUEST_FAILED(false, 5002, "카카오 소셜 로그인 중 응답 받기에 실패했습니다."),
    INVALID_IDX(false, 5003, "잘못된 IDX 값입니다."),
    INVALID_OFFSET(false, 5004, "잘못된 OFFSET 값입니다."),

    // 7000
    FAIL(false, 7000, "실패.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
