package ballerina.net.jms;
import ballerina.util;

@Description { value:"JMS client connector to poll messages to the JMS provider."}
@Param { value:"clientProperties: Pre-defind and additional properties for the connector"}
public connector ClientConnector (ClientProperties clientProperties) {

    string connectorID = util:uuid();

    @Description {value:"SEND action implementation of the JMS Connector"}
    @Param {value:"destinationName: Destination Name"}
    @Param {value:"message: Message"}
    native action send (string destinationName, JMSMessage m);

    @Description {value:"POLL action implementation of the JMS Connector"}
    @Param {value:"destinationName: Destination Name"}
    @Param {value:"time: Timeout that needs to blocked on"}
    native action poll (string destinationName, int time) (JMSMessage);

    @Description {value:"POLL action implementation with selector support of the JMS Connector"}
    @Param {value:"destinationName: Destination Name"}
    @Param {value:"time: Timeout that needs to blocked on"}
    @Param {value:"selector: Selector to filter out messages"}
    native action pollWithSelector (string destinationName, int time, string selector) (JMSMessage);

}