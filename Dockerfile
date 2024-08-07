# 베이스 이미지로 OpenJDK 11을 사용
FROM openjdk:17-jdk-slim

# JAR_FILE 변수를 설정하여 빌드된 JAR 파일을 지정
ARG JAR_FILE=build/libs/*.jar

# JAR 파일과 application-secrets.properties 파일을 컨테이너 내부로 복사
COPY ${JAR_FILE} app.jar
COPY application-secrets.properties application-secrets.properties

# 환경 변수 설정
ENV SPRING_CONFIG_IMPORT=optional:classpath:application-secrets.properties
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/codyus
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=1234
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_PROPERTIES_HIBERNATE_SHOW_SQL=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_USE_SQL_COMMENTS=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect

# 애플리케이션을 실행
ENTRYPOINT ["java","-jar","/app.jar"]