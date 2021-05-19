pipeline {
    agent any
    environment {
        def job_name = currentBuild.fullDisplayName
    }
    stages {
        stage('BUILD'){
            echo "BUILDING DONE";
        }
        stage {
            steps {
                def email = "udaykumar.gorrepati123@gmail.com"
                def userAborted = false
                emailext body: '''
                please goto console outout of ${BUILD_URl} input to Approve or Reject.<br>
                ''',
                mimeType: 'text/html',
                subject: "[Jenkins] ${job_name} Build Approval Request.",
                to: "${email}"
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]

                try {
                    userInput = input submitter: 'vagrant', message: 'Do you approve?'
                }
                catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {
                    cause = e.causes.get(0)
                    echo "Aborted by  "+ cause.getUser.toString()
                    userAborted = true
                    echo "System Aborted but it looks like timeout Period Didn't Compllete. Aborting......."
                }
                if(userAborted) {
                    currentBuild.result = 'ABORTED'
                }
                else {
                    echo "continue to  next stage in piprline."
                }
            }
        }
    }
}
