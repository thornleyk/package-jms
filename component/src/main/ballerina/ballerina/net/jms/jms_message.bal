package ballerina.net.jms;

@Description { value:"Represents an JMS message"}
public struct JMSMessage {
}

@Description { value:"Message acknowledgement action implementation for jms connector when using jms client acknowledgement mode"}
@Param { value:"message: message" }
@Param { value:"deliveryStatus: Specify whether message delivery is SUCCESS or ERROR" }
public native function <JMSMessage msg> acknowledge (string deliveryStatus);

@Description { value:"Session rollback action implementation for jms connector when using jms session transaction mode"}
@Param { value:"message: message" }
public native function <JMSMessage msg> rollback ();

@Description { value:"Session commit action implementation for jms connector when using jms session transaction mode"}
@Param { value:"message: message" }
public native function <JMSMessage msg> commit ();

@Description { value:"Sets a JMS transport string property from the message"}
@Param { value:"key: The string property name" }
@Param { value:"value: The string property value" }
public native function <JMSMessage msg> setStringProperty (string key, string value);

@Description { value:"Gets a JMS transport string property from the message"}
@Param { value:"key: The string property name" }
@Return { value:"string: The string property value" }
public native function <JMSMessage msg> getStringProperty (string key) (string);

@Description { value:"Sets a JMS transport integer property from the message"}
@Param { value:"key: The integer property name" }
@Param { value:"value: The integer property value" }
public native function <JMSMessage msg> setIntProperty (string key, int value);

@Description { value:"Gets a JMS transport integer property from the message"}
@Param { value:"key: The integer property name" }
@Return { value:"int: The integer property value" }
public native function <JMSMessage msg> getIntProperty (string key) (int);

@Description { value:"Sets a JMS transport boolean property from the message"}
@Param { value:"key: The boolean property name" }
@Param { value:"value: The boolean property value" }
public native function <JMSMessage msg> setBooleanProperty (string key, boolean value);

@Description { value:"Gets a JMS transport boolean property from the message"}
@Param { value:"key: The boolean property name" }
@Return { value:"boolean: The boolean property value" }
public native function <JMSMessage msg> getBooleanProperty (string key) (boolean);

@Description { value:"Sets a JMS transport float property from the message"}
@Param { value:"key: The float property name" }
@Param { value:"value: The float property value" }
public native function <JMSMessage msg> setFloatProperty (string key, float value);

@Description { value:"Gets a JMS transport float property from the message"}
@Param { value:"key: The float property name" }
@Return { value:"float: The float property value" }
public native function <JMSMessage msg> getFloatProperty (string key) (float);

@Description { value:"Sets text content for the JMS message"}
@Param { value:"content: Text Message Content" }
public native function <JMSMessage msg> setTextMessageContent (string content);

@Description { value:"Gets text content of the JMS message"}
@Return { value:"string: Text Message Content" }
public native function <JMSMessage msg> getTextMessageContent () (string);

@Description { value:"Sets bytes content for the JMS message"}
@Param { value:"content: Bytes Message Content" }
public native function <JMSMessage msg> setBytesMessageContent (blob content);

@Description { value:"Get bytes content of the JMS message"}
@Return { value:"string: Bytes Message Content" }
public native function <JMSMessage msg> getBytesMessageContent () (blob);

@Description { value:"Get JMS transport header MessageID from the message"}
@Return { value:"string: The header value" }
public native function <JMSMessage msg> getMessageID() (string);

@Description { value:"Get JMS transport header Timestamp from the message"}
@Return { value:"int: The header value" }
public native function <JMSMessage msg> getTimestamp() (int);

@Description { value:"Sets DeliveryMode JMS transport header to the message"}
@Param { value:"i: The header value" }
public native function <JMSMessage msg> setDeliveryMode(int i);

@Description { value:"Get JMS transport header DeliveryMode from the message"}
@Return { value:"int: The header value" }
public native function <JMSMessage msg> getDeliveryMode() (int);

@Description { value:"Sets Expiration JMS transport header to the message"}
@Param { value:"i: The header value" }
public native function <JMSMessage msg> setExpiration(int i);

@Description { value:"Get JMS transport header Expiration from the message"}
@Return { value:"int: The header value" }
public native function <JMSMessage msg> getExpiration() (int);

@Description { value:"Sets Priority JMS transport header to the message"}
@Param { value:"i: The header value" }
public native function <JMSMessage msg> setPriority(int i);

@Description { value:"Get JMS transport header Priority from the message"}
@Return { value:"int: The header value" }
public native function <JMSMessage msg> getPriority() (int);

@Description { value:"Get JMS transport header Redelivered from the message"}
@Return { value:"boolean: The header value" }
public native function <JMSMessage msg> getRedelivered() (boolean);

@Description { value:"Sets CorrelationID JMS transport header to the message"}
@Param { value:"s: The header value" }
public native function <JMSMessage msg> setCorrelationID(string s);

@Description { value:"Get JMS transport header CorrelationID from the message"}
@Return { value:"string: The header value" }
public native function <JMSMessage msg> getCorrelationID() (string);

@Description { value:"Sets Type JMS transport header to the message"}
@Param { value:"s: The header value" }
public native function <JMSMessage msg> setType(string s);

@Description { value:"Get JMS transport header Type from the message"}
@Return { value:"string: The header value" }
public native function <JMSMessage msg> getType() (string);

@Description { value:"Sets ReplyTo JMS destinaiton name to the message"}
@Param { value:"s: The header value" }
public native function <JMSMessage msg> setReplyTo(string s);

@Description { value:"Get ReplyTo JMS destinaiton name from the message"}
@Return { value:"string: The header value" }
public native function <JMSMessage msg> getReplyTo() (string);

@Description { value:"Clear JMS properties of the message"}
public native function <JMSMessage msg> clearProperties();

@Description { value:"Clear body JMS of the message"}
public native function <JMSMessage msg> clearBody();
