# WildFly 8 on Docker with Centos 7 and OpenJDK 1.7
FROM jboss/wildfly:latest

ADD target/kwetter-user-service.war /opt/wildy/deployment

# Expose http and admin ports
EXPOSE 8080 9990