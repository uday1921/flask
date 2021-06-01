pipeline {
    agent any
    stages {
        stage("Groovy file test")
        {
            steps {
                script {
                    
                    def path = JENKINS_HOME.replace("\\","/")
                    def approval = load("${path}/workspace/GroovyScripts/test.groovy")
                    sleep 150
                    approval.check()
                }
            }
        }
    }
 
}
