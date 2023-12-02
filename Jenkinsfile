pipeline {
    agent any
    environment {
        // POSTGRES AUTHENTICATION
        POSTGRES_PASSWORD = credentials('POSTGRES_PASSWORD')
        POSTGRES_USER = credentials('POSTGRES_USER')
        POSTGRES_DATABASE = credentials('POSTGRES_DATABASE')

        // DOCKER PUSH
        DOCKER_TOKEN = credentials('DOCKER_TOKEN')
        DOCKER_USER = "viniferr33"

        PORT = "${env.BRANCH_NAME == "dev" ? "8081" : "8080"}"
        ENV = getEnvName(env.BRANCH_NAME);

        APPLICATION_IMAGE_NAME = "viniferr33/ac2"
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
                def jacocoReport = junit '**/target/site/jacoco/jacoco.xml'

                slackSend(
                        color: 'good',
                        message: "Job '${currentBuild.fullDisplayName}' was successful!\nCobertura de Código: ${jacocoReport}",
                        channel: "#dev",
                        teamDomain: "vinidevworkspace",
                        tokenCredentialId: "SLACK_TOKEN"
                )
            }
        }

        failure {
            script {
                def buildFailureCause = currentBuild.rawBuild.getBuildStatusSummary().message

                slackSend(
                        color: 'danger',
                        message: "Job '${currentBuild.fullDisplayName}' failed.\nError: ${buildFailureCause}",
                        channel: "#dev",
                        teamDomain: "vinidevworkspace",
                        tokenCredentialId: "SLACK_TOKEN"
                )
            }
        }
    }
}

def getEnvName(branchname) {
    if ("main".equals(branchname)) {
        return "prod";
    } else if ("homolog".equals(branchname)) {
        return "homol";
    } else {
        return "dev";
    }
}