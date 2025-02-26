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
![](https://i.imgur.com/FTC6ebg.png)

### EDA (Event-Driven Architecture) 흐름

### **1️⃣ 실시간 가격 수집 서비스 (Real-Time Price Collector)**

✅ **거래소(WebSocket) → Kafka로 실시간 가격 전송**  
✅ **모든 서비스가 Kafka를 통해 데이터 수신 가능 → 확장성 극대화**  
✅ **여러 개의 Consumer가 동일한 데이터를 받아서 개별 처리 가능**

📌 **데이터 흐름:**
- 업비트 & 바이낸스에서 가격 수집
- `Kafka (price-updates)`에 실시간 가격 데이터 발행

---

### **2️⃣ 집계 서비스 (Aggregation Service)**

✅ **Kafka에서 가격 정보 수신 → 김프 계산**  
✅ **테더(USDT) 가격을 1분마다 업데이트 & 환율 캐싱 (Redis)**  
✅ **프론트엔드에 WebSocket으로 실시간 데이터 전송**  
✅ **최종 가격 & 김프 값을 Kafka로 다시 발행 (`kimp-updates`)**

📌 **데이터 흐름:**

- `Kafka (price-updates)`를 구독하여 가격 데이터 수신
- USDT 환율을 Redis에서 가져와 김프 계산
- 계산된 결과를 `Kafka (kimp-updates)`에 다시 발행
- 동시에 프론트엔드 WebSocket을 통해 실시간 푸시

---

### **3️⃣ 알림 서비스 (Notification Service)**

✅ **각 회원별 김프 임계치 정보를 Redis에 저장**  
✅ **Kafka에서 `kimp-updates` 메시지를 수신 → 임계치와 비교**  
✅ **초과하면 해당 회원에게 알림 전송 (Email, Push, Telegram)**

📌 **데이터 흐름:**

- `Kafka (kimp-updates)`에서 새로운 김프 값 수신
- Redis에 저장된 **회원별 임계치 값과 비교**
- 초과하면 회원에게 **푸시 알림 / SMS / 이메일 전송**