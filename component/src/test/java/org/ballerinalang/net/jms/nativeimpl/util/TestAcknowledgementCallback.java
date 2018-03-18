/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.net.jms.nativeimpl.util;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.transport.jms.callback.JMSCallback;

/**
 * Acknowledgement Callback for testing service/resource invocations.
 */
public class TestAcknowledgementCallback extends JMSCallback implements FutureWaiter {
    private static final Logger log = LoggerFactory.getLogger(TestAcknowledgementCallback.class);

    private boolean acknowledged = false;

    private boolean complete = false;

    private boolean reseted = false;

    public TestAcknowledgementCallback(Session session) {
        super(session);
    }

    @Override
    public void done(boolean status) {
        if (status) {
            acknowledged = true;
        } else {
            reseted = true;
        }
        complete = true;
    }

    @Override
    public int getAcknowledgementMode() {
        return Session.CLIENT_ACKNOWLEDGE;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public boolean isReseted() {
        return reseted;
    }

    public boolean isComplete() {
        return complete;
    }
}
