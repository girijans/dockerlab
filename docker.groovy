pipeline {
    agent any 
    
      stages {
          stage ('Build') {
              steps {
                  git branch: 'main', url: 'https://github.com/girijans/dockerlab.git'
                  sh "docker build . -t nodejstest:latest"
                    }
          }  
          stage ('Test') {
              steps {
                  sh """ docker run  -d -p 3000:3000 nodejstest:latest 
                         curl localhost:3000
                         if ( echo $? = 0 )
                         exit
                     """    
              }
          }
          stage ('Deploy') {
              steps {
                  sh """docker push girijans/nodejstest:latest
                        docker rmi nodejstest
                     """   
              }
          }
    }
}
