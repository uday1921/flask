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
          echo "Docker Image Build done........"
    }
      }
    }
    stage('mannuel approval to deploy p]images to kubernetes') {
      steps {
        script {
           input message: 'User input required'
          echo "uday kumar......"
        }
    }
    }
    }
  }
