FROM python:3.8-slim-buster
WORKDIR /Code/
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
COPY . .
CMD ["python","calc.py"]



# FROM openjdk:8
# WORKDIR /Code/
# ADD demo.java /Code/
# RUN ["javac","demo.java"]
# ENTRYPOINT ["java","demo"]

# jenkins will build this image, you don't have to do it manually