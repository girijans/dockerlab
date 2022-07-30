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
                  sh """docker run -d --name sample -p 3000:3000 nodejstest:latest 
                        curl localhost:3000
                        export status = \${?}
                        if [ "${status}" != "0" ]
                        then
                          exit 
                        fi
                        # if [ echo ${} = 0 ]
                        # exit
                     """    
              }
          }
          stage ('Deploy') {
              steps {
                  sh """docker push girijans/nodejstest:latest
                        docker rm -f sample
                        docker rmi nodejstest
                     """   
              }
          }
    }
}
