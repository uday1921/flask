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
                    myStage = input message: 'What stage do you want to approve Deployment stage?', parameters: [choice(choices: 'Yes\nNo', description: '', name: 'Stage')]
                }
                echo myStage
            }
        }

        stage('Stage1') {
            when {
                expression { myStage == 'Yes' }
            }
            steps {
                echo "Running deployment"
            }
        }

        stage('Stage2') {
            steps {
                echo "Running Stage2"
            }
        }

        stage('Stage3') {
            steps {
                echo "Running Stage3"
            }
        }

    }
}
