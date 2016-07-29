#Spring Boot for Oracle Weblogic

Proof of concept project, how to deploy spring boot based web app into Oracle Weblogic application server. Please note, this sample application tested against Oracle Weblogic 12.2.1.1 release, the configuration file structure seems a bit different with previous release, based on the documentations.

Important things to note :

1. Before defining datasource other than Oracle and MySQL, install third party JDBC driver by referencing the jar library into Weblogic classpath. Following are snippet of updated ```${ML_HOME}/oracle_common/common/bin/commExtEnv.sh``` (Some tutorial recommend to add in ```${WL_HOME}/server/bin/setWLSEnv.sh``` file). Assume the driver library file, in this case ```postgresql-9.4.1209.jre7.jar``` copied into ```${WL_HOME}/server/lib``` already.
```sh
...

POSTGRESQL_JDBC_DRIVER_PATH="${WL_HOME}/server/lib/postgresql-9.4.1209.jre7.jar"
export POSTGRESQL_JDBC_DRIVER_PATH

WEBLOGIC_CLASSPATH="${JAVA_HOME}/lib/tools.jar${CLASSPATHSEP}${PROFILE_CLASSPATH}${CLASSPATHSEP}${ANT_CONTRIB}/lib/ant-contrib.jar${CLASSPATHSEP}${CAM_NODEMANAGER_JAR_PATH}${CLASSPATHSEP}${POSTGRESQL_JDBC_DRIVER_PATH}"

...
```

2. Add ```<enforce-valid-basic-auth-credentials>false</enforce-valid-basic-auth-credentials>``` as the last element of ```</security-configuration>``` tag, so that user wont be prompted to authenticating theirself to application server when accessing application, as authentication managed internally inside the application.

3. JNDI resource with name, for example ```jdbc.SampleDatasource```, could be looked up using ```jdbc/SampleDatasource``` name in addition to lookup using original name.

4. Don't forget to add an empty application context configuration with name ```dispatcherServlet-servlet.xml``` located on ```src/main/webapp/WEB-INF/```. Following are example of that file.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
</beans>
```