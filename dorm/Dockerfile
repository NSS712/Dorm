FROM openjdk

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/dorm-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m",  "-jar", "/app/dorm-0.0.1-SNAPSHOT.jar"]

EXPOSE 8083