package org.ballerinalang.net.jms;

import org.ballerinalang.bre.Context;
import org.ballerinalang.util.transactions.BallerinaTransactionContext;
import org.ballerinalang.util.transactions.LocalTransactionInfo;
import org.ballerinalang.util.transactions.TransactionResourceManager;
import org.wso2.transport.jms.contract.JMSClientConnector;
import org.wso2.transport.jms.exception.JMSConnectorException;
import org.wso2.transport.jms.sender.wrappers.SessionWrapper;

public class JMSTransactionUtil {

    /**
     * Get tx SessionWrapper.
     * If the transaction context is already started, we can re-use the session, otherwise acquire a new SessionWrapper
     * from the transport.
     *
     * @param context Ballerina context.
     * @param jmsClientConnector transport level JMSClientConnector of this Ballerina Connector.
     * @param connectorKey Id of the Ballerina Connector.
     * @return
     * @throws JMSConnectorException Error when acquiring the session.
     */
    public static SessionWrapper getTxSession(Context context, JMSClientConnector jmsClientConnector, String connectorKey)
            throws JMSConnectorException {
        SessionWrapper sessionWrapper;
        TransactionResourceManager txManager = TransactionResourceManager.getInstance();
        LocalTransactionInfo localTransactionInfo = context.getLocalTransactionInfo();
        String globalTxId = localTransactionInfo.getGlobalTransactionId();
        int currentTxBlockId = localTransactionInfo.getCurrentTransactionBlockId();
        BallerinaTransactionContext txContext = localTransactionInfo.getTransactionContext(connectorKey);
        // if transaction initialization has not yet been done
        // (if this is the first transacted action happens from this particular connector within this
        // transaction block)       
        
        if (txContext == null) {
            sessionWrapper = jmsClientConnector.acquireSession();
            txContext = new JMSTransactionContext(sessionWrapper, jmsClientConnector);
            //Handle XA initialization
            if (txContext.getXAResource() != null) {
                txManager.beginXATransaction(globalTxId, currentTxBlockId, txContext.getXAResource());
            }
            context.getLocalTransactionInfo().registerTransactionContext(connectorKey, txContext);
        } else {
            sessionWrapper = ((JMSTransactionContext) txContext).getSessionWrapper();
        }
        return sessionWrapper;
    }
}