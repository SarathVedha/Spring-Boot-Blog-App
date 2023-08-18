pipeline {
    agent any

    tools {
        // Install the Maven version configured as "Maven" in Jenkins Tools and add it to the path.
        maven "Maven"
    }

    stages {
        // CI Development Stages
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
                bat 'docker build -t sarathvedha/springboot-blog-app:%BUILD_NUMBER% .'

                echo 'Docker Image Creation Done'
            }

        }
        stage('Push Docker Image To DockerHub') {
            steps{

                echo "Running ${env.JOB_NAME} Building ${env.BUILD_NUMBER} On ${env.JENKINS_URL}"

                echo 'Pushing Docker Image To Hub'

                // Added Docker Creeds In Jenkins Secrets to login docker ${Docker}
                withCredentials([string(credentialsId: 'Docker', variable: 'Docker')]) {

                    // for sh -> bat 'docker login -u sarathvedha -p ${Docker}'
                    bat 'docker login -u sarathvedha -p %Docker%'
                    bat 'docker push sarathvedha/springboot-blog-app:%BUILD_NUMBER%'
                }

                echo 'Imaged Pushed To Hub Done'
            }
        }
        stage('Update K8s Deployment File') {
            steps{
                // Updating K8s Deployment File image tag with jenkins build number
                script{

                    echo "Existing Deployment File : "
                    // sh -> sh 'cat K8s/Deployment.yaml'
                    bat 'more K8s\\Deployment.yaml'

                    // Updating Deployment File With Groovy syntax this for both linux and windows
                    def text = readFile 'K8s/Deployment.yaml'
                    text = text.replaceAll("image:.*", "image: sarathvedha/springboot-blog-app:${env.BUILD_NUMBER}")
                    writeFile file: 'K8s/Deployment.yaml', text: text

                    echo "Updated Deployment File : "
                    // sh -> sh 'cat K8s/Deployment.yaml'
                    bat 'more K8s\\Deployment.yaml'

                }
            }
        }
        stage('Push Deployment File To GitHub') {
            steps{
                echo "Pushing Updated Deployment File To GITHUB Repo"

                // sh -> sh 'git config user.email vedha@admin.com'
                bat 'git config user.email vedha@admin.com'

                bat 'git config user.name Vedha'

                bat 'git add K8s\\Deployment.yaml'

                // sh -> sh 'git commit -m "image tag updated to ${BUILD_NUMBER}"'
                bat 'git commit -m "Updating Deployment.yaml File Image Tag To %BUILD_NUMBER%"'

                withCredentials([string(credentialsId: 'GitHubToken', variable: 'TOKEN')]) {
                    bat 'git push https://%TOKEN%@github.com/SarathVedha/Spring-Boot-Blog-App HEAD:master'
                }

                echo "Pushing Updated Deployment File To GITHUB Repo Done"

            }
        }
    }
}
