pipeline {
    agent any
    environment {
      GITHUB_CREDENTIALS = 'BitrockCI token'
      GITHUB_ACCOUNT = 'bitrockteam'
      GITHUB_REPO = 'kafka-dvs-avro-schemas'
      GITHUB_SSH = "centos"
      RELEASE_BRANCH = "master"
    }
    options {
        ansiColor('xterm')
    }
    stages {
        stage("Branch checkout") {
          steps {
            script {
              sh "git checkout ${BRANCH_NAME}"
              committerEmail = sh (
                    script: "git --no-pager show -s --format=\'%ae\'",
                    returnStdout: true
              ).trim()
            }
          }
        }
        stage("Environment setup") {
          when {
              not {
                  equals expected: "ci@bitrock.it", actual: committerEmail
              }
          }
          steps {
            githubNotify  status: "PENDING",
                          credentialsId: GITHUB_CREDENTIALS,
                          description: "Build pending",
                          account: GITHUB_ACCOUNT,
                          repo: GITHUB_REPO,
                          sha: GIT_COMMIT
            sh "git config --local user.name Jenkins"
            sh "git config --local user.email ci@bitrock.it"
            withCredentials([usernamePassword(credentialsId: 'BitrockNexus',
                                              usernameVariable: 'NEXUS_USER',
                                              passwordVariable: 'NEXUS_PASSWORD')]) {
                sh """
                mkdir .sbt
                echo "realm=Sonatype Nexus Repository Manager" > .sbt/.credentials
                echo "host=nexus.reactive-labs.io" >> .sbt/.credentials
                echo "user=${NEXUS_USER}" >> .sbt/.credentials
                echo "password=${NEXUS_PASSWORD}" >> .sbt/.credentials
                """
            }
          }
        }
        stage("Building master release"){
            when {
                allOf {
                  branch RELEASE_BRANCH
                  not {
                    equals expected: "ci@bitrock.it", actual: committerEmail
                  }
                }
            }
            steps {
                echo "Building master branch"
                sshagent (credentials: ['centos']) {
                  sh "sbt 'release with-defaults'"
                  githubNotify  status: "SUCCESS",
                                credentialsId: GITHUB_CREDENTIALS,
                                description: "Build success",
                                account: GITHUB_ACCOUNT,
                                repo: GITHUB_REPO,
                                sha: GIT_COMMIT
                  slackSend color: "#008000",
                            message: ":star-struck: ${JOB_NAME} new release! (<${BUILD_URL}|Open>)"
                }
          }
        }
        stage("Building feature/develop"){
          when {
              allOf {
                not {
                  branch RELEASE_BRANCH
                }
                not {
                  equals expected: "ci@bitrock.it", actual: committerEmail
                }
              }
          }
          steps {
              echo "Building feature/develop branch"
              sh "sbt test"
              githubNotify  status: "SUCCESS",
                            credentialsId: GITHUB_CREDENTIALS,
                            description: "Build success",
                            account: GITHUB_ACCOUNT,
                            repo: GITHUB_REPO,
                            sha: GIT_COMMIT
          }
        }
      }
    post {
      always {
        echo "Cleaning workspace"
        cleanWs()
      }
      failure {
        githubNotify  status: "FAILURE",
                      credentialsId: GITHUB_CREDENTIALS,
                      description: "Build failure",
                      account: GITHUB_ACCOUNT,
                      repo: GITHUB_REPO,
                      sha: GIT_COMMIT
        slackSend color: "#FF0000",
                  message: ":exploding_head: ${JOB_NAME} build ${BUILD_NUMBER} failure! (<${BUILD_URL}|Open>)"
      }
    }
}
