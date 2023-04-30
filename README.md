# Jenkins CI/CD Pipeline

We have two virtual machines one for development purpose and the other for deployment purpose. We create a pipeline connecting the two machines.

## Tech Stack used:
- Jenkins
- Git
- Python
- Docker

## Purpose:
The changes made in the production application in one virtual machine's container will be reflected onto Jenkins (on another machine's container) with each git push. Jenkins will then re-build the image each time any changes are made to the application, then that image will be pushed to Docker hub.

## Files:
- calc.py: This file is hardcoded to select choice 1 and the inputs are harcoded too. It is used to perform operations like: addition, subtraction, multiplication and division.
- mytest folder: it contains the test case python file. To make sure the tests run on Jenkins, the test python file should have prefix ‘test_’, else you might encounter build failure in running the test cases.
- devcontainer folder: It contains a json file specifying image configuration for the container in VSCode which contains the application.

## Configuring Jenkins :
STEP 1: Write Dockerfile to install docker along with Jenkins in the docker container.
 ![image](https://user-images.githubusercontent.com/107244393/235337523-acba790e-70f3-4c8b-8691-26a3cebe9a55.png)

STEP 2: Navigate to the folder where your dockerfile is and then build the image using command: sudo docker build <image_name> .
 
STEP 3: After successful build of the image, run the container on port 8083, using same docker engine as that of the host also use a created volume for data persistence on Jenkins
-	sudo docker volume create <volume_name>
-	sudo docker run -v /var/run/docker.sock:/var/run/docker.sock -v <volume_name>:jenkins_home -p 8083:8080 --name <container_name> <image_name>
![image](https://user-images.githubusercontent.com/107244393/235337545-5bff5afe-bedf-4a70-9ffe-e6892a82e52a.png)

 
STEP 4: Open your web browser and enter localhost:8083. It will prompt for AdminPassword. Use the password displayed after running the container. If the password is not visible then use command: sudo docker container exec <container_name> sh -c “cat /var/jenkins_home/secrets/initialAdminPassword”.
 
STEP 5: Install the suggested Plugins.
 
STEP 6: Create Admin User by entering user details.
 
STEP 7: Click on create a job.
 
STEP 8: Name the project and select type of project as Freestyle Project.
 
STEP 9: Click on Dashboard > Manage Jenkins > Available Plugins. Install Plugins named:
•	CloudBees Docker Build and Publish
•	Docker 
•	docker-build-step
Restart Jenkins after these plugins are installed.
 
STEP 10: Click on your account> Credentials. Add your Github and DockerHub credentials in it. Make sure the password entered is the access token generated in both Github and DockerHub respectively.

STEP 11: Navigate to terminal. 
-	sudo docker exec -it -u root <container_name> /bin/bash
-	apt-get install python3
-	apt-get install python3-pip
-	docker login

 
After entering docker login command, you will be prompted for username and password (Password will not be the access token but the general password that you enter to login to your account). Enter that.
STEP 12: Configure the created job:
•	General: Select Github project and enter your project repository url.
•	Source Code Management: Select Git and enter project’s url.
•	Build Triggers: Select Poll SCM. Enter ‘* * * * * *’ in the Schedule section. This will make sure that Jenkins automatically triggers a build after successful commit of code in Git.
•	Build Steps: Select Execute Shell. Type:
python3 -m pip install -r requirements.txt
python3 calc.py
pytest mytests --junitxml=./xmlReport/output.xml
(Here mytests refer to the folder where my test case python files are stored and a xml report file will be generated named output.xml)
![image](https://user-images.githubusercontent.com/107244393/235337592-916f8b70-9922-47c2-b3e0-c25c02b63e07.png)

 
Click on Add Build Step. Select Docker Build and Publish.
•	In repository name: <docker_hub_username>/<image_name_you_want_to_push>

•	Post Build Actions: Select Publish Junit test result report. In the Test report XMLs: xmlReport/output.xml.
Click on Save.

STEP 13: Click on build now.
![image](https://user-images.githubusercontent.com/107244393/235337609-aaf72771-dd48-4d2f-8004-c64bb957ca20.png)
![image](https://user-images.githubusercontent.com/107244393/235337611-2ad8a872-7469-45a6-98c9-83bc36f28e26.png)
![image](https://user-images.githubusercontent.com/107244393/235337617-8e119753-4dff-4a16-a9ba-d05cb6e407d4.png)

  
STEP 14: You will see that the build is successful, all image is pushed to Docker Hub which can be used by production.
![image](https://user-images.githubusercontent.com/107244393/235337620-8e59020a-371b-45fa-aa7e-82f5534e1553.png)

STEP 15: In Status section: you can see the test report and test status.
 ![image](https://user-images.githubusercontent.com/107244393/235337623-ebb1350d-89ce-48c7-8a49-41008d0fc806.png)

STEP 16: You can also try pushing a code change in github repository and you will see that the Jenkins build is automatically triggered and the docker image is rebuilt and updated in DockerHub.

## ISSUES YOU MAY ENCOUNTER:
1.	Invalid Credentials, build failure in Jenkins.
	Make sure the password you gave in the Dashboard is the access token.

2.	Failed to push image to Docker Hub due to http request deny.
	Navigate to the /bin/bash of container using command: sudo docker exec -it -u root <container_name> /bin/bash. 
Enter commands:
o	apt-get install vim
o	vim /etc/resolv.conf
Once the file is opened, add lines:
nameserver 8.8.4.4
nameserver 8.8.8.8

3.	Docker not found error when running the container by directly pulling Jenkins image.
	Use dockerfile to install both Jenkins and docker.

4.	Make sure the test file name written in your test folder has prefix ‘test_’, else you might encounter build failure in running the test cases.

5.	If the given Docker file do not work to install docker and Jenkins, try using this instead:

FROM jenkins:jenkins:lts
USER root
RUN curl -sSL https://get.docker.com/ |sh
USER jenkins

6.	Sometimes the easiest way to debug an error you encounter like, unable to run a dockerfile or unable to start Jenkins on localhost, might simply be solved by either restarting your Virtual Machine, changing your internet connection or restarting docker service by using command: sudo systemctl start docker.

7.	If you see the error of permission denied, it may simply be solved by adding sudo in front of the command.

8.	sudo git push may give fatal error even after entering the correct credentials (access token as password), try git push instead.






