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

import org.wso2.transport.jms.callback.JMSCallback;

import javax.jms.Session;

/**
 * Callback for testing service/resource invocations.
 */
public class TestTransactionCallback extends JMSCallback implements FutureWaiter{

    private boolean commited = false;

    private boolean rollbacked = false;

    private boolean complete = false;

    public TestTransactionCallback(Session session) {
        super(session);
    }


    @Override
    public void done(boolean status) {
        if (status) {
            commited = true;
        } else {
            rollbacked = true;
        }
        complete = true;
    }

    @Override
    public int getAcknowledgementMode() {
        return Session.SESSION_TRANSACTED;
    }

    public boolean isCommited() {
        return commited;
    }

    public boolean isRollbacked() {
        return rollbacked;
    }

    public boolean isComplete() {
        return complete;
    }
}
