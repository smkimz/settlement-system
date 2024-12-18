# 💵 동영상 정산 시스템

## 1. 프로젝트 소개

 동영상 스트리밍 서비스의 동영상 업로더와 광고 업로더에게 조회수에 따라 금액을 정산하는 시스템입니다.

 기본적인 정산 기능과 더불어 각종 통계 기능을 제공하여 동영상 업로더에게 동영상 업로드 작업에서의 편리함을 주기 위한 것이 이 프로젝트의 목적입니다.



## 2. 프로젝트 설명

### (1) 기술 스택

- **IntelliJ Ultimate** : 2024.1.4
- **Java** : Azul Zulu 21.0.5
- **Spring Boot** : 3.3.4
- **Spring Data JPA** : 3.4.0
- **Spring Batch** : 5.1.2
- **Spring Security** : 6.3.4
- **MySQL** : 8.0.40
- **Docker Desktop** : 4.34.3



### (2) 아키텍처

(현재 작성중입니다.)



### (3) ERD

![erd](https://github.com/smkimz/settlement-system/blob/main/readme/erd.png?raw=true)



## 3. 주요 특징

#### (1) Spring Batch를 이용한 정산 및 통계 기능

- 스케쥴링 방식을 이용하여 특정 시간마다 업로더에게 동영상과 광고의 조회수에 따라 정산을 실시하도록 하였고, 통계 데이터를 제공하도록 하였습니다.
- 최근에 올라온 동영상들에 대해 1일, 1주일, 1달 동안 올라온 동영상들 중 조회수가 높은 동영상과 재생 시간이 긴 동영상 TOP N개를 추출하여 통계로 제공하는 기능을 구현하였습니다.

#### (2) CQRS 패턴을 적용하여 다중 DB 구축

- 데이터들을 저장하는 DB를 Command DB와 Query DB로 나누어 읽기 작업과 쓰기 작업을 서로 다른 DB에서 실행하도록 하였습니다.
  - Command DB와 Query DB로 나눔으로써 부하를 분산시키는 효과가 있었습니다.

#### (3) Microservices Architecture

- Monolithic Architecture에서 Microservices Architecture로 변경하였고, 기능에 따라 하나의 모듈을 여러 모듈로 분리하였습니다.
  - 여러 모듈로 분리함에 따라 개발 및 유지보수성을 향상시켰으며, 특정 하나의 기술에만 의존하지 않도록 하였습니다.



## 4. 성능 최적화

(현재 작성중입니다.)



