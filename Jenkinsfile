pipeline {
    agent any
    stages {
        stage("Groovy file test")
        {
            steps {
                script {
                    
                    echo "${JENKINS_URL}"
                    def path = JENKINS_HOME.replace("\\","/")
                    def approval = load("${JENKINS_HOME}/workspace/GroovyScripts/test.groovy")
                    approval.check()
                }
            }
        }
    }
 
}
