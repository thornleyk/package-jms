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

package org.ballerinalang.net.jms.clientendpoint;

import java.util.Map;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.connector.api.Struct;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.net.jms.JMSUtils;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.transport.jms.contract.JMSClientConnector;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.impl.JMSConnectorFactoryImpl;
/**
 * {@code Init} is the Init action implementation of the JMS Connector.
 *
 * @since 0.9
 */
@BallerinaFunction(
        packageName = "ballerina.net.jms",
        functionName = "initEndpoint",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "Client",
                structPackage = "ballerina.net.jms"),
        args = {@Argument(name = "epName", type = TypeKind.STRING),
                @Argument(name = "config", type = TypeKind.STRUCT, structType = "ClientProperties")},
        isPublic = true
)
public class Init extends BlockingNativeCallableUnit {
    private static final Logger log = LoggerFactory.getLogger(Init.class);

    private JMSConnectorFactoryImpl factory = new JMSConnectorFactoryImpl();
    @Override
    public void execute(Context context) {
        log.info("execute");
        Struct clientEndpoint = BLangConnectorSPIUtil.getConnectorEndpointStruct(context);
        Struct clientEndpointConfig = clientEndpoint.getStructField("config");
        Map<String, String> propertyMap = JMSUtils.preProcessJmsConfig(clientEndpointConfig);
        for (String name: propertyMap.keySet()){

                String key =name.toString();
                String value = propertyMap.get(name).toString();  
                System.out.println(key + " " + value);  
    
    
        } 
        
        try {
                JMSClientConnector jmsClientConnector = new JMSConnectorFactoryImpl().createClientConnector(propertyMap);
                clientEndpoint.addNativeData("ClientConnector", jmsClientConnector);
                log.info("Config"+ jmsClientConnector);

        } catch (JMSConnectorException e) {
                log.error("failed to create jms client connector. " + e.getMessage());
                throw new BallerinaException("failed to create jms client connector. " + e.getMessage(), e, context);
        } catch (Exception e) {
                e.printStackTrace();
                log.error("failed to create jms client connector. " + e.getMessage());

        }

        context.setReturnValues();
    }
}
