pipeline {
    agent { label 'android' }

    environment {
        ANDROID_HOME = '/home/codequest/Android/Sdk'
        PATH = "/usr/local/bin:${PATH}"

        repositoryName = sh(returnStdout: true, script: "basename ${env.GIT_URL} .git").trim()
        artifactPath = "app/build/outputs/apk/dev/release"
        apkFilename = "pathbysimplex-dev-release.apk"

        // AWS bucket configuration values
        region = 'eu-central-1'
        bucketName = 'codequest-android-test-apps'
        credentials = 'android-aws-uploader'
        branchHumanReadableName = "${(env.BRANCH_NAME.startsWith('PR')) ? env.CHANGE_BRANCH.replaceAll("/", "-") : env.BRANCH_NAME}"
        s3Filename = "pathbysimplex-${branchHumanReadableName}-${env.BUILD_NUMBER}.apk"
        s3uploadPath = "${repositoryName}/${env.BRANCH_NAME}/$s3Filename"
        s3absolutePath = "https://codequest-android-test-apps.s3.${region}.amazonaws.com/${s3uploadPath}"

        // Slack channel values
        slackUserWero = "@UHGA3A8GY"
        slackUserManiek = "@U0C1BLL00"
        slackBuildStatusChannel = "#status-snopsis-android"

        // Emails for notifications
        emailCharles = "Charles@onacloud.org"

        slackProjectName = "${env.JOB_NAME}"
        slackBranchMessageBranchName = "Branch name: *${env.branchHumanReadableName}*"
        slackBuildNumber = "Build number: ${env.BUILD_NUMBER}"
    }

    stages {
        stage('Copy secrets') {
            steps {
                script { copySecrets() }
            }
        }

        stage('Clean') {
            steps {
                sh './gradlew clean'
            }
        }

        stage('Rebuild') {
            steps {
                sh './gradlew assembleDebug'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew testdevReleaseUnitTest'

                junit allowEmptyResults: true, testResults: '**/test-results/*.xml'
            }
        }

        stage('Upload to AWS') {
            when {
                branch 'develop'
            }
            steps {
                withAWS(region: region, credentials: credentials) {
                    s3Upload(file: "$artifactPath/$apkFilename", bucket: bucketName, path: s3uploadPath)
                }
            }
        }
    }

    post {
        success {
            sendSuccessSlackMessage()
        }

        failure {
            sendFailureSlackMessage()
        }

        always {
            cleanWs deleteDirs: true, patterns: [[pattern: 'app/build/outputs/**', type: 'EXCLUDE'],
                                                 [pattern: 'app/build/test-results/**', type: 'EXCLUDE'],
                                                 [pattern: 'app/builds/reports/**', type: 'EXCLUDE'],
                                                 [pattern: '', type: 'INCLUDE']]
        }
    }
}

private void copySecrets() {
    copyKeystores()
    copyKeystoresProperties()
}

private void copyKeystores() {
    withCredentials([file(credentialsId: 'snopsis_upload_keystore', variable: 'upload_keystore_file')]) {
        sh 'cp \$upload_keystore_file $PWD/upload_keystore.jks'
    }
    withCredentials([file(credentialsId: 'snopsis_debug_keystore', variable: 'debug_keystore_file')]) {
        sh 'cp \$debug_keystore_file $PWD/debug_keystore.jks'
    }
}

private void copyKeystoresProperties() {
    withCredentials([file(credentialsId: 'snopsis_upload_keystore_properties', variable: 'upload_keystore_properties_file')]) {
        sh 'cp \$upload_keystore_properties_file $PWD/upload_keystore.properties'
    }
    withCredentials([file(credentialsId: 'snopsis_debug_keystore_properties', variable: 'debug_keystore_properties_file')]) {
        sh 'cp \$debug_keystore_properties_file $PWD/debug_keystore.properties'
    }
}

private void sendSuccessSlackMessage() {
    successMessage = "*New build: ${slackProjectName}*:\n${slackBranchMessageBranchName}\n${slackBuildNumber}${getInstallMsg()}"
    slackSend channel: env.slackBuildStatusChannel, color: "good", message: successMessage
    if (env.BRANCH_NAME.contains("develop")) {
        slackSend channel: env.slackUserWero, color: "good", message: successMessage
        slackSend channel: env.slackUserManiek, color: "good", message: successMessage
    }
}

private void sendFailureSlackMessage() {
    failureMessage = "*New build failed: ${slackProjectName}*:\n${slackBranchMessageBranchName}\n${slackBuildNumber}"
    slackSend channel: env.slackBuildStatusChannel, color: "danger", message: failureMessage
}

private String getInstallMsg() {
    if (env.BRANCH_NAME.contains("develop")) {
        return "\nInstall link: ${s3absolutePath}"
    } else {
        return ""
    }
}
