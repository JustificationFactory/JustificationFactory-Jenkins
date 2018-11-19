# Justification Factory - Jenkins
Jenkins plug-in for Justification Factory. It exposes a post-build step plugin allowing to interact with justification services throught the Justification Factory Engine. 

## Dependencies needed

You need this set of tools :
* Java 8
* Maven
* Jenkins instance (for deployment)

## To run the platform

* Compile and run tests ``` mvn clean install```
* Run plug-in for development (embedded Jenkins instance) ```mvn hpi:run```
* Run plugin for deployment : ```mvn package && cp target/*.hpi $JENKINS_HOME/plugins/justification-factory.hpi```

