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
        script {
          def email = "udaykumar.gorrepati123@gmail.com"
          emialtext (
            to: "${email}"
            body: "please goto to console output of ${env.BUILD_URL} to approve or reject",
          subject: "[jenkins] ${env.JOB_NAME} Build Approval Request..",
          recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
        }

    }
    }
