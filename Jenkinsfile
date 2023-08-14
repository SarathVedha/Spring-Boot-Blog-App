pipeline {
    agent any

    tools {
        // Install the Maven version configured as "Maven" in Jenkins Tools and add it to the path.
        maven "Maven"
    }

    stages {
        stage('Get SourceCode From GitHub') {
            steps {

                echo "Running ${env.JOB_NAME} Building ${env.BUILD_NUMBER} On ${env.JENKINS_URL}"

                echo 'Getting Source Code From GitHub Repo'

                // Use PipeSyntax to generate scripts
                // Get some code from a GitHub repository
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'GitHub', url: 'https://github.com/SarathVedha/Spring-Boot-Blog-App.git']])

                echo 'CheckOut Source Code Done'
            }
        }
        stage('Building Maven App Jar') {
            steps{

                echo "Running ${env.JOB_NAME} Building ${env.BUILD_NUMBER} On ${env.JENKINS_URL}"

                echo 'Building Jar Using Maven'

                // To run Maven on a Windows agent, use
                bat 'mvn clean install -Dmaven.test.skip=true'

                echo 'Building Jar Done'
            }
        }
        stage('Creating Docker Image'){
            steps{

                echo "Running ${env.JOB_NAME} Building ${env.BUILD_NUMBER} On ${env.JENKINS_URL}"

                echo 'Creating Docker Image'

                // Create Docker Image By checkout github repo code having Dockerfile
                // Want To Pass Job Build Number in tag
                // for windows -> (bat 'docker build -t sarathvedha/springboot-blog-app:%BUILD_NUMBER% .')
                // for sh -> (sh 'docker build -t sarathvedha/springboot-blog-app:${BUILD_NUMBER} .')
                bat 'docker build -t sarathvedha/springboot-blog-app:latest .'

                echo 'Docker Image Creation Done'
            }

        }
        stage('Push Docker Image To Hub') {
            steps{

                echo "Running ${env.JOB_NAME} Building ${env.BUILD_NUMBER} On ${env.JENKINS_URL}"

                echo 'Pushing Docker Image To Hub'

                // Added Docker Creeds In Jenkins Secrets to login docker ${Docker}
                withCredentials([string(credentialsId: 'Docker', variable: 'Docker')]) {

                    // for sh -> bat 'docker login -u sarathvedha -p ${Docker}'
                    bat 'docker login -u sarathvedha -p %Docker%'
                    bat 'docker push sarathvedha/springboot-blog-app:latest'
                }

                echo 'Imaged Pushed To Hub Done'
            }
        }
    }
}
