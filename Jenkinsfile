pipeline {
    agent any
    environment {
        DOCKER_CLI_EXPERIMENTAL = 'enabled'
        DOCKER_IMAGE = 'bookie'  // Image name
        DOCKER_REGISTRY = 'docker.io'  // Docker Hub registry
        DOCKERHUB_CREDENTIALS = 'dockerhub-credentials'  // Jenkins credentials for Docker Hub
        SONARQUBE_SERVER = 'SonarQube'  // SonarQube server name in Jenkins
        MAVEN_HOME = '/usr/local/maven'  // Path to Maven installation (can be adjusted)
    }
    tools {
        maven '3.9.9'  // Assuming Maven 3.8.1 is installed in Jenkins tools section
    }
    stages {
        stage('Checkout Code') {
            steps {
                // Pull your code from GitHub repository
                git 'https://github.com/NokuManhango/book.git'
            }
        }
        stage('Maven Build and Tests') {
            steps {
                script {
                    echo 'Building with Maven and running tests...'

                    // Run Maven build, tests and generate the .jar/.war file
                    sh "mvn clean install -DskipTests=false"
                    
                    // Run SonarQube analysis after the build
                    withSonarQubeEnv('SonarQube') {
                        sh "mvn sonar:sonar -Dsonar.projectKey=bookie -Dsonar.host.url=http://localhost:9000"
                    }

                    // Run JaCoCo for code coverage
                    sh "mvn clean test jacoco:report"
                }
            }
        }
        stage('SonarQube Quality Gate') {
            steps {
                script {
                    echo 'Waiting for SonarQube Quality Gate...'
                    // Wait for SonarQube Quality Gate analysis to pass
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker Image...'
                    // Build Docker image from Dockerfile in the repository
                    docker.build("${DOCKER_IMAGE}")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    echo 'Pushing Docker Image to Docker Hub...'
                    // Push Docker image to Docker Hub
                    docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKERHUB_CREDENTIALS) {
                        docker.image("${DOCKER_IMAGE}").push('latest')
                    }
                }
            }
        }
        stage('Deploy with Docker Compose') {
            steps {
                script {
                    echo 'Deploying with Docker Compose...'
                    // Use Docker Compose to deploy the service
                    sh 'docker-compose -f docker-compose.yml up -d'
                }
            }
        }
    }
    post {
        always {
            // Clean up Docker containers and images after deployment
            echo 'Cleaning up...'
            sh 'docker-compose down'
            sh 'docker system prune -f'
        }
    }
}
