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
        stage('Unit Test') {
            steps {
               script {
                   try {
                       sh './gradlew clean test --no-daemon' //run a gradle task
                   } finally {
                       junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
                   }
               }
            }
        }
    }
}