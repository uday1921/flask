 import org.ini4j.Ini

pipeline {
  agent any
  environment {
    BITBUCKET_URL = Cred("bitbucket","url").toString().trim()
    dockerImage1 = ''
    dokcerImage2 = ''
    dockerImage3 = ''
    dockerImage4 = ''
    admin_dockerfile = 'Dockerfiles/admin_manager_Dockerfile'
    template_dockerfile = 'Dockerfiles/template_manager_Dockerfile'
    projects_dockerfile = 'Dockerfiles/projects_manager_Dockerfile'
    workflow_dockerfile = 'Dockerfiles/workflow_manager_Dockerfile'
    registry1 = "admin-manager-pipeline:2.0.${BUILD_NUMBER}"
    registry2 = "project-manager-pipeline:2.0.${BUILD_NUMBER}"
    registry3 = "template-manager-pipeline:2.0.${BUILD_NUMBER}"
    registry4 = "workflow-manager-pipeline:2.0.${BUILD_NUMBER}"
    DEVELOPERGRP = Cred("ldap","developergroup")
    ADMINGROUP = Cred("ldap","admingroup")
    JOB_NAME = "${env.JOB_NAME}"
    BRANCH_NAME = "${env.BRANCH_NAME}"
    BUILD_NUMBER = "${env.BUILD_NUMBER}"
    INITIALIZE_APPROVAL_STATUS = 'status'
    DEV_APPROVAL_STATUS = 'status'
    FINAL_APPROVAL_STATUS = 'status'
    CODE_REVIEW_APPROVAL_STATUS='status'
    LDAP_URL = Cred("ldap","url").toString().trim()
    userinfo="null"
    
  }

  stages {
     stage('code Review Stage'){
	steps {
		script {
			echo "${LDAP_URL}"
			echo "${DEVELOPERGRP}"
			echo "${ADMINGROUP}"
			echo "${BITBUCKET_URL}"
			echo "code review stage is starting"
			//def codereview = load("${JENKINS_HOME}/workspace/GroovyScripts/code.groovy")
			//codereview.codereviewstage()	
			}
		}
		post {
			always {
					echo "completed code review stage"
				}
			aborted {
				script {
					echo "code review stage is aborted by ${userinfo}"
					CODE_REVIEW_APPROVAL_STATUS='ABORTED'
					echo "Aborted pipeline build at code review stage"
					}
				}
			success {
				script {
					 echo "code review  stage is approved by ${userinfo}"
           					  CODE_REVIEW_APPROVAL_STATUS  = 'APPROVED'
					}
				}

			}
	}
    
  }
 
}
def Cred(key,value) {
	def ldapCreds = new Ini(new FileInputStream("${JENKINS_HOME}/workspace/allcreds.cnf.txt"));
   	 return ldapCreds.get(key, value)
}
