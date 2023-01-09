FROM openjdk

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/dorm-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m",  "-jar", "/app/dorm-0.0.1-SNAPSHOT.jar" ]
# --spring.datasource.url=jdbc:mysql://$ip1:3306/dorm?serverTimezone=UTC --spring.redis.host=$ip1 --spring.rabbitmq.host=$ip2
#ENV ip1="127.0.0.1"
#ENV ip2="127.0.0.1"


EXPOSE 8083