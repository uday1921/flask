import org.ini4j.ini4j

def approvalStage()
{
    sendmail()
    def submitter  = input(message: "would you like to provide approval")
    
}
def sendmail()
{
    mail bcc:'',
    subject: 'groovy',
    to: 'udaykumar.gorrepati123@gmail.com',
    mimeType: 'text/html',
    body: "goto ${BUILD_URL}  to approve stahe",
    cc: '',
    from: 'Jenkins <noreply@gmail.com>'
    replyTo: 'no Reply'
}