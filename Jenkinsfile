pipeline {
  agent any
  environment {
    dockerImage = ''
    registry = '1781043/docker-jenkins'
  }
  stages {
    stage('Build Docker Image') {
      dockerImage =  docker.build registry
    }
  }
}
