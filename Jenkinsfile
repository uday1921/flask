pipeline {
    agent any
    stages {
        stage("Groovy file test")
        {
            steps {
                script {
                    
                    def path = JENKINS_HOME.replace("\\","/")
                    def approval = load("${JENKINS_HOME}/workspace/GroovyScripts/test.groovy")
                    approval.check()
                    echo "${c}"
                }
            }
        }
    }
 
}
