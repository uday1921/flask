pipeline {
    agent any

    stages {
        stage ('First') {
            agent any
            steps {
                echo "First dummy stage"
            }
        }
        stage ('Deployment') {
            agent any
            steps {
                script {

                    def email = "udaykumar.gorrepati123@gmail.com"
                    emailext body: ''' Please goto the The URl ${BUILD_URL} to approve or Reject  Deletion of kube Cluster on AKS.<br><br>
                                    ''',
                            mimeType: 'text/html',
                            subject: "[Jenkins] ${env.JOB_NAME} to approve or Reject  Deletion of kube Cluster on AKS ...",
                            to: "${email}"
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]

                    myStage = input message: 'What stage do you want to Delete Kube cluster?', parameters: [choice(choices: 'YES\nNo', description: '', name: 'Stage')]
                    if(myStage == 'No') {
                        echo 'Skipping Deleting kube Cluster Stage.'
                        def reasonforAbortion = input(message:"Reason for Abortion", parameters: [[$class: 'TextParameterDefinition', defaultValue: 'Your Reason', description: 'Reason for Rejection',name: 'aText']], ok: 'Go Ahead ');
                        currentBuild.result = 'ABORTED'
                    }
                 }    
            }
        }

        stage('Deleting the cluster') {
            when {
                expression { myStage == 'YES' }
            }
            steps {
                echo "Deleting Cluster"
            }
        }

    }
}
