package ballerina.net.jms;

@Description { value:"Create JMS Text Message based on client connector"}
@Param { value:"jmsClient: clientConnector" }
public native function createTextMessage (ClientProperties jmsClient) (JMSMessage);

@Description { value:"Create JMS Bytes Message based on client connector"}
@Param { value:"jmsClient: clientConnector" }
public native function createBytesMessage (ClientProperties jmsClient) (JMSMessage);

@Description { value:"Value for persistent JMS message delivery mode"}
public const int PERSISTENT_DELIVERY_MODE = 2;

@Description { value:"Value for non persistent JMS message delivery mode"}
public const int NON_PERSISTENT_DELIVERY_MODE = 1;

@Description { value:"Value to use when acknowledge jms messages for success"}
public const string DELIVERY_SUCCESS = "Success";

@Description { value:"Value to use when acknowledge jms messages for errors"}
public const string DELIVERY_ERROR = "Error";

@Description { value:"Value for auto acknowledgement mode (default)"}
public const string AUTO_ACKNOWLEDGE = "AUTO_ACKNOWLEDGE";

@Description { value:"Value for client acknowledge mode"}
public const string CLIENT_ACKNOWLEDGE = "CLIENT_ACKNOWLEDGE";

@Description { value:"Value for dups ok acknowledgement mode"}
public const string DUPS_OK_ACKNOWLEDGE = "DUPS_OK_ACKNOWLEDGE";

@Description { value:"Value for Session transacted mode"}
public const string SESSION_TRANSACTED = "SESSION_TRANSACTED";

@Description { value:"Value for XA transcted mode"}
public const string XA_TRANSACTED = "XA_TRANSACTED";

@Description { value:"Value for JMS Queue type"}
public const string TYPE_QUEUE = "queue";

@Description { value:"Value for JMS Topic type"}
public const string TYPE_TOPIC = "topic";
