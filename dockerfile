FROM openjdk:8
WORKDIR /Code/
ADD demo.java /Code/
RUN ["javac","demo.java"]
ENTRYPOINT ["java","demo"]