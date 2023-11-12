pipeline {
    agent any
    environment {
        // POSTGRES AUTHENTICATION
        POSTGRES_PASSWORD = credentials('POSTGRES_PASSWORD')
        POSTGRES_USER = credentials('POSTGRES_USER')
        POSTGRES_DATABASE = credentials('POSTGRES_DATABASE')

        PORT = "${env.BRANCH_NAME == "develop" ? "8081" : "8080"}"
        ENV = getEnvName(env.BRANCH_NAME);

        APPLICATION_IMAGE_NAME = "ac2_${env.ENV}"
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
            steps {
                script {
                    if(isUnix()) {
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
                    if(isUnix()) {
                        sh 'chmod +x ./jenkins/scripts/compose.sh'
                        sh './jenkins/scripts/compose.sh'
                    } else {
                        bat 'jenkins/scripts/compose.bat'
                    }
                }

                input message: 'Kill the container? (click to continue)'

                script {
                    if(isUnix()) {
                        sh 'chmod +x ./jenkins/scripts/kill.sh'
                        sh './jenkins/scripts/kill.sh'
                    } else {
                        bat 'jenkins/scripts/kill.bat'
                    }
                }
            }
        }
    }
}

def getEnvName(branchName) {
    if("main".equals(branchName)) {
        return "prod";
    } else if ("homolog".equals(branchName)) {
        return "homol";
    } else {
        return "dev";
    }
}