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
                    def userAborted = false
                    emailext body: '''
                    please goto console outout of ${BUILD_URl} input to Approve or Reject.<br>
                    ''',
                    mimeType: 'text/html',
                    subject: "[Jenkins] ${job_name} Build Approval Request.",
                    to: "${email}"
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']]

                    try {
                        userInput = input submitter: 'udaykumar', message: 'Do you approve?'
                    }
                    catch (e) {
                        def urrentAborter = e.getCauses()[0].getUser().toString()
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
}
