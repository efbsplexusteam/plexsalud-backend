pipeline {
  agent none

  environment {
    DOCKER_IMAGE = "plexsalud-backend"
    DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}"
    COMPOSE_DIR = "/docker/plexsalud"
  }

  stages {
    // ==================================================
    // = plexsalud Jenkinsfile                          |
    // ==================================================
    stage('Check Dockerfile') {
      agent any

      steps {
          script {
              def dockerfilePath = './plexsalud/Dockerfile'
              if (fileExists(dockerfilePath)) {
                  echo "Dockerfile found at: ${dockerfilePath}"
              } else {
                  error "Dockerfile not found"
              }
          }
      }
    }

    stage('Build') {
      agent any

      steps {
        echo 'Building the project...'
        script{
          sh """
          docker build --no-cache --memory=1g --memory-swap=2g \
          -t ${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG} \
          ./plexsalud
          """
        }
      }
    }

    // stage('Deploy') {
    //   agent any

    //   steps {
    //     sh """
    //       sed -i 's|image: ${DOCKER_IMAGE}:.*|image: ${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}|' ${COMPOSE_DIR}/docker-compose.yml
    //     """

    //     sh """
    //       cd ${COMPOSE_DIR}
    //       # docker-compose down
    //       # docker-compose up -d --remove-orphans
    //       # cat ${COMPOSE_DIR}/docker-compose.yml
    //     """

    //     echo 'Building Docker image for deployment...'
    //   }
    // }
  }

  

  post {
    always {
      echo 'Finalizando pipeline'
    }
    failure {
      echo 'Algo falló 😢'
    }
    success {
      echo '¡Todo salió bien 🎉!'
    }
  }
}