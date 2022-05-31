package com.minji.idusbackend.utils;

import java.math.BigInteger;

public class Validation {

    public static boolean isValidatedBirthDay(String birthDay) {
        Integer birthDayInt = Integer.parseInt(birthDay);

        if (birthDayInt < 2021 && birthDayInt > 1900 && birthDay.length() == 4) {
            return true;
        }
        return false;
    }

    public static boolean isValidatedStatus(Integer status) {
        if (status.equals(0) || status.equals(1)) {
            return true;
        }
        return false;
    }

    public static boolean isValidatedIdx(BigInteger idx) {
        BigInteger bigInteger = new BigInteger("0");

        if (idx.compareTo(bigInteger) == -1) {
            return false;
        }
        return true;
    }

    public static boolean isValidatedSearchWord(String string) {
        if (string == null) {
            return false;
        }
        return true;
    }

    public static boolean isValidatedStatusFromOrder(String status) {
        if (status.equals("주문취소") || status.equals("입금대기") || status.equals("결제완료")
                || status.equals("배송준비") || status.equals("배송중") || status.equals("배송완료")
                || status.equals("리뷰쓰기")) {
            return true;
        }
        return false;
    }

    public static boolean isValidatedDeliveryType(String deliveryType) {
        if (deliveryType.equals("무료배송") || deliveryType.equals("일반배송")) {
            return true;
        }
        return false;
    }

}
