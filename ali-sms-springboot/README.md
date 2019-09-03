# ali-sms-springboot

将配置信息存放xml。可以配置与多个项目多个模板组合

发送：http://127.0.0.1:8888/sms/send?templateId=1001&numbers=13521300635&code=654321&clientIp=127.0.0.1&projectId=222



pom-jar.xml
将sms达成jar包，命令：mvn clean package   -Dmaven.test.skip=true，需要修改对应的Application

pom-war.xml
将sms达成war包，命令：mvn clean package   -Dmaven.test.skip=true，需要修改对应的Application
