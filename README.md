## ♻️ 아비테더 (arbTether) (가제)
### 주제
국내 - 해외간의 가격의 괴리를 찾아 아비트라지 매매를 돕는 사이트
특히, 테더와 같은 안정적인 아비트라지에 초점
### 주요 기능
- 업비트 - 바이낸스간의 모든 코인 `김치프리미엄` 계산
- 사용자가 설정한 괴리율 초과 시 알림 (이메일, 텔레그램 등)
- 테더 수익률 계산
    - 헷징 펀비, 스테이킹, 홀딩 등을 하였을 때, 발생할 수 있는 수익 계산
    - 최근 테더 코인의 가격 움직임을 고려하여, 이익을 최대화 할 수 있는 방법 추천
- 트레이딩뷰 차트 연동 (비트, 테더)

### 기술 스택 (임시)
##### BackEnd
- Spring Boot
- JPA
- QueryDSL
- Spring Batch
- Spring Security
##### Infra
- Jenkins
- Kubernetes
- ArgoCD
- Helm
##### FrontEnd
- Vue.js
- Vuex
- 또는 Spring MVC

### 아키텍처 (임시)
![](https://i.imgur.com/rVQ3cUr.png)

#### 아키텍처 설계 과정
[블로그 링크](https://velog.io/@sonjiseokk/1%EC%95%84%EB%B9%84%ED%84%B0Arbit-er-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%84%A4%EA%B3%84)
### EDA (Event-Driven Architecture) 흐름

### 프로젝트 진행 과정

#### 1️⃣ 아키텍처 설계
[블로그 링크](https://velog.io/@sonjiseokk/1%EC%95%84%EB%B9%84%ED%84%B0Arbit-er-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%84%A4%EA%B3%84)

#### 2️⃣ 프로젝트 구조
[블로그 링크](https://velog.io/@sonjiseokk/2%EC%95%84%EB%B9%84%ED%84%B0Arbit-er-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B5%AC%EC%A1%B0)

#### 3️⃣ 업비트 실시간 시세 연동
[블로그 링크](https://velog.io/@sonjiseokk/2%EC%95%84%EB%B9%84%ED%84%B0Arbit-er-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%97%85%EB%B9%84%ED%8A%B8-%EC%8B%A4%EC%8B%9C%EA%B0%84-%EC%8B%9C%EC%84%B8-%EC%97%B0%EB%8F%99)

#### 4️⃣ 과도한 이벤트 발행 문제해결 (🔥93% 감소)
[블로그 링크](https://velog.io/@sonjiseokk/4%EC%95%84%EB%B9%84%ED%84%B0Arbit-er-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B3%BC%EB%8F%84%ED%95%9C-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EB%B0%9C%ED%96%89-%EB%AC%B8%EC%A0%9C%ED%95%B4%EA%B2%B0-93-%EA%B0%90%EC%86%8C)
