import org.ini4j.Ini

def approvalStage()
{
   // sendmail()
    //def submitter  = input(message: "would you like to provide approval")
    name = creds("details", "name").toString().trim()
    sur = creds("details", "surname").toString().trim()
    echo "${name}" 
    echo "${sur}"
    
}
def sendmail()
{
    emailext bcc:'',
    subject: 'groovy',
    to: 'udaykumar.gorrepati123@gmail.com',
    mimeType: 'text/html',
    body: "goto ${BUILD_URL}  to approve stahe"
}
def creds(key,value)
{
    def detailcreds = load Ini(new FileInputStream(".connect_param.cnf.txt"));
    return detailcreds.get(key,value);
}
return this
