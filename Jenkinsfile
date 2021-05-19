pipeline {
  agent any
  environment {
    dockerImage = ''
    registry = 'flask'
  }
  stages {
    stage('Build Docker Image') {
      steps {
        script {
      dockerImage =  docker.build registry
          bat "echo ${BUILD_NUMBER}"
    }
      }
    }
    stage('mannuel approval') {
      input "approve stage?"
    }
    stage("display message")
    {
      echo "uday kumar......"
    }
  }
}
