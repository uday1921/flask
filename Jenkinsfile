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
    stage('mannuel approval') {
      steps {
        script {
           input message: 'User input required'
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
