package org.ballerinalang.net.jms;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.model.values.BStruct;

/**
 * {@code DataContext} is the wrapper to hold {@code Context} and {@code CallableUnitCallback}.
 */
public class DataContext {
    public Context context;
    public CallableUnitCallback callback;

    public DataContext(Context context, CallableUnitCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void notifyReply(BStruct response, BStruct jmsConnectorError) {
        context.setReturnValues(response, jmsConnectorError);
        callback.notifySuccess();
    }
}