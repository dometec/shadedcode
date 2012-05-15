#!/bin/sh

rm -rf /opt/FrameworkJava/apache-tomcat-7.0.25/webapps/demo*
rm -rf /opt/FrameworkJava/apache-tomcat-7.0.25/conf/Catalina/*
rm -rf /opt/FrameworkJava/apache-tomcat-7.0.25/work/*

mvn clean package
mv target/demo.war /opt/FrameworkJava/apache-tomcat-7.0.25/webapps/
/opt/FrameworkJava/apache-tomcat-7.0.25/bin/catalina.sh jpda run

