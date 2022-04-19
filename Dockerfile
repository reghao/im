FROM debian11/openjdk11

WORKDIR /app
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
COPY target/im.jar /app/im.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/im.jar"]