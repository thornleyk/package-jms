import ballerina.net.jms;
//import ballerina.net.http;
//import ballerina.net.http.resiliency;

function testAcknowledge(jms:JMSMessage msg, string s){
    msg.acknowledge(s);
}

function testCommit(jms:JMSMessage msg){
    msg.commit();
}

function testRollback(jms:JMSMessage msg){
    msg.rollback();
}

jms:ClientProperties properties = {initialContextFactory:"wso2mbInitialContextFactory",
                                    providerUrl:"amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
                                    connectionFactoryName: "QueueConnectionFactory",
                                    connectionFactoryType : "queue"};


endpoint<jms:Client> productsService {initialContextFactory:"wso2mbInitialContextFactory",
                                    providerUrl:"amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
                                    connectionFactoryName: "QueueConnectionFactory",
                                    connectionFactoryType : "queue"}

function jmsClientConsume() {
    
    
}

function jmsClientConnector() {

}

function jmsMessageCreate() {

    jms:JMSMessage message = jms:createTextMessage(properties);
    productsService -> send("HL7.IN",message);

}