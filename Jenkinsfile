pipeline {
    agent any
    environment {
        // postgres authentication
        postgres_password = credentials('postgres_password')
        postgres_user = credentials('postgres_user')
        postgres_database = credentials('postgres_database')

        // docker push
        docker_token = credentials('docker_token')
        docker_user = "viniferr33"

        port = "${env.branch_name == "dev" ? "8081" : "8080"}"
        env = getenvname(env.branch_name);

        slack_id = credentials('slack_token')
        slack_channel = "#dev"
        slack_workspace = "vinidevworkspace"

        application_image_name = "viniferr33/ac2"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'chmod +x ./jenkins/scripts/test.sh'
                        sh './jenkins/scripts/test.sh'
                    } else {
                        bat 'jenkins/scripts/test.bat'
                    }
                    junit 'target/surefire-reports/**/*.xml'
                    jacoco(execPattern: 'target/**.exec')
                }
            }
        }

        stage('Build') {
            when {
                branch 'dev'
            }

            steps {
                script {
                    if (isUnix()) {
                        sh 'chmod +x ./jenkins/scripts/build.sh'
                        sh './jenkins/scripts/build.sh'
                    } else {
                        bat 'jenkins/scripts/build.bat'
                    }
                }
            }
        }

        stage('Homolog') {
            when {
                branch 'homolog'
            }

            steps {
                script {
                    def envFile = """
                        POSTGRES_PASSWORD=${env.POSTGRES_PASSWORD}
                        POSTGRES_USER=${env.POSTGRES_USER}
                        POSTGRES_DATABASE=${env.POSTGRES_DATABASE}
                        PORT=${env.PORT}
                        ENV=${env.ENV}
                    """
                    writeFile file: '.env', text: envFile
                }

                script {
                    if (isUnix()) {
                        sh 'chmod +x ./jenkins/scripts/compose.sh'
                        sh './jenkins/scripts/compose.sh'
                    } else {
                        bat 'jenkins/scripts/compose.bat'
                    }
                }

                input message: 'Kill the container? (click to continue)'

                script {
                    if (isUnix()) {
                        sh 'chmod +x ./jenkins/scripts/kill.sh'
                        sh './jenkins/scripts/kill.sh'
                    } else {
                        bat 'jenkins/scripts/kill.bat'
                    }
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }

            steps {
                input message: 'Deploy on Azure?'

                script {
                    if (isUnix()) {
                        sh 'chmod +x ./jenkins/scripts/deploy.sh'
                        sh './jenkins/scripts/deploy.sh'
                    } else {
                        bat 'jenkins/scripts/deploy.bat'
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                slackSend(
                        color: 'good',
                        message: "Job '${currentBuild.fullDisplayName}' was successful!",
                        channel: env.slack_channel,
                        teamDomain: env.slack_workspace,
                        tokenCredentialId: env.slack_id
                )
            }
        }

        failure {
            script {
                slackSend(
                        color: 'danger',
                        message: "Job '${currentBuild.fullDisplayName}' failed.",
                        channel: env.slack_channel,
                        teamDomain: env.slack_workspace,
                        tokenCredentialId: env.slack_id
                )
            }
        }
    }
}

def getenvname(branchname) {
    if ("main".equals(branchname)) {
        return "prod";
    } else if ("homolog".equals(branchname)) {
        return "homol";
    } else {
        return "dev";
    }
}