package ballerina.net.jms;

@Description {value:"Represents an JMS client endpoint"}
@Field {value:"epName: The name of the endpoint"}
@Field {value:"config: The configurations associated with the endpoint"}
public struct Client {
    string epName;
    ClientProperties config;
}

@Description { value:"JMS Client connector properties to pass JMS client connector configurations"}
@Field {value:"initialContextFactory: Initial context factory name, specific to the provider"}
@Field {value:"providerUrl: Connection URL of the provider"}
@Field {value:"connectionFactoryName: Name of the connection factory"}
@Field {value:"connectionFactoryType: Type of the connection factory (queue/topic)"}
@Field {value:"acknowledgementMode: Ack mode (auto-ack, client-ack, dups-ok-ack, transacted, xa)"}
@Field {value:"clientCaching: Is client caching enabled (default: enabled)"}
@Field {value:"connectionUsername: Connection factory username"}
@Field {value:"connectionPassword: Connection factory password"}
@Field {value:"configFilePath: Path to be used for locating jndi configuration"}
@Field {value:"connectionCount: Number of pooled connections to be used in the transport level (default: 5)"}
@Field {value:"sessionCount: Number of pooled sessions to be used per connection in the transport level (default: 10)"}
@Field {value:"properties: Additional Properties"}
public struct ClientProperties {
    string initialContextFactory;
    string providerUrl;
    string connectionFactoryName;
    string connectionFactoryType = "queue";
    string acknowledgementMode = "AUTO_ACKNOWLEDGE";
    boolean clientCaching = true;
    string connectionUsername;
    string connectionPassword;
    string configFilePath;
    int connectionCount = 5;
    int sessionCount = 10;
    map properties;
}

@Description {value:"Initializes the ClientProperties struct with default values."}
@Param {value:"config: The ClientProperties struct to be initialized"}
public function <ClientProperties config> ClientProperties() {

}

@Description { value:"Gets called when the endpoint is being initialized during the package initialization."}
@Param { value:"ep: The endpoint to be initialized" }
@Param { value:"epName: The endpoint name" }
@Param { value:"config: The ClientProperties of the endpoint" }
public function <Client ep> init (string epName, ClientProperties config) {
    ep.epName = epName;
    ep.config = config;
    ep.initEndpoint();
}

public native function<Client ep> initEndpoint ();

public function <Client ep> register (type serviceType) {

}

public function <Client ep> start () {

}

@Description { value:"Stops the registered service"}
@Return { value:"Error occured during registration" }
public function <Client ep> stop () {

}

@Description { value:"Returns the connector that client code uses"}
@Return { value:"The connector that client code uses" }
public native function <Client ep> getConnector () returns (ClientConnector repConn);