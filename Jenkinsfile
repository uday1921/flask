pipeline {
    agent any
    stages {
        stage("Groovy file test")
        {
            steps {
                script {
                    
                    def path = JENKINS_HOME.replace("\\","/")
                    def approval = load("${path}/workspacr/Groovyscripts/test.groovy")
                    approval.check()
                }
            }
        }
    }
 
}
