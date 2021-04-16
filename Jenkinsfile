pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/uday1921/flask.git']]])
      }
    }

  }
}
