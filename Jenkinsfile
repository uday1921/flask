pipeline {
    agent any

    stages {
        stage ('First') {
            agent any
            steps {
                echo "First dummy stage"
            }
        }
        stage ('Input') {
            agent any
            steps {
                script {

                    def email = "udaykumar.gorrepati123@gmil.com"
                    emailext body: ''' Please goto the The URl ${BUILD_URL} to Approve or Abort the Deployment of containers to cluster..<br><br>
                                    Remain in page to Confirm To delete cluster or not.<br>
                                    ''',
                            mimeType: 'text/html',
                            subject: "[Jenkins] ${env.JOB_NAME} to approve or Reject Deployment Stage...",
                            to: "${email}"
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]

                    myStage = input message: 'What stage do you want to deploy COntainers to cluster?', parameters: [choice(choices: 'YES\nNo', description: '', name: 'Stage')]


                }
                if(myStage==None)
                {
                    echo 'Skipping Deployment and Deleting Cluster Stage.'
                }
            }
        }

        stage('Deployment') {
            when {
                expression { myStage == 'Yes' }
            }
            steps {
                echo "Running Stage1"
            }
        }

        stage('Deleting the cluster') {
            when {
                expression { myStage == 'Yes' }
            }
            steps {
                echo "Deleting Cluster"
            }
        }

    }
}
