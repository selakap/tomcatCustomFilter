# tomcatCustomFilter

1. Stop the tomcat server.
2. Add the jar file after building the project to the following directory of the SERVER. Please find the attached source code as well.

<SERVER_HOME>/repository/component/lib

3. Add the filter configuration as follows to the web.xml file located in the <SERVER_HOME>/repository/conf/tomcat/carbon/WEB-INF/ directory.

<filter>
<filter-name>requestdumpingfilter</filter-name>
<filter-class>tomcat.custom.filter.TomcatRequestFilter</filter-class>
</filter>

<filter-mapping>
<filter-name>requestdumpingfilter</filter-name>
<url-pattern>*</url-pattern>
</filter-mapping>

4. Locate to the log4j.properties file in the <SERVER_HOME>/repository/conf

Configure the logger for the custom component by adding below line to the log4j.properties file

log4j.logger.tomcat.custom.filter=DEBUG

5. Restart the server

Sample log

TID: [-1234][http-nio-9443-exec-13id] [] [2020-10-28 05:33:32,940] DEBUG {tomcat.custom.filter.TomcatRequestFilter} -  ============ Custom Log ===========
Header - content-type: application/soap+xml; charset=UTF-8; action="urn:validateKey"
