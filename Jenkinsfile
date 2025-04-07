pipeline {
    agent any

    environment {
        IMAGE_NAME = 'noku/book-app'
    }

    stages {
        stage('Clone') {
            steps {
                git url: 'https://github.com/NokuManhango/book.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:${BUILD_NUMBER} .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'nokutendamanhango', passwordVariable: 'Cleaverboard3#')]) {
                    sh """
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $IMAGE_NAME:${BUILD_NUMBER}
                    """
                }
            }
        }

        stage('Deploy with Ansible') {
            steps {
                sh 'ansible-playbook -i ansible/inventory.ini ansible/deploy.yml --extra-vars "build_number=${BUILD_NUMBER}"'
            }
        }
    }
}
