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
          echo "proceeding to user deciion..."
               }
      }
      post {
        always {
        script {
          def email = "udaykumar.gorrepati123@gmail.com"
          emailtext (
            to: "${email}",
            subject: "[jenkins] ${env.JOB_NAME} Build Approval Request..",
            body: "please goto to console output of ${env.BUILD_URL} to approve or reject",
          
          recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
        }
    }
    }

    }
    }
