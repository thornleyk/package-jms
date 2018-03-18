/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.ballerinalang.net.jms;

import org.ballerinalang.util.transactions.BallerinaTransactionContext;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.transport.jms.contract.JMSClientConnector;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.sender.wrappers.SessionWrapper;
import org.wso2.transport.jms.sender.wrappers.XASessionWrapper;

import javax.jms.JMSException;
import javax.transaction.xa.XAResource;

/**
 * {@link JMSTransactionContext} to hold information regarding the transactional stage
 * inside a Context flow.
 */
public class JMSTransactionContext implements BallerinaTransactionContext {
    private static final Logger log = LoggerFactory.getLogger(JMSTransactionContext.class);
    private SessionWrapper sessionWrapper;
    private JMSClientConnector clientConnector;

    public JMSTransactionContext(SessionWrapper sessionWrapper, JMSClientConnector clientConnector) {
        this.sessionWrapper = sessionWrapper;
        this.clientConnector = clientConnector;
    }

    public SessionWrapper getSessionWrapper() {
        return this.sessionWrapper;
    }

    @Override
    public void commit() {
        try {
            if (sessionWrapper != null && sessionWrapper.getSession().getTransacted()) {
                sessionWrapper.getSession().commit();
            }
        } catch (JMSException e) {
            throw new BallerinaException("transaction commit failed: " + e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void rollback() {
        try {
            if (sessionWrapper != null && sessionWrapper.getSession().getTransacted()) {
                sessionWrapper.getSession().rollback();
            }
        } catch (JMSException e) {
            throw new BallerinaException("transaction rollback failed: " + e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void close() {
    }

    @Override
    public void done() {
        if (sessionWrapper != null) {
            try {
                clientConnector.releaseSession(sessionWrapper);
                sessionWrapper = null;
            } catch (JMSConnectorException e) {
                log.error("jms session release failed: " + e.getLocalizedMessage(), e);
            }
        }
    }

    @Override
    public XAResource getXAResource() {
        XAResource xaResource = null;
        if (sessionWrapper instanceof  XASessionWrapper) {
            xaResource = ((XASessionWrapper) sessionWrapper).getXASession().getXAResource();
        }
        return xaResource;
    }

}
