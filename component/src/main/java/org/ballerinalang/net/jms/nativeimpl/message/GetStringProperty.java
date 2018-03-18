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

package org.ballerinalang.net.jms.nativeimpl.message;

import javax.jms.JMSException;
import javax.jms.Message;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.jms.AbstractBlockingAction;
import org.ballerinalang.net.jms.JMSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Get String Property from the JMS Message.
 */
@BallerinaFunction(
        packageName = "ballerina.net.jms",
        functionName = "getStringProperty",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "JMSMessage",
                             structPackage = "ballerina.net.jms"),
        args = {@Argument(name = "propertyName", type = TypeKind.STRING)},
        returnType = {@ReturnType(type = TypeKind.STRING)},
        isPublic = true
)
public class GetStringProperty extends AbstractBlockingAction {

    private static final Logger log = LoggerFactory.getLogger(GetStringProperty.class);

    public void execute(Context context) {

        BStruct messageStruct  = ((BStruct) context.getRefArgument(0));
        String propertyName = context.getStringArgument(0);

        Message jmsMessage = JMSUtils.getJMSMessage(messageStruct);

        String propertyValue = null;
        try {
            propertyValue = jmsMessage.getStringProperty(propertyName);
        } catch (JMSException e) {
            log.error("Error when retrieving the string property :" + e.getLocalizedMessage());
        }

        if (log.isDebugEnabled()) {
            log.debug("Get string property" + propertyName + " from message with value: " + propertyValue);
        }
        context.setReturnValues(new BString(propertyValue));
    }
}
