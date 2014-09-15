# Messaging on Cloud

In this demo, we will create a broker that takes in MQTT, STOMP, and AMQP protocol on Openshift.
And we are also going to use camel to route the data using the protocol above and put it into a message collector. 

##Setup Broker 
Under MQ, create a new broker configuration
![createbrokerconfig](https://github.com/weimeilin79/iot-mashup/blob/master/docs/image/01-createbrokerconfig.png?raw=true)

![brokerdetail](https://github.com/weimeilin79/iot-mashup/blob/master/docs/image/02-brokerdetail.png?raw=true)

Add a new configuration xml file "demobroker.xml"
Add new transport protocol port

		<transportConnector name="amqp" publishedAddressPolicy="#addressPolicy" uri="amqp://${OPENSHIFT_FUSE_IP}:5672"/>
		<transportConnector name="mqtt" publishedAddressPolicy="#addressPolicy" uri="mqtt://${OPENSHIFT_FUSE_IP}:1883"/>
		<transportConnector name="stomp" publishedAddressPolicy="#addressPolicy" uri="stomp://${OPENSHIFT_FUSE_IP}:31613"/>
		<transportConnector name="websocket" publishedAddressPolicy="#addressPolicy" uri="ws://${OPENSHIFT_FUSE_IP}:31614"/>

Under org.fusesource.mq.fabric.server-democontainer.properties, change the profile to 

		config = profile:demobroker.xml
		
Create a new container for the broker. Under MQ, click on the red triangle in the demobroker
![brokertriangle](https://github.com/weimeilin79/iot-mashup/blob/master/docs/image/03-brokertriangle.png?raw=true)
![brokercontainer](https://github.com/weimeilin79/iot-mashup/blob/master/docs/image/04-brokercontainer.png?raw=true)

##Create different routes
Make sure all 3 camel projects has the correct broker setting, with the IP and Port on the OpenShift and ID/PWD as well.
There are 4 property files that stores authentication data for camel route to retrieve later.
		
		amqp-consumer/src/main/fabric8openshiftconfig.properties
		mqtt-consumer/src/main/fabric8openshiftconfig.properties
		stomp-consumer/src/main/fabric8openshiftconfig.properties 
		dashboard/src/main/resources/openshiftconfig.properties

Change it according to your setting
		
		username=admin
		password=awzWIkuBY7Xg
		internalIP=127.4.65.1
		
With local web application, instead of using camel components to poll the message, normal Java JMS or API is used. So 2 more properties needs to be configured as well.
		dashboard/src/main/resources/amqp.properties
		dashboard/src/main/resources/mqtt.properties

For amqp.properties, please update your user login name and password
		connectionfactory.myJmsFactory = amqp://admin:awzWIkuBY7Xg@localhost:5672/test/

For mqtt.properties, please also update your user login name and password
		username=admin
		password=awzWIkuBY7Xg


##Deploy projects
Change the setting in your .m2/setting.xml

    <server>
      <id>fabric8.openshift.repo</id>
      <username>admin</username>
      <password>fusepassword</password>
    </server>
    
Add/Change the password and internal IP address in the openshiftconfig.properties under src/main/fabric8    

In commandline console, build and deploy 3 different route projects for AMQP, MQTT and AMQP
		mvn clean install
		mvn fabric8:deploy
in all 3 projects

Add 3 built profile to the container we have created for the broker


##Forward the port to local machine

		rhc port-forward containerName

##Start up local application by running in the dashboard Project

Change the openshift fuse password in amqp.properties, mqtt.properties and openshiftconfig.properties
Start up the local web application. 
		
		java -jar jetty-runner-7.6.9.v20130131.jar  target/dashboard-1.0.0-SNAPSHOT.war

There are 4 pages to play with

	The Receiver Page, shows and print out all the message from all sender
	http://localhost:8080/index.html
	
	AMQP Sender Page, send message via AMQP
	http://localhost:8080/amqp.html
	
	MQTT Sender Page, send message via MQTT
	http://localhost:8080/mqtt.html
	
	STOMP Sender Page, send message via WebSocket
	http://localhost:8080/stomp.html
		
##Related Video		
https://vimeo.com/105984107
		
##TODO
	Setup SNI static route in OSE