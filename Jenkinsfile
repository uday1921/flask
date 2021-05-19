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
      steps {
      input {
        message "approve stage?"
      }
    }
    }
    stage("display message")
    {
      steps {
      echo "uday kumar......"
    }
    }
    }
  }
