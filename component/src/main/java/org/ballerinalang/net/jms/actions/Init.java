/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.net.jms.actions;

import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.nativeimpl.actions.ClientConnectorFuture;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.net.jms.Constants;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.wso2.transport.jms.contract.JMSClientConnector;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.impl.JMSConnectorFactoryImpl;

import java.util.Map;

/**
 * {@code Init} is the Init action implementation of the JMS Connector.
 *
 * @since 0.9
 */
@BallerinaAction(packageName = "ballerina.net.jms",
                 actionName = "<init>",
                 connectorName = Constants.CONNECTOR_NAME,
                 args = {
                         @Argument(name = "c",
                                   type = TypeKind.CONNECTOR)
                 },
                 connectorArgs = {
                         @Argument(name = "properties",
                                   type = TypeKind.STRUCT)
                 })
public class Init extends AbstractJMSAction {

    @Override
    public ConnectorFuture execute(Context context) {
        BConnector bConnector = (BConnector) getRefArgument(context, 0);
        validateParams(bConnector);

        // Create the JMS Transport Client Connector and store it as a native data in the Ballerina JMS Client
        // Connector under the key of JMS_TRANSPORT_CLIENT_CONNECTOR
        // When performing an action this native object can be retrieved
        BStruct  connectorConfig = ((BStruct) bConnector.getRefField(0));
        Map<String, String> propertyMap = JMSUtils.preProcessJmsConfig(connectorConfig);

        try {
            JMSClientConnector jmsClientConnector = new JMSConnectorFactoryImpl().createClientConnector(propertyMap);
            bConnector.setNativeData(Constants.JMS_TRANSPORT_CLIENT_CONNECTOR, jmsClientConnector);
        } catch (JMSConnectorException e) {
            throw new BallerinaException("failed to create jms client connector. " + e.getMessage(), e, context);
        }

        ClientConnectorFuture future = new ClientConnectorFuture();
        future.notifySuccess();
        return future;
    }

    private boolean validateParams(BConnector connector) {
        if ((connector != null)
                && (connector.getRefField(0) != null) && (connector.getRefField(0) instanceof BStruct)) {
            return true;
        } else {
            throw new BallerinaException("Connector parameters not defined correctly.");
        }
    }

    @Override
    public boolean isNonBlockingAction() {
        return false;
    }
}
