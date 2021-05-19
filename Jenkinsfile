pipeline {
  agent any
  environment {
    dockerImage = ''
    def toemialid = "udaykumar.gorrepati123@gmail.com"
    def fromemialid = "udaykumar.gorrepati123@gmail.com"
    def 
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
          def userAborted = false
          emialtext body: ''' please goto to console output of ${BUILD_URL} to approve or reject.<br>''',
          mimeType: 'text/html',
          subject: "[jenkins] ${env.JOB_NAME} Build Approval Request..",
          from: ${fromemialid},
          to: ${fromemialid},
          recipientProviders: [[$class: 'CuipritsRecipientProvider']]
          
          try {
            userInput = input submitter: 'vagrant', message: 'Do you approve?' 
            
          }
          catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {
            
            cause = e.causes.get(0)
            echo "Aborted by" + cause.getUser().toString()
            userAborted = true
          }
            if(userAborted)
            {
              currentBuild.result = 'ABORTED'
            }
            else
            {
              echo "uday kumar gorrepati123"
            }
          
        }
    }
    }
    }
