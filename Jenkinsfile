import org.jenkinsci.plugins.workflow.support.steps.input.Rejection.*;

pipeline {
    agent any
    environment {
        def job_name = "${JOB_NAME}"
    }
    stages {
        stage('BUILD'){
            steps {
                script{
                    echo "BUILDING DONE";
                }
            }
        }
        stage('Testing for mail Approval') {
            steps {
                script{
                    def email = "udaykumar.gorrepati123@gmail.com"
                    def userApproved = false
                    emailext body: '''
                    please goto console outout of ${BUILD_URl} input to Approve or Reject.<br>
                    ''',
                    mimeType: 'text/html',
                    subject: "[Jenkins] ${job_name} Build Approval Request.",
                    to: "${email}"
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']]

                    try {
                        userInput = input submitter: 'udaykumar', message: 'Do you approve?'
                        userApproved = true
                    }
                     finally {
                         if(!userApproved) {
                            //def urrentAborter = e.getCauses()[0].getUser().toString()
                            //echo "Aborted by  "+ cause.getUser.toString()
                            def userAborted = true
                            echo "System Aborted but it looks like timeout Period Didn't Compllete. Aborting......."
                         }
                    }
                    if(!userApproved) {
                        currentBuild.result = 'ABORTED'
                    }
                    else {
                         currentBuild.result = 'SUCCESS'
                    }
                }
            }
        }
        stage("after aborting test")
        {
            steps
            {
                script
                {
                    echo "uday kumar gorrepati"
                }
            }
        }
    }
}
