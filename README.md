# devstarter-android-mvi

Android application devstarter project template that can be used to start writing your application using MVI architecture. 

## Project setup

1. [Create a new repo using devstarter-android-mvi as a template](https://github.com/codequest-eu/devstarter-android-mvi/generate)
2. Clone it locally

   ```sh
   git clone <project_url>
   ```
   
3. Change package name for the project structure

4. Align [config.gradle](./config.gradle)

5. Update README.md file

6. Enjoy!

## CI/CD

Example workflow for [Jenkins](https://jenkins.io/) could be:

1. Run stage with `./gradlew clean` to clean the workspace before build
2. Run stage with `./gradlew assemble` to build the app
3. Run stage with `./gradlew detekt` to run static code analysis
4. Run stage with `./gradlew test` to run application unit tests

