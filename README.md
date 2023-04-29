# Jenkins CI/CD Pipeline

We have two virtual machines one for development purpose and the other for deployment purpose. We create a pipeline connecting the two machines.

## Tech Stack used:
- Jenkins
- Git
- Python
- Docker

## Purpose:
The changes made in the production application in one virtual machine will be reflected onto Jenkins with each git commit. Jenkins will then re-build the image each time any changes are made to the application, then that image will be pushed to Docker hub.

