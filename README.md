🍦 A Little Vanilla
================

> 👉🏻 Summary <br />
> 이 프로젝트는 스프링부트와 리액트로 회원가입, 로그인, 카카오로그인, 상품 등록/조회/수정/삭제/좋아요/장바구니/구매 등 <br />
> 다양한 쇼핑몰 서비스를 제공하기 위해 만들어졌습니다. <br />
> 현재 [I am port](https://www.iamport.kr/?gclid=CjwKCAjws8yUBhA1EiwAi_tpEawr0NbpwdG_4bW9KRPVRXXVEdwQ32yO8SyXEhMBfxiqHIml3c8uxhoC2nYQAvD_BwE, "Iamport link") 결제 API를 연동하여 실제 거래까지 이루어지지만, 현재 배포된 사이트에서는 당일 자정에 모두 환불됩니다.<br />
> 안심하고 테스트 해보세요!




## 사용 기술 스택
### Backend
+ Spring Boot 2.6.3
  + Spring Framework에서 클래스패스의 라이브러리를 자동으로 인식하여 설정해주고 내장 서버를 제공하는 등 많은 편의성을 제공하기 때문에 빠른 개발이 가능하다고 생각하여 Spring Boot를 사용했습니다.
+ Spring Security
  + 서비스에서 회원 기능을 지원하기 때문에 이에 필수적인 인증, 인가 기능을 적용하기 위해 사용했습니다.
  + [JWT]([https://www.iamport.kr/?gclid=CjwKCAjws8yUBhA1EiwAi_tpEawr0NbpwdG_4bW9KRPVRXXVEdwQ32yO8SyXEhMBfxiqHIml3c8uxhoC2nYQAvD_BwE](https://blog.naver.com/ghdalswl77/222517833354), "link") 
  + Session 방식보다 확장성이 높고, 자원낭비가 덜하다고 생각해 (세션 클러스터링 등) 로그인 방식으로 JWT를 사용했습니다.
+ MySQL 8.0.26
  + 구현할 프로젝트가 규모가 큰 서비스가 아니며, 문자열 비교에서 대소 문자를 구분하지 않는다는 장점이 있는 MySQL을 사용했습니다.


### 배포
+ AWS EC2

<hr/>

## API 설계 및 진행상황
### 로그인/회원가입
| Feature | Request | API | 설명 | 체크 |
| ------ | -- | -- | -- | ----------- |
| 회원가입 | GET | / | 회원가입 웹 페이지 | ☑️ |
| 회원가입 | POST | / | user form DB 전송 | ☑️ |
| 회원가입 | POST | / | seller form DB 전송 | ☑️ |
| 로그인 | GET | / | 로그인 페이지 | ☑️ |
| 회원가입 | GET | / | 회원가입 중복 방지 | ☑️ |
| 회원가입 | GET | / | 소셜 회원가입 | ☑️ |
| 로그인 | GET | / | 소셜 회원가입 | ☑️ |


### 로그인/회원가입
| Feature | Request | API | 설명 | 체크 |
| ------ | -- | -- | -- | ----------- |
| 회원가입 | GET | / | 회원가입 웹 페이지 | ☑️ |
| 회원가입 | POST | / | user form DB 전송 | ☑️ |
| 회원가입 | POST | / | seller form DB 전송 | ☑️ |
| 로그인 | GET | / | 로그인 페이지 | ☑️ |
| 회원가입 | GET | / | 회원가입 중복 방지 | ☑️ |
| 회원가입 | GET | / | 소셜 회원가입 | ☑️ |
| 로그인 | GET | / | 소셜 회원가입 | ☑️ |
| 4주차 | GET | / | qwer | ☑️ |
| 5주차 | GET | / |디자인 합동 세미나 | ☑️ |
| 6주차 | GET | / |서버 합동 세미나 + 솝커톤  | ☑️ |
| 7주차 | GET | / |클론 코딩을 통한 실전 UI 구성, Animation, 통신 보충  | ☑️ |
| 8주차 | GET | / | e기획 경선 + 앱잼 전 보충 세미나 + 앱스토어 배포 가이드  | ☑️ |
