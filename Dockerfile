FROM maven:3.8-openjdk-18

WORKDIR /parking-app
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run