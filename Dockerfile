FROM openjdk:8
WORKDIR /Code/
ADD demo.java /Code/
RUN ["javac","demo.java"]
ENTRYPOINT ["java","demo"]

# jenkins will build this image, you don't have to do it manually