package org.ballerinalang.net.jms.nativeimpl.client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ballerinalang.bre.bvm.WorkerExecutionContext;
import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSConnectorFutureListener;
import org.ballerinalang.net.jms.nativeimpl.util.BTestUtils;
import org.ballerinalang.net.jms.nativeimpl.util.TestAcknowledgementCallback;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.jms.Connection; 
import javax.jms.ConnectionFactory; 
import javax.jms.DeliveryMode; 
import javax.jms.JMSException; 
import javax.jms.Message; 
import javax.jms.MessageProducer; 
import javax.jms.Queue; 
import javax.jms.Session; 
import javax.jms.TextMessage; 
import java.util.Optional; 
import java.util.UUID; 
/**
 * Test cases for ballerina.net.jms native functions.
 */
public class ClientTest {
    private CompileResult compileResult;
    private ConnectionFactory connectionFactory = mock(ConnectionFactory.class);  
    private Connection connection = mock(Connection.class);   
    private Session session = mock(Session.class);
    private Queue queue = mock(Queue.class); 
    private MessageProducer messageProducer = mock(MessageProducer.class);  
    private TextMessage textMessage = mock(TextMessage.class);   


    private static final Log log = LogFactory.getLog(ClientTest.class);

    @BeforeClass
    public void setup() {
        compileResult = BTestUtils.compile("client/test-jms-client.bal");
    }

    @Test (enabled= false, description = "Test Ballerina native JMS send message")
    public void testSend() {
        
        //Create the Context for the Execution
        WorkerExecutionContext parentCtx = new WorkerExecutionContext(compileResult.getProgFile());
        parentCtx.globalProps.put(Constants.JMS_SESSION_ACKNOWLEDGEMENT_MODE,  javax.jms.Session.CLIENT_ACKNOWLEDGE);

        //Create the Callback
        TestAcknowledgementCallback jmsCallback = new TestAcknowledgementCallback(null);
        JMSConnectorFutureListener future = new JMSConnectorFutureListener(jmsCallback);

        //Construct the JMS Message
        BStruct messageStruct = BTestUtils.createAndGetStruct(compileResult.getProgFile(), 
                Constants.PROTOCOL_PACKAGE_JMS, 
                Constants.JMS_MESSAGE_STRUCT_NAME);
        BValue[] args = {messageStruct, new BString("SUCCESS") };
        
        //Execute
        BTestUtils.invoke(compileResult, "jmsMessageCreate", args, parentCtx, future);

        Assert.assertTrue(jmsCallback.isAcknowledged(), "JMS message is not acknowledged properly");
    }
}
