FROM openjdk:8-jre-alpine
MAINTAINER qcb 373413704@qq.com
RUN ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
COPY ./target/springboot-apollo.jar /springboot-apollo/springboot-apollo.jar
EXPOSE 8761
ENV JAVA_OPTS="-Duser.timezone=GMT+08"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /springboot-apollo/springboot-apollo.jar"]