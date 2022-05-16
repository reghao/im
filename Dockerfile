FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.15_10

WORKDIR /app
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
COPY target/im.jar /app/im.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/im.jar"]