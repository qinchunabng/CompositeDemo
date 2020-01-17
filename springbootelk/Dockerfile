FROM openjdk:jdk

MAINTAINER qcb

RUN ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /log-service && cd /log-service

WORKDIR /log-service

EXPOSE 8010

ADD ./target/springboot-elk.jar ./springboot-elk.jar

ENV JAVA_OPTS="-Duser.timezone=GMT+08"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /log-service/springboot-elk.jar" ]