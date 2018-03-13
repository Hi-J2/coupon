# KAKAOPAY 사전과제

**개발환경**

- Backend
  - JAVA8
  - JPA
  - gradle
- Frontend 
  -  Jquery3 
  -  Bootstrap4 
  	  Datatables	

**Build & Excute**

​	`./gradlew build && java -jar build/libs/coupon-0.0.1.jar`

**Swagger API-Document **

​	URL : http://localhost:8080/api-docs

**ROOT Page **

​	URL : http://localhost:8080/

​	

## 1. Coupon 생성 조건 및 규칙

### 1. 1. 생성 조건 정리

```
1. 쿠폰번호는 16자리여야 한다.
2. [0-9][a-z][A-Z]의 문자를 사용한다.
3. 중복된 쿠폰번호를 발행할 수 없다.
4. 중복된 Email로 쿠폰 발행은 불가
```
### 1. 2. 생성 규칙
```
1. 8자리 임의 코드 생성([0-9][a-z][A-Z])
2. 2자리의 HashCode
3. 6자리의 순서번호 (DB Auto increament 사용)
4. 1, 2, 3번항목의 자리수를 모두 더하여 16자리의 쿠폰코드 생성
```



## 2. 쿠폰번호 생성 방법

### 2. 1. 임의의 문자열 생성

```
1. java.Util.Random Class 사용
	- nextInt(int n) 메서드는 0부터 n-1 사이의 난수 반환
2. nextInt(62)메서드를 통해 반환된 숫자를 62진수로 변환 ([0-9][a-z][A-Z])
3. 1, 2번 항목을 8번 반복하고 문자를 모두 연결하여 8자리의 임의의 문자열생성

Ex) ABCDEFGH
```

### 2. 2. HashCode 생성

```
1. 2.1 항목의 8자리 62코드를 한문자씩 10진수로 변환
2. 10진수로 변환된 값을 모두 더함
3. 모두 더한 값을 62진수 코드로 변환하여 2자리의 HashCode생성

EX) 
1. abcdefg => 10,11,12,13,14,15,16,17
2. 10+11+12+13+14+15+16+17 => 108
3. 108 => 1K
  * 최대로 나올 수 있는 최대 HashCode는 61 * 8 = 488 => 7S 
```

### 2. 3. ID 생성

```
1. Database의 Auto Increament 기능을 이용
2. DB Insert시 순서 대로 생성되는 ID를 6자리의 62진수로 변환
3. 6자리 이하라면 5-자릿수만큼 0으로 채움

EX) 10(a) => 00000a
```
### 2. 4. ID Rotate

```
2.3에서 생생된 6자리의 ID는 순서이기 때문에 유추를 어렵게 하기 위해 HashCode만큼 Rotate시킨다.
EX) ID : 00000a , HashCoe : 3 => 00a000 
```

### 2. 5. 최종 쿠폰값

```
1. 2.1 에서 생성됨 8자리의 문자와 2.3~5에서 생성된 ID를 한 문자씩 번갈아며 사용(뒤에 2자리는 붙임)
	EX) code : abcdefgh, ID : 00a000 => a0b0cad0e0f0gh
2. 2.2에서 생성된 hascode를  생성된 문자열 뒤에 붙여 준다.
	EX) hashcode : 1L => a0b0cad0e0f0gh1L
	
* 생성된 쿠폰번호 : a0b0-cad0-e0f0-gh1L
```

### 2. 5. 정리

```
1. 무작위 값을 이용한 코드 생성 (8자리)
2. HashCode를 넣어 일시적 생성된 쿠폰번호에 대한 검증(2자리) 
3. 쿠폰번호 중복생성 방지를 위한 DB Autoincreament 값 사용(6자리)
```



## 3. API 정보

### 3. 1. 기본정보

- 데이터 타입 : JSON 
- Swagger 문서 잠조 : /api-docs

| 구분    | 내용             | 비고                             |
| :------ | :--------------- | :------------------------------- |
| code    | 응답코드         | 200, 400, 500 등                 |
| message | 메시지           | 성공 : success, 실패 : 실패 사유 |
| data    | 요청에 대한 내용 |                                  |

### 3. 2. API LIST

#### 3.2.1. 쿠폰생성

> URL : /api/coupon/
>
> Method : POST
>

#### Parameter 

| 구분  | Type   | 필수여부 |
| :---- | :----- | :------- |
| email | String | Y        |


#### 3.2.2. 쿠폰리스트

> URL : /api/coupon/findAll
>
> Method : GET
>

#### Parameter 

| 구분  | Type    | 필수여부               |
| :--- | :------ | :--------------------- |
| page | integer | N ( default : 0)       |
| size | integer | N (default : 10)       |
| sort | String  | N (default : id, desc) |

#### 3.2.3. 쿠폰사용

> URL : /api/coupon/use/{couponNumber}
>
> Method : PUT

#### Parameter

| 구분         | Type   | 필수여부 |
| ------------ | ------ | -------- |
| couponNumber | String | Y        |
