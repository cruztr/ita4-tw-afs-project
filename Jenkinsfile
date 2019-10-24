pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
               script {
                    sh './gradlew build' //run a gradle task
               }
            }
        }
    }
}