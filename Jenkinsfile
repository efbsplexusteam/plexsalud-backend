pipeline {
  agent none

  environment {
    DOCKER_IMAGE = "plexsalud" // Nombre de la imagen Docker con el número de construcción
    DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}" // Nombre de la imagen Docker con el número de construcción
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