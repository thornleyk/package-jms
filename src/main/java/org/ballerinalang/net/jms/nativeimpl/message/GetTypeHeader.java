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

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.AbstractNativeFunction;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.Attribute;
import org.ballerinalang.natives.annotations.BallerinaAnnotation;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.jms.JMSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Get Type header from the JMS Message.
 */
@BallerinaFunction(
        packageName = "ballerina.net.jms.jmsmessage",
        functionName = "getType",
        args = {@Argument(name = "msg", type = TypeKind.STRUCT, structType = "JMSMessage",
                          structPackage = "ballerina.net.jms")},
        returnType = {@ReturnType(type = TypeKind.STRING)},
        isPublic = true
)
@BallerinaAnnotation(annotationName = "Description", attributes = {@Attribute(name = "value",
        value = "Get Type header from the message") })
@BallerinaAnnotation(annotationName = "Param", attributes = {@Attribute(name = "message",
        value = "The JMS message") })
@BallerinaAnnotation(annotationName = "Return", attributes = {@Attribute(name = "string",
        value = "The header value") })
public class GetTypeHeader extends AbstractNativeFunction {

    private static final Logger log = LoggerFactory.getLogger(GetTypeHeader.class);

    public BValue[] execute(Context context) {

        BStruct messageStruct  = ((BStruct) this.getRefArgument(context, 0));
        Message jmsMessage = JMSUtils.getJMSMessage(messageStruct);
        BValue[] headerValue = null;
        try {
            headerValue = getBValues(new BString(jmsMessage.getJMSType()));
        } catch (JMSException e) {
            log.error("Unable to retrieve Type from the JMS Message. " + e.getLocalizedMessage());
        }

        if (log.isDebugEnabled()) {
            log.debug("Get Type from message");
        }

        return headerValue;
    }
}
