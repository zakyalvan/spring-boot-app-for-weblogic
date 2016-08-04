#Spring Boot for Oracle Weblogic

Proof of concept project, how to deploy spring boot based web app into Oracle Weblogic application server. Please note, this sample application tested against Oracle Weblogic 12.2.1.1 release, the configuration file structure seems a bit different with previous release, based on the documentations.

Important things to note :

1. Make sure ```org.springframework.boot:spring-boot-starter-tomcat``` dependency scope set to ```provided```, to prevent interference with servlet libraries come with Weblogic.

2. Create Weblogic deployment descriptor ```weblogic.xml``` in ```src/main/webapp/WEB-INF``` directory. Following are example to configure logging library. If you prefer to use packaged libraries instead of libraries come with Weblogic (for example ```Jackson```), just add another ```<wls:package-name>package.name</wls:package-name>``` element inside ```<wls:prefer-application-packages>``` element.
	```xml
	<?xml version="1.0" encoding="UTF-8"?>
	<wls:weblogic-web-app
	    xmlns:wls="http://xmlns.oracle.com/weblogic/weblogic-web-app"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
	        http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd
	        http://xmlns.oracle.com/weblogic/weblogic-web-app
	        http://xmlns.oracle.com/weblogic/weblogic-web-app/1.4/weblogic-web-app.xsd">
	    <wls:container-descriptor>
	        <wls:prefer-application-packages>
	            <wls:package-name>org.slf4j</wls:package-name>
	        </wls:prefer-application-packages>
	    </wls:container-descriptor>
	</wls:weblogic-web-app>
	```

3. Before defining datasource (other than Oracle and MySQL), install third party JDBC driver by referencing the jar library into Weblogic classpath. Following are snippet of updated ```${ML_HOME}/oracle_common/common/bin/commExtEnv.sh``` (Some tutorial recommend to add in ```${WL_HOME}/server/bin/setWLSEnv.sh``` file). Assume the driver library file, in this case ```postgresql-9.4.1209.jre7.jar``` copied into ```${WL_HOME}/server/lib``` already.
	
	```sh
	...
	
	POSTGRESQL_JDBC_DRIVER_PATH="${WL_HOME}/server/lib/postgresql-9.4.1209.jre7.jar"
	export POSTGRESQL_JDBC_DRIVER_PATH
	
	WEBLOGIC_CLASSPATH="${JAVA_HOME}/lib/tools.jar${CLASSPATHSEP}${PROFILE_CLASSPATH}${CLASSPATHSEP}${ANT_CONTRIB}/lib/ant-contrib.jar${CLASSPATHSEP}${CAM_NODEMANAGER_JAR_PATH}${CLASSPATHSEP}${POSTGRESQL_JDBC_DRIVER_PATH}"
	
	...
	```

4. Add ```<enforce-valid-basic-auth-credentials>false</enforce-valid-basic-auth-credentials>``` as the last element of ```</security-configuration>``` tag on ```DOMAIN_DIRECTORY/config/condif.xml``` xml file, so that user wont be prompted to authenticating theirself against Weblogic server when accessing the application, as authentication managed internally by the application.

5. JNDI resource with name, for example ```jdbc.SampleDatasource```, could be looked up using ```jdbc/SampleDatasource``` name in addition to lookup using original name.

6. Don't forget to add an empty application context configuration with name ```dispatcherServlet-servlet.xml``` located on ```src/main/webapp/WEB-INF/```. Following are example of that file.
	
	```xml
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
	</beans>
	```