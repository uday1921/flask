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
          env.TAG_ON_DOCKER_HUB = input message: 'User input required',
              parameters: [choice(name: 'Tag on Docker Hub', choices: 'no\nyes', description: 'Choose "yes" if you want to deploy this build')]
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
