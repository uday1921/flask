pipeline {
    agent any
    stages {
        stage("Groovy file test")
        {
            steps {
                script {
                    def approval = load("approval.groovy")
                    approval.approvalStage()
                    echo 'successful....'
                }
            }
        }
    }
 
}
