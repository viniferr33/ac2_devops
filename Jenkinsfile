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
                        sh './jenkins/scripts/test.sh'
                    } else {
                        bat 'jenkins/scripts/test.bat'
                    }
                    junit 'target/surefire-reports/**/*.xml'
                    jacoco(execPattern: 'target/**.exec')
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