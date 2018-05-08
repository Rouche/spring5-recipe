FROM centos

RUN yum install -y java-1.8.0-openjdk*

VOLUME /tmp

ADD maven/recipe-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

# Just to change the date for static cached stuff to reload
RUN sh -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
