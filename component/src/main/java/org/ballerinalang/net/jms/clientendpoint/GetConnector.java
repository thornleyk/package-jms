/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.ballerinalang.net.jms.clientendpoint;
import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Get the client endpoint.
 *
 * @since 0.966
 */

@BallerinaFunction(packageName = "ballerina.net.jms",
                    functionName = "getConnector",
                    receiver = @Receiver(type = TypeKind.STRUCT, structType = "Client",
                    structPackage = "ballerina.net.jms"),
                    returnType = {@ReturnType(type = TypeKind.CONNECTOR)},
                    isPublic = true
)
public class GetConnector extends BlockingNativeCallableUnit {
    private static final Logger log = LoggerFactory.getLogger(GetConnector.class);

    @Override
    public void execute(Context context) {
        log.info("execute");

        BConnector clientConnector = null;
        BStruct clientEndPoint = (BStruct) context.getRefArgument(0);
        BStruct clientEndpointConfig = (BStruct) clientEndPoint.getRefField(0);
        if (clientEndPoint.getNativeData("BConnector") != null) {
            clientConnector = (BConnector) clientEndPoint.getNativeData("BConnector");
        } else {
            clientConnector = BLangConnectorSPIUtil.createBConnector(context.getProgramFile(), "ballerina.net.jms",
                    "ClientConnector", clientEndpointConfig.getStringField(0), clientEndpointConfig);
        }
        clientConnector.setNativeData("ClientConnector", clientEndPoint
                .getNativeData("ClientConnector"));
        context.setReturnValues(clientConnector);
    }
}