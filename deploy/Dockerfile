# build
FROM maven:3.6.3-adoptopenjdk-11 as builder
WORKDIR /usr/src/app
COPY . .

RUN mvn clean deploy --settings ./deploy/.m2/settings.xml
