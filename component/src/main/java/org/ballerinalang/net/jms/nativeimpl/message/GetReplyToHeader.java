/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.jms.AbstractBlockingAction;
import org.ballerinalang.net.jms.BallerinaJMSMessage;
import org.ballerinalang.net.jms.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Get ReplyTo Destination name.
 *
 * @since 0.95.5
 */
@BallerinaFunction(packageName = "ballerina.net.jms",
                   receiver = @Receiver(type = TypeKind.STRUCT,
                                        structType = "JMSMessage",
                                        structPackage = "ballerina.net.jms"),
                   functionName = "getReplyTo",
                   returnType = { @ReturnType(type = TypeKind.STRING) },
                   isPublic = true)
public class GetReplyToHeader extends AbstractBlockingAction {

    private static final Logger log = LoggerFactory.getLogger(GetReplyToHeader.class);

    public void execute(Context context) {

        BStruct messageStruct = ((BStruct) context.getRefArgument(0));
        BallerinaJMSMessage ballerinaJMSMessage = (BallerinaJMSMessage) messageStruct
                .getNativeData(Constants.JMS_API_MESSAGE);
        BValue[] headerValue = getBValues(new BString(ballerinaJMSMessage.getReplyDestinationName()));

        if (log.isDebugEnabled()) {
            log.debug("get Reply destination name from the jms message");
        }

        context.setReturnValues(headerValue);
    }
}
