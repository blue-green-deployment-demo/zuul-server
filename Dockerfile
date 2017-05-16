FROM ewolff/docker-java
ADD target/zuul-0.0.1-SNAPSHOT.jar .
RUN sh -c 'touch zuul-0.0.1-SNAPSHOT.jar'
CMD /usr/bin/java -Xmx600m -Xms600m -jar zuul-0.0.1-SNAPSHOT.jar
ENTRYPOINT /usr/bin/java -Xmx600m -Xms600m -jar zuul-0.0.1-SNAPSHOT.jar
EXPOSE 8080
EXPOSE 80

