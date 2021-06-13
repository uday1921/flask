import org.ini4j.Ini

def codereviewstage() 
{
	
	def developers = LDAPUSERS("${DEVELOPERGRP}")
	sendmail()
   	def instance= input( message: "Would you like to initialize the pipeline?", submitterParameter: 'submitter', submitter: "${developers}");
   	echo "${instance.submitter} is submitter "
    	userinfo = "${instance.submitter}"
}

def sendemail() {
    def jobname = "${env.JOB_NAME}".split('/').first()
    def array = jobname.split("/")
    jobname = array[0];
     def branchname = "${env.JOB_NAME}".split('/').last()
    def approvers = LDAPUSERS("${DEVELOPERGRP}")
    def recipents = approvers.replace('[','')
    recipents = "$recipents"
    echo "${recipents}"
    try {
            mail bcc: '',
            subject: "Need Approval for the code review stage of Unlok Application   for the Jenkins Build ${env.BUILD_NUMBER}: Job ${jobname}",
            to: "${recipents}",
            mimeType: 'text/html',
            body: "<b>Need Approval for the code review stage of Unlok Application click here :</b> ${JENKINS_URL}blue/organizations/jenkins/${jobname}/detail/${branchname}/${BUILD_NUMBER}/pipeline",<b>
	     for Code review Goto to the repository URL: ${BITBUCKET_URL}</b> 
           cc: '',
            from: 'Jenkins <noreply@motorolasolutions.com>',
            replyTo: 'no reply'
    }
    catch(error) {
            echo "Error Message: ${error}"
            if (error.toString().indexOf('User Unknown') != -1) {
                    try {
                            def errmessage = ((error.toString()).replace("<","(")).replace(">",")")
                            mail bcc: '',
                            subject: "Send email from Jenkins pipeline is not successful",
                            to: "${recipents}",
                            mimeType: 'text/html',
                            body: "Send email from Jenkins pipeline is not successful for Unlok Application.<br> <br> Error Message: ${errmessage}",
                            cc: '',
                            from: 'Jenkins <noreply@motorolasolutions.com>',
                            replyTo: 'no reply'
                    }
                    catch(err) {
                            echo "Error Message: ${err}"
                    }
            }
            else {
                    currentBuild.result = 'ABORTED'
                    error('Aborting the process')
            }
    }
}

def LDAPUSERS(group) {
    def ldapUsername =  Cred("ldap", "user").toString().trim()
    def ldapPassword = Cred("ldap", "password").toString().trim()
    def ldapUrl = Cred("ldap", "url").toString().trim()
    def ldapGroupDn = Cred("ldap", "groupdn").toString().trim()
    def ldapEntryFile = "${JENKINS_HOME}/workspace/output.txt"
    
    // LDAP group details fetching
    def ldapAuth = "${ldapUsername}"+":"+"${ldapPassword}"
    ldapUrl = "${ldapUrl}" + 'cn='+ "${group}" +"${ldapGroupDn}" 
    def uersListLDAPScript = 'curl --user '+ "\"${ldapAuth}\"" +' '+ "${ldapUrl}" +' -o '+ "${ldapEntryFile}"+' '
    sh (script: "${uersListLDAPScript}" , returnStdout: true).toString().trim().toLowerCase()
    def ldapSearch = new File("${ldapEntryFile}") as String[]
    def ldapArr = []
    def grepUsrId = 'null'
    ldapSearch.each { item ->
            if(item.contains('uniquemember: motguid=')) {
                    grepUsrId = "${item}".substring(item.indexOf(" "))
              		def val = "${grepUsrId}".indexOf("=")+1
              		def val2 =  "${grepUsrId}".substring(val,"${grepUsrId}".indexOf(",") )
                    ldapArr.push("${grepUsrId}".substring("${grepUsrId}".indexOf("=")+1,"${grepUsrId}".indexOf(",")))
            }
    }
    def tempLdapArr = "${ldapArr}".toLowerCase().replace('[','')
    def formatLdapArr = "${tempLdapArr}".replace(']','')
    def finalSubmitter = "${formatLdapArr}".replaceAll("\\s","")
    return finalSubmitter
}


def Cred(key,value){
    def ldapCreds = new Ini(new FileInputStream("${JENKINS_HOME}/workspace/allcreds.cnf.txt"));
    return ldapCreds.get(key, value)
}
return this
