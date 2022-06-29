🍦 [A Little Vanilla](http://www.alittlevanilla.kro.kr/, "link")  : Backend
================

> 👉🏻 Summary <br />
> 이 프로젝트는 스프링부트와 리액트로 회원가입, 로그인, 카카오로그인, 상품 등록/조회/수정/삭제/좋아요/장바구니/구매 등 다양한 쇼핑몰 서비스를 제공하기 위해 만들어졌습니다. <br />
> 현재 [I am port](https://www.iamport.kr/?gclid=CjwKCAjws8yUBhA1EiwAi_tpEawr0NbpwdG_4bW9KRPVRXXVEdwQ32yO8SyXEhMBfxiqHIml3c8uxhoC2nYQAvD_BwE, "Iamport link") 결제 API를 연동하여 실제 거래까지 이루어지지만, 현재 배포된 사이트에서는 당일 자정에 모두 환불됩니다. 안심하고 테스트 해보세요!
<br />

## 프로젝트 전체 구조 <br />
<img width="1353" alt="전체과정" src="https://user-images.githubusercontent.com/68539040/176331491-ac4ec70b-5d4b-4299-af16-5f86301e3b37.png">
 <br /> <br />

## 🚀 프로젝트 주요 관심사
+ 이유와 근거가 명확한 기술의 사용을 지향합니다.
+ 객체지향적 개념을 이해하고 이를 코드에 녹여내어 의미있는 설계를 지향합니다.
+ 대용량 트래픽의 상황에서 지속적인 서버 성능을 개선하기 위해 노력합니다. 
  > + [사용자가 증가하면 서버를 어떻게 확장해야할까?](https://blog.naver.com/ghdalswl77/222533904073) <br />
  > + [다중 서버 환경에서의 Session 관리](https://blog.naver.com/PostView.naver?blogId=ghdalswl77&Redirect=View&logNo=222764535050&categoryNo=94&isAfterWrite=true&isMrblogPost=false&isHappyBeanLeverage=true&contentLength=25351&isWeeklyDiaryPopupEnabled=false) <br />
+ 반복적인 작업은 자동화하여 개발의 효율을 높이기 위해 노력하였습니다.
  > + [젠킨스 CI/CD 구축](https://blog.naver.com/ghdalswl77/222743600660) <br />

  <br />

## 개발 환경
+ Java, Mac, IntelliJ Ultimate(Educational License), Lombok, Gradle 7.x
  <br />

## 🎩 사용 기술 스택
+ **Spring Boot 2.6.3**
  + Spring Framework에서 클래스패스의 라이브러리를 자동으로 인식하여 설정해주고 내장 서버를 제공하는 등 많은 편의성을 제공하기 때문에 빠른 개발이 가능하다고 생각하여 Spring Boot를 사용했습니다.
+ **Spring Security**
  + 서비스에서 회원 기능을 지원하기 때문에 이에 필수적인 인증, 인가 기능을 적용하기 위해 사용했습니다.
+ **JWT**
  + 토큰 기반 인증을 구현하기 위해 사용하였습니다. Session 방식보다 확장성이 높고, 자원낭비가 덜하다고 생각해 (세션 클러스터링 등) 로그인 방식으로 [JWT](https://blog.naver.com/ghdalswl77/222517833354, "link")를 사용했습니다.
  > + [JWT와 session 기반 인증의 차이점](https://blog.naver.com/ghdalswl77/222713870678) <br />
  > + [Refresh Token 도입의 필요성](http://www.alittlevanilla.kro.kr/)
+ **AWS EC2 배포**
  + 스프링부트 프로젝트와 AWS RDS 연동으로 ['A Little Vanilla'](http://www.alittlevanilla.kro.kr/) 에 웹사이트를 배포했습니다.
+ MySQL 8.0.26
+ Jenkins CI/CD
  + git push가 발생 할 때마다 빌드와 테스트를 자동화 하여 개발 효율성을 높일 수 있도록 젠킨스를 활용하였습니다.
    <br /><br />


## 🕹 API 설계 및 진행상황
### 로그인/회원가입 및 회원 정보
| Feature | Request | API | 설명 | 체크 |
| ------ | -- | -- | -- | ----------- |
| 회원가입 | POST | /member/signup | 일반회원 form DB 전송, 판매자 회원 form DB 전송, 회원가입 중복 방지 | ☑️ |
| 회원가입 | GET/POST | /member | 카카오 소셜 회원가입 | ☑️ |
| 메일인증 | GET | /member/confirm | 이메일 인증, 중복 가입 방지 | ☑️ |
| 정보수정 | GET | /modify | 회원 정보 수정 | ☑️ |
| 정보수정 | PATCH | /modify/{idx} | 닉네임, 아이디, 비밀번호, 성별 등의 정보 수정 | ☑️ |
| 회원탈퇴 | PATCH | /delete/{idx} | status 수정으로 회원 탈퇴 처리 | ☑️ |
| 재가입 | PATCH | /member/signup | 회원 가입 시 기존 회원 가입 이력이 있을 경우, status 변경으로 재가입 |  |
<br />

> 후기 및 개선방향 <br />
+ [Spring Boot + JWT + Security + Security '회원가입/로그인'](https://blog.naver.com/ghdalswl77/222675846877) <br />
+ [Spring Boot 카카오 소셜 로그인 하기 (JWT+OAuth2)](https://blog.naver.com/ghdalswl77/222711444513) <br />
+ [springboot 구글 이메일 gmail 인증 회원가입 구현](https://blog.naver.com/ghdalswl77/222739067045) <br />
+ [언젠가 수많은 사용자가 서비스를 이용한다면, 과연 이 서버가 감당할수 있을까?](https://blog.naver.com/ghdalswl77/222533904073) <br />


<br />


### 🛍 상품
| Feature | Request | API | 설명 |
| ------ | -- | -- | ----------- |
| 상품작성 | POST | /product/create | 상품 1개 작성 |
| 상품조회 | GET | /product/{idx} | 상품 idx를 통한 상품 1개 조회 |
| 상품삭제 | PATCH | /product/delete/{idx} | 상품 idx를 통한 상품 1개 삭제 |
| 상품수정 | PATCH | /product/{idx} | 상품 정보 수정 |
| 상품목록 | GET | /product/list | 상품 목록 조회. 상품명, 판매자, 상품 사진 등 정보 포함. |
| 상품검색 | GET | /product/search | 상품 검색. 가격대, 배송타입, 이미지만 보기 등 정렬 검색 기능. 상품명, 판매자, 상품 사진 등 정보 포함.|
| 상품좋아요 | GET | /product/like/{idx} | 상품 idx를 통한 상품 좋아요/좋아요 취소 기능. |
| 좋아요목록 | GET | /product/likelist | 회원 idx를 통한 상품 좋아요 목록 조회 |
<br />

> 후기 및 개선방향 <br />
+ [검색 기능](https://blog.naver.com/ghdalswl77/222661721733) <br />
+ [Spring Boot 게시글 좋아요](https://blog.naver.com/ghdalswl77/222686567891) <br />
  <br /><br />



### 📝 상품주문
| Feature | Request | API | 설명 |
| ------ | -- | -- | ----------- |
| 상품주문 | POST | /cart/in | 상품 1개 장바구니 담기 |
| 장바구니 취소 | PATCH | /cart/cancel/{idx} | 상품 idx를 통한 상품 장바구니 취소 |
| 장바구니 목록 | GET | /cart/list | 내 장바구니 목록 조회 |
<br />


###  🧺 서랍
['서랍'](https://blog.naver.com/ghdalswl77/222695713878, "link")기능이란?
| Feature | Request | API | 설명 |
| ------ | -- | -- | ----------- |
| 서랍 추가 | POST | /cart/in | 상품 1개 장바구니 담기 |
| 서랍 취소 | PATCH | /cart/cancel/{idx} | 상품 idx를 통한 상품 장바구니 취소 |
| 서랍 목록 | GET | /cart/list | 내 장바구니 목록 조회 |
<br />


### 🛒 장바구니
| Feature | Request | API | 설명 |
| ------ | -- | -- | ----------- |
| 장바구니 추가 | POST | /cart/in | 상품 1개 장바구니 담기 |
| 장바구니 취소 | PATCH | /cart/cancel/{idx} | 상품 idx를 통한 상품 장바구니 취소 |
| 장바구니 목록 | GET | /cart/list | 내 장바구니 목록 조회 |
<br />


### 💳 결제
| Feature | Request | API | 설명 | 체크 |
| ------ | -- | -- | -- | ----------- |
| 결제 | POST | /order/create | 상품 idx를 통한 상품 장바구니 취소 | ☑️ |
| 결제 취소 | GET | /order/cancel/{idx} | 상품 idx를 통한 상품 결제 취소 요청 | |
| 결제 목록 | GET | /order/list | 구매 목록 조회 | ☑️ |
| 결제 검증 | POST | /pay/complete | 상품 결제 금액과 실제 결제된 금액을 비교 후, 일치하면 거래 완료 처리/불일치 시 거래 실패 처리하는 결제 검증 기능. | |
<br />

### 💰 마일리지
| Feature | Request | API | 설명 | 체크 |
| ------ | -- | -- | -- | ----------- |
| 마일리지 적립 | POST | /pay/chargePay | 상품 금액의 n% 금액 마일리지 적립 | |
| 마일리지 사용 | POST | /pay/withdrawPay | 마일리지 사용 | |
| 마일리지 목록 | GET | /pay/list | 구매 목록 조회 | |
| 마일리지 잔액 조회 | GET | /pay/showtotal | 마일리지 잔액 조회 | |
| 마일리지 확인 | GET | /pay/showlist | 마일리지 적립/지출 이력 조회 | |
<br />
<hr />

## ERD 구조
![vnilla_ERD](https://user-images.githubusercontent.com/68539040/171995935-40d5b641-a053-462d-bc11-c3991ba8b9db.png)
<hr />

