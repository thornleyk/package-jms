import ballerina.net.jms;

jms:ClientProperties properties = {initialContextFactory:"the.mocks.InitialContextFactoryMock",
                                    providerUrl:"amqp://admin:admin@carbon/carbon?brokerlist='tcp://localhost:5672'",
                                    connectionFactoryName: "QueueConnectionFactory",
                                    connectionFactoryType : "queue"};


endpoint<jms:Client> productsService {initialContextFactory:"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
                                    providerUrl:"tcp://192.168.99.100:61616",
                                    connectionFactoryName: "QueueConnectionFactory",
                                    connectionFactoryType : "queue"}

function jmsMessageCreate() {

    jms:JMSMessage message = jms:createTextMessage(properties);
    productsService -> send("HL7.IN",message);

}