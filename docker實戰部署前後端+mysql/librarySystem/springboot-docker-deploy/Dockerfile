FROM openjdk:8

# 建立了一個目錄 /springboot-docker-deploy 用於存放後續的文件和代碼
RUN mkdir -p /springboot-docker-deploy

# 指令了在接下來的命令中，工作目錄都是 /springboot-docker-deploy
WORKDIR /springboot-docker-deploy

#定義了構建參數 JAR_FILE 並且給了他一個默認值 target/springboot-docker-deploy-1.0-SNAPSHOT.jar
ARG JAR_FILE=target/springboot-docker-deploy-1.0-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

EXPOSE 9090

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xms256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 10; java -jar app.jar $JAVA_OPTS
