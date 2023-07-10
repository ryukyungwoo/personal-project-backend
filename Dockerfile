# 기본 이미지로 OpenJDK 17-slim 사용
FROM openjdk:17-slim

# JAR_FILE 변수에 빌드된 JAR 파일 경로 설정
ARG JAR_FILE=/build/libs/*.jar

# 빌드된 JAR 파일을 컨테이너의 app.jar로 복사
COPY ${JAR_FILE} app.jar

# wait-for-it 스크립트 다운로드 및 컨테이너에 추가
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /

# wait-for-it 스크립트에 실행 권한 부여
RUN chmod +x /wait-for-it.sh