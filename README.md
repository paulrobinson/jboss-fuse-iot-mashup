# Messaging on Cloud

In this demo, we will create a broker that takes in MQTT, STOMP, and AMQP protocol on Openshift.
And we are also going to use camel to route the data using the protocol above and put it into a message collector. 

##Setup Broker 
Please follow the video instruction to setup your broker

##Create different routes

Change the setting in your .m2/setting.xml

    <server>
      <id>fabric8.openshift.repo</id>
      <username>admin</username>
      <password>fusepassword</password>
    </server>

mvn fabric8:deploy to install 

in all 3 projects


##Forward the port to local machine

		rhc port-forward containerName

##Start up local application by running in the dashboard Project

		java -jar jetty-runner-7.6.9.v20130131.jar  target/dashboard-1.0.0-SNAPSHOT.war
		
##TODO
	Setup SNI static route in OSE