import org.ini4j.Ini

def approvalStage()
{
    sendmail()
    def submitter  = input(message: "would you like to provide approval")
    
}
def sendmail()
{
    emailext bcc:'',
    subject: 'groovy',
    to: 'udaykumar.gorrepati123@gmail.com',
    mimeType: 'text/html',
    body: "goto ${BUILD_URL}  to approve stahe"
}
return this
