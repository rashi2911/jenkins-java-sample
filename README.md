# jenkins-java-sample

We have two virtual machines one for development purposes and the other for deployment purposes. We create a pipeline connecting the two instances.

## Tech Stack used:
> Jenkins
> Git
> Java
> Docker

## Purpose:
The changes made in the production application in one virtual machine will be reflected onto Jenkins with each git commit. Jenkins will then re-build the image each time any changes are made to the application, then that image will be pushed to Docker hub.

