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
    LDAP_DN = Cred("ldap","groupdn").toString().trim()
    instance="null"
    
  }

  stages {
     stage('code Review Stage'){
	steps {
		script {
			echo "code review stage is starting"
			def codereview = load("${JENKINS_HOME}/workspace/GroovyScripts/codereviewapproval.groovy")
			codereview.codereviewstage()	
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
    
    stage("Initialization Stage") {
      steps {
        script {
          echo "Initialization stage is starting."
          def Intialization = load("${JENKINS_HOME}/workspace/GroovyScripts/intialization.groovy")
           Intialization.intializationstage()
        }
      }
      post {
        always {
          echo "completed intialization process.."
        }
        aborted {
          script {
            echo "Intialization stage is aborted by ${userinfo}"
            INITIALIZE_APPROVAL_STATUS  = 'ABORTED'
            echo "Aborted pipeline build at intialization stage"
          }
        }
        success {
          script {
            echo "intialization stage is approved by ${userinfo}"
            INITIALIZE_APPROVAL_STATUS  = 'APPROVED'
          }
        }
      }
    }
    stage('creation of Images') {
      steps {
        script {
          echo "creating images."
          dockerImage1 = docker.build(registry1, "-f ${admin_dockerfile} .")
          dockerImage2 = docker.build(registry2, "-f ${projects_dockerfile} .")
          dockerImage3 = docker.build(registry3, "-f ${template_dockerfile} .")
          dockerImage4 = docker.build(registry4, "-f ${workflow_dockerfile} .")
          echo "images creation is done."
        }
      }
    }
    stage('Tagging Images to repository.') {
      steps {
        script {
          echo "Tagging Images to repository."
          sh "sudo docker tag ${registry1} docker.mot-solutions.com/msi/unlok-dev/${registry1}"
          sh "sudo docker tag ${registry2} docker.mot-solutions.com/msi/unlok-dev/${registry2}"
          sh "sudo docker tag ${registry3} docker.mot-solutions.com/msi/unlok-dev/${registry3}"
          sh "sudo docker tag ${registry4} docker.mot-solutions.com/msi/unlok-dev/${registry4}"
          echo "Tagging Images is Done."
        }
      }
    }
    stage('pushing images to jfrog') {
      steps {
        script {
          echo "pushing images to Jfrog Artifactory."
          sh "sudo docker push docker.mot-solutions.com/msi/unlok-dev/${registry1}"
          sh "sudo docker push docker.mot-solutions.com/msi/unlok-dev/${registry2}"
          sh "sudo docker push docker.mot-solutions.com/msi/unlok-dev/${registry3}"
          sh "sudo docker push docker.mot-solutions.com/msi/unlok-dev/${registry4}"
          echo "Pushing images to Artifactory Done."
        }
      }
    }
    stage('Deploy Images to Dev Environment) {
      steps {
        script {
           	if (INITIALIZE_APPROVAL_STATUS != 'APPROVED') {                        
		echo "intialization approval stage is not approved hence images not deployed to Dev environment"
		}
	else {
		echo "Deploy in dev environment.."
		}
        }

      }
    }
stage('Dev Approval stage') {
	steps {
		script {
			echo 'Dev approval stae is starting'
			def devapproval = load("${JENKINS_HOME}/workspace/GroovyScripts/devApproval.groovy")
			devapproval.devApprovalstage()	
			}
		}
		post {
			always {
				echo"completed DEV approval process post"
				}
			aborted {
				script {
					echo "DEV approval stage is aborted by ${userinfo}"
					DEV_APPROVAL_STATUS  = 'ABORTED'
                 					echo "Aborted pipeline build at DEV Approval stage"
					}
				}
			success {
				script {
					 echo "DEV approval stage is approved by ${userinfo}."
                 					 DEV_APPROVAL_STATUS  = 'APPROVED'
                  					echo "Completed DEV approval stage"
					}
				}
			}

}
  }
  post {
    failure {
      script {
        def email_addr = "qhkt64@motorolasolutions.com"

        emailext(
          to: "${email_addr}", subject: "Failed to run the Unlok pipeline on branch ${GIT_BRANCH}",
          body: "Please Check the build job at ${BUILD_URL} ",
          recipientProviders: [
            [$class: 'DevelopersRecipientProvider']
          ]
        )
      }
    }
    success {
      script {
        def email_addr = "qhkt64@motorolasolutions.com"

        emailext(
          to: "${email_addr}", subject: "successfully run the Unlok pipeline on branch ${GIT_BRANCH}",
          body: "Please Check the build job at ${BUILD_URL} ",
          recipientProviders: [
            [$class: 'DevelopersRecipientProvider']
          ]
        )
      }
    }
    aborted {
      script {
        def email_addr = "qhkt64@motorolasolutions.com"

        emailext(
          to: "${email_addr}", subject: "Aborted manually Unlok pipeline by the requested user at the deletion of kube cluster stage...",
          body: "Please Check the build job at ${BUILD_URL} ",
          recipientProviders: [
            [$class: 'DevelopersRecipientProvider']
          ]
        )
      }
    }
    changed {
      echo 'Things were different before...'
    }
    always {
      echo "cleaning up workspace."
      deleteDir()
    }
  }
}
def Cred(key,value){
    def ldapCreds = new Ini(new FileInputStream("${JENKINS_HOME}/workspace/allcreds.cnf.txt"));
    return ldapCreds.get(key, value)
}
