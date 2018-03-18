package ballerina.net.jms;
import ballerina.util;

@Description { value:"JMS client connector to poll messages to the JMS provider."}
public struct ClientConnector {
    //These properties are populated from the init call to the client connector as these were needed later stage
    //for retry and other few places.
    ClientProperties config;
}

public function <ClientConnector conn> ClientConnector() {
    string connectorID = util:uuid();
}

@Description {value:"SEND action implementation of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"message: Message"}
public native function <ClientConnector client> send (string destinationName, JMSMessage m);

@Description {value:"POLL action implementation of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"time: Timeout that needs to blocked on"}
public native function <ClientConnector client> poll (string destinationName, int time) (JMSMessage);

@Description {value:"POLL action implementation with selector support of the JMS Connector"}
@Param {value:"destinationName: Destination Name"}
@Param {value:"time: Timeout that needs to blocked on"}
@Param {value:"selector: Selector to filter out messages"}
public native function <ClientConnector client> pollWithSelector (string destinationName, int time, string selector) (JMSMessage);

