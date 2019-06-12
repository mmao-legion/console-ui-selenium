node {
    def testImage
    stage('Checkout') {
        checkout scm
    }
    stage('Build image') {
        testImage = docker.build("console-ui-selenium:${env.BUILD_ID}")
    }
    stage('Run test') {
        testImage.inside {
            sh 'mvn test -X -P dev'
        }
    }
}