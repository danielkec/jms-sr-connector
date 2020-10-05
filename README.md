# Helidon MP Bare

POC for using SmallRye JMS connector with Helidon Messaging

NOTICE: 
* SmallRye JMS connector jar is missing bean.xml - workaround is possible 
  with registering connector bean manually see `SmallryeJMSCompatibilityExtension`
* Tested with Artemis active MQ eg. JMS 2.0 api(smallrye is not backward compatible)
  Weblogic supports JMS 2.0 api since 12.2.1

```
docker run -it --rm  \
-e DISABLE_SECURITY=true \
-p 8161:8161 \
-p 61616:61616 \
-e ARTEMIS_USERNAME=kec \
-e ARTEMIS_PASSWORD=kec \ 
vromero/activemq-artemis

mvn package
java -jar target/jms-sr.jar

curl -X GET http://localhost:8080/send/test1

>>Message: test1
```
