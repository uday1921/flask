import org.ini4j.Ini

def devApprovalstage() 
{
    
    
        def approvers = LDAPUSERS("${ADMINGRP}")
        def recipents = approvers.replace('[','')
        def adminapprovers = LDAPUSERS("${ADMINGRP}")
        sendemail()      
	def instance = input( message: "Would you like to provide Approval for testing in test Environment? ", submitterParameter: 'submitter',submitter: "${adminapprovers}")
	userinfo = "${instance.submitter}"
	echo "$userinfo  is submitter"
}

def LDAPUSERS(group) {
    def ldapUsername =  Cred("ldap", "user").toString().trim()
    def ldapPassword = Cred("ldap", "password").toString().trim()
    def ldapUrl = Cred("ldap", "url").toString().trim()
    def ldapGroupDn = Cred("ldap", "groupdn").toString().trim()
    
    // LDAP group details fetching
    def ldapAuth = "${ldapUsername}"+":"+"${ldapPassword}"
    ldapUrl = "${ldapUrl}" + 'cn='+ "${group}" +"${ldapGroupDn}" 
    ldapEntryFile = "${JENKINS_HOME}/workspace/output.txt"
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

def sendemail() {
    def jobname = "${env.JOB_NAME}".split('/').first()
    def array = jobname.split("/")
    jobname = array[0];
    def branchname = "${env.JOB_NAME}".split('/').last()
    def approvers = LDAPUSERS("${ADMINGRP}")
    def recipents = approvers.replace('[','')
    recipents = "$recipents"
    echo "${recipents}"
    try {
            mail bcc: '',
            subject: "Need DEV Approval for Jenkins Build ${env.BUILD_NUMBER}: Job ${jobname}/${env.BRANCH_NAME}",
            to: "${recipents}",
            mimeType: 'text/html',
            body: "<b>Need DEV Approval in jenkins for migration to testing  Stage, click here :</b> ${JENKINS_URL}blue/organizations/jenkins/${jobname}/detail/${branchname}/${BUILD_NUMBER}/pipeline",
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
                            subject: "Send email from Jenkins pipeline is not successful for unlok Appllication",
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

def Cred(key,value){
    def ldapCreds = new Ini(new FileInputStream("${JENKINS_HOME}/workspace/allcreds.cnf.txt"));
    return ldapCreds.get(key, value)
}
return this
