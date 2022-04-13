package com.minji.idusbackend.utils;

import java.math.BigInteger;

public class Validation {
    public static boolean isValidatedHousingType(String housingType) {
        if (housingType.equals("원룸&오피스텔") || housingType.equals("아파트")
                || housingType.equals("빌라") || housingType.equals("단독주택")) {
            return true;
        }
        return false;
    }

    public static boolean isValidatedHouseSize(String houseSize) {
        if (houseSize.equals("10평 미만") || houseSize.equals("10평대")
                || houseSize.equals("20평대") || houseSize.equals("30평대")
                || houseSize.equals("40평 이상")) {
            return true;
        }
        return false;
    }

    public static boolean isValidatedBirthDay(String birthDay) {
        Integer birthDayInt = Integer.parseInt(birthDay);

        if (birthDayInt < 2021 && birthDayInt > 1900 && birthDay.length() == 4) {
            return true;
        }
        return false;
    }

    public static boolean isValidatedSex(String sex) {
        if (sex.equals("F") || sex.equals("M")) {
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

    public static boolean isValidatedFourOffset(Integer offset) {
        if (offset % 4 != 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidatedFiveOffset(Integer offset) {
        if (offset % 5 != 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidatedTenOffset(Integer offset) {
        if (offset % 10 != 0) {
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

    public static boolean isValidatedReviewStars(Integer durabilityStars, Integer priceStars
                , Integer designStars, Integer deliveryStars) {
        if (durabilityStars >= 0 && durabilityStars <= 5
                && priceStars >= 0 && priceStars <= 5
                && designStars >= 0 && designStars <= 5
                && deliveryStars >= 0 && deliveryStars <= 5) {
            return true;
        }
        return false;
    }

    public static boolean isValidatedReviewIsTodayHouse(String isTodayHouse) {
        if (isTodayHouse.equals("Y") || isTodayHouse.equals("N")) {
            return true;
        }
        return false;
    }

    public static boolean isValidatedReviewText(String reviewText) {
        if (reviewText.length() >= 20) {
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

    public static boolean isValidatedIsTodayDeal(String idTodayDeal) {
        if (idTodayDeal.equals("Y") || idTodayDeal.equals("N")) {
            return true;
        }
        return false;
    }
}
