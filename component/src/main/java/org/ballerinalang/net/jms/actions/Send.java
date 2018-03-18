/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ballerinalang.net.jms.actions;

import javax.jms.Message;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.net.jms.AbstractNonBlockingAction;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSTransactionUtil;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.transport.jms.contract.JMSClientConnector;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.sender.wrappers.SessionWrapper;
import org.wso2.transport.jms.utils.JMSConstants;

/**
 * {@code Send} is the send action implementation of the JMS Connector.
 */
@BallerinaFunction(
    packageName = "ballerina.net.jms",
    functionName = "send",
    receiver = @Receiver(type = TypeKind.STRUCT, structType = "ClientConnector",
            structPackage = "ballerina.net.jms"),
    args = {
            @Argument(name = "destinationName", type = TypeKind.STRING),
            @Argument(name = "message", type = TypeKind.STRUCT)
    }
)
public class Send extends AbstractNonBlockingAction {
    private static final Logger log = LoggerFactory.getLogger(Send.class);

    @Override
    public void execute(Context context,  CallableUnitCallback callback) {
        log.info("execute sending JMS Message to ");

        // Extract argument values
        BConnector bConnector = (BConnector) context.getRefArgument(0);
        BStruct messageStruct = ((BStruct) context.getRefArgument(1));
        String destination = context.getStringArgument(0);

        // Retrieve the ack mode from jms client configuration
        BStruct connectorConfig = ((BStruct) bConnector.getRefField(0));
        String acknowledgementMode = connectorConfig.getStringField(Constants.CLIENT_CONFIG_ACK_FIELD_INDEX);

        // Retrieve transport client
        JMSClientConnector jmsClientConnector = (JMSClientConnector) bConnector
                .getNativeData(Constants.JMS_TRANSPORT_CLIENT_CONNECTOR);

        String connectorKey = bConnector.getStringField(0);

        Message jmsMessage = JMSUtils.getJMSMessage(messageStruct);

        try {
            //if (log.isDebugEnabled()) {
                log.info("sending JMS Message to " + destination);
           //}
            // Add ReplyTo header to the message
            JMSUtils.updateReplyToDestination(messageStruct, jmsClientConnector);

            if (JMSConstants.SESSION_TRANSACTED_MODE.equalsIgnoreCase(acknowledgementMode)
                    || JMSConstants.XA_TRANSACTED_MODE.equalsIgnoreCase(acknowledgementMode)) {
                // if the action is not called inside a transaction block
                if (!context.isInTransaction()) {
                    throw new BallerinaException(
                            "jms transacted send action should perform inside a transaction block ", context);
                }
            SessionWrapper sessionWrapper = JMSTransactionUtil.getTxSession(context, jmsClientConnector, connectorKey);
            jmsClientConnector.sendTransactedMessage(jmsMessage, destination, sessionWrapper);
            } else {
                jmsClientConnector.send(jmsMessage, destination);
            }
        } catch (JMSConnectorException e) {
            throw new BallerinaException("failed to send message. " + e.getMessage(), e, context);
        }
        callback.notifySuccess();
    }
}
