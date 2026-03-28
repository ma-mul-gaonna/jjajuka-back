# Docker 실행 가이드

jjajuka-back Spring Boot 애플리케이션을 Docker로 실행하는 방법입니다.

## 빠른 시작

### 1. Docker Compose로 실행 (MySQL 포함)

```bash
# 컨테이너 시작
docker compose up -d

# 로그 확인
docker compose logs -f app

# 애플리케이션 접속
curl http://localhost:8080/actuator/health
```

### 2. 중지 및 정리

```bash
# 컨테이너 중지
docker compose stop

# 컨테이너 삭제 (데이터 유지)
docker compose down

# 컨테이너 + 데이터 완전 삭제
docker compose down -v
```

---

## 상세 가이드

### 프로젝트 구성

- **애플리케이션**: Spring Boot 4.0.5, Java 21
- **데이터베이스**: MySQL 8.0
- **빌드 도구**: Gradle 9.4.0

### 생성되는 컨테이너

| 이름 | 포트 | 역할 |
|------|------|------|
| jjajuka-app | 8080 | Spring Boot 애플리케이션 |
| jjajuka-mysql | 3306 | MySQL 데이터베이스 |

### MySQL 접속 정보

```bash
Host: localhost
Port: 3306
Database: jjajuka
Username: jjajuka
Password: jjajuka_2026
Root Password: root
```

### MySQL 직접 접속

```bash
# 컨테이너 내부에서 접속
docker exec -it jjajuka-mysql mysql -u jjajuka -pjjajuka_2026 jjajuka

# 테이블 확인
docker exec -it jjajuka-mysql mysql -u jjajuka -pjjajuka_2026 jjajuka -e "SHOW TABLES;"

# Root로 접속
docker exec -it jjajuka-mysql mysql -u root -proot
```

---

## 코드 변경 시 재빌드

```bash
# 이미지 재빌드 후 재시작
docker compose up -d --build

# 또는
docker compose build
docker compose up -d
```

---

## 유용한 명령어

### 로그 확인
```bash
# 전체 로그
docker compose logs -f

# 애플리케이션 로그만
docker compose logs -f app

# MySQL 로그만
docker compose logs -f mysql
```

### 컨테이너 상태 확인
```bash
docker compose ps
```

### 컨테이너 재시작
```bash
# 전체 재시작
docker compose restart

# 애플리케이션만 재시작
docker compose restart app
```

---

## 환경 변수 (docker-compose.yml)

주요 설정을 변경하려면 `docker-compose.yml` 파일의 `environment` 섹션을 수정하세요.

```yaml
environment:
  # 데이터베이스 연결
  SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/jjajuka
  SPRING_DATASOURCE_USERNAME: jjajuka
  SPRING_DATASOURCE_PASSWORD: jjajuka_2026

  # JPA 설정
  SPRING_JPA_HIBERNATE_DDL_AUTO: update  # create, update, validate
  SPRING_JPA_SHOW_SQL: "true"            # SQL 로그 출력
```

### DDL 모드 설명

- **`create`**: 시작 시 기존 테이블 삭제 후 재생성 (데이터 손실)
- **`update`**: 테이블 없으면 생성, 있으면 스키마 업데이트 (개발용 권장)
- **`validate`**: 스키마 검증만 수행 (프로덕션 권장)
- **`none`**: 아무것도 하지 않음

---

## 트러블슈팅

### 1. 포트가 이미 사용 중인 경우

```bash
# 8080 포트 사용 중인 프로세스 확인
lsof -i :8080

# 3306 포트 사용 중인 프로세스 확인
lsof -i :3306

# docker-compose.yml에서 포트 변경
ports:
  - "8081:8080"  # 로컬:컨테이너
```

### 2. 애플리케이션이 시작되지 않을 때

```bash
# 로그 확인
docker compose logs app

# MySQL이 준비될 때까지 대기 (이미 설정됨)
# docker-compose.yml의 depends_on 확인
```

### 3. 테이블이 생성되지 않을 때

```bash
# 환경 변수 확인
docker compose exec app env | grep JPA

# DDL 모드를 create로 변경 후 재시작
# docker-compose.yml에서:
# SPRING_JPA_HIBERNATE_DDL_AUTO: create
docker compose down -v
docker compose up -d
```

### 4. 데이터 초기화

```bash
# 모든 데이터 삭제
docker compose down -v

# 재시작
docker compose up -d
```

---

## AWS 배포 (프로덕션)

프로덕션 배포를 위해서는 다음 파일을 참고하세요:
- `application-prod.properties`: 프로덕션 환경 설정
- `docker-compose.rds.yml`: AWS RDS 연결용
- `scripts/connect-rds.sh`: SSM 포트 포워딩

### ECR에 이미지 푸시

```bash
# ECR 로그인
aws ecr get-login-password --region ap-northeast-2 | \
  docker login --username AWS --password-stdin <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com

# 이미지 빌드 및 태그
docker build --platform linux/amd64 -t jjajuka-back:latest .
docker tag jjajuka-back:latest <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com/jjajuka-back:latest

# 푸시
docker push <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com/jjajuka-back:latest
```

---

## Health Check

애플리케이션이 정상 동작 중인지 확인:

```bash
# Health check 엔드포인트
curl http://localhost:8080/actuator/health

# 예상 응답
{"status":"UP"}
```

---

## 참고

- Spring Boot 버전: 4.0.5
- Java 버전: 21
- MySQL 버전: 8.0
- Gradle 버전: 9.4.0

더 자세한 내용은 프로젝트 루트의 `docker-compose.yml`과 `Dockerfile`을 참고하세요.
