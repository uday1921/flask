pipeline {
    agent any
    stages
    {
        stage("Display message")
        {
            steps
            {
               script 
               {
                    echo "uday kumar gorrepati...."
               }
            }
            post
            {
                always
                {
                    script 
                    {
                        def email = "udaykumar.gorrepati123@gmail.com"
                        emailext (
                            to: "${email}",
                            subject: "In post Stage....",
                            body: "Please click on URL ${BUILD_URL} to check..",
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                        )
                    }
                }
            }

        }
    }
}
