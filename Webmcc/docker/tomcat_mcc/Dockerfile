FROM dordoka/tomcat
MAINTAINER Lucian Condescu

COPY apt.conf /etc/apt/apt.conf
COPY setenv.sh /opt/tomcat/bin/setenv.sh
COPY catalina.properties /opt/tomcat/conf/catalina.properties
RUN dpkg --add-architecture i386 && apt-get update && apt-get install -y wine
