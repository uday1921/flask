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
    USER_INFO='null'
    
  }

  stages {
	  
	  
	
	  
	  
     stage('code Review Stage'){
	steps {
		script {
			echo "${LDAP_URL}"
			echo "${DEVELOPERGRP}"
			echo "${ADMINGROUP}"
			echo "${BITBUCKET_URL}"
			echo "${JENKINS_URL}blue/organizations/jenkins/${env.JOB_NAME}/detail/${env.BRANCH_NAME}/${env.BUILD_NUMBER}/pipeline"
			echo "code review stage is starting"
			def codereview = load("${JENKINS_HOME}/workspace/GroovyScripts/code.groovy")
			codereview.codereviewstage()	
			}
		}
		post {
			always {
					echo "completed code review stage"
				}
			aborted {
				script {
					 wrap([$class: 'BuildUser']) {
         					 echo "${BUILD_USER}"'
       					 }
					echo "code review stage is aborted by ${USER_INFO}"
					CODE_REVIEW_APPROVAL_STATUS='ABORTED'
					echo "${CODE_REVIEW_APPROVAL_STATUS}"
					currentBuild.result = 'ABORTED'
					echo "Aborted pipeline build at code review stage"
					}
				}
			success {
				script {
					 echo "code review  stage is approved by ${USER_INFO}"
           					  CODE_REVIEW_APPROVAL_STATUS  = 'APPROVED'
						echo "${CODE_REVIEW_APPROVAL_STATUS}"
					}
				}

			}
	}
	 
	stage("Initialization Stage") {
      steps {
        script {
          echo "Initialization stage is starting."
          def Intialization = load("${JENKINS_HOME}/workspace/GroovyScripts/init.groovy")
           Intialization.intializationstage()
        }
      }
      post {
        always {
          echo "completed intialization process.."
        }
        aborted {
          script {
            echo "Intialization stage is aborted by ${USER_INFO}"
            INITIALIZE_APPROVAL_STATUS  = 'ABORTED'
		  echo "${INITIALIZE_APPROVAL_STATUS}"
            	  echo "Aborted pipeline build at intialization stage"
          }
        }
        success {
          script {
            echo "intialization stage is approved by ${USER_INFO}"
            INITIALIZE_APPROVAL_STATUS  = 'APPROVED'
	    echo "${INITIALIZE_APPROVAL_STATUS}"
          }
        }
      }
    }
	 
	   stage('creation of Images') {
      steps {
        script {
          echo "creating images."
          echo "images creation is done."
        }
      }
    }
    stage('Tagging Images to repository.') {
      steps {
        script {
          echo "Tagging Images to repository."
         
          echo "Tagging Images is Done."
        }
      }
    }
    stage('pushing images to jfrog') {
      steps {
        script {
          echo "pushing images to Jfrog Artifactory."
          echo "Pushing images to Artifactory Done."
        }
      }
    }
	  
	stage('Deploy Images to Dev Environment') {
      steps {
        script {
           	if (INITIALIZE_APPROVAL_STATUS != 'APPROVED') {                        
		echo "intialization approval stage is not approved hence images not deployed to Dev environment"
		}
	else {
		echo "Deploy in dev and  environment.."
		}
        }

      }
    }
	  
	  
	  
	  
	  stage('Dev Approval stage') {
	steps {
		script {
			echo 'Dev approval stae is starting'
			def devapproval = load("${JENKINS_HOME}/workspace/GroovyScripts/dev.groovy")
			devapproval.devApprovalstage()	
			}
		}
		post {
			always {
				echo"completed DEV approval process post"
				}
			aborted {
				script {
					echo "DEV approval stage is aborted by ${USER_INFO}"
					DEV_APPROVAL_STATUS  = 'ABORTED'
                 			echo "Aborted pipeline build at DEV Approval stage"
					}
				}
			success {
				script {
					 echo "DEV approval stage is approved by ${USER_INFO}."
                 					 DEV_APPROVAL_STATUS  = 'APPROVED'
                  					echo "Completed DEV approval stage"
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
