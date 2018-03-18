package org.ballerinalang.net.jms;
import org.ballerinalang.bre.Context;
import org.ballerinalang.model.NativeCallableUnit;
import org.ballerinalang.bre.bvm.CallableUnitCallback;
import org.ballerinalang.model.values.BValue;

public abstract class AbstractNonBlockingAction implements NativeCallableUnit {
    public void execute(Context context) {
        this.execute(context, null);
    }
    public abstract void execute(Context context, CallableUnitCallback callback);

    /**
     * Returns whether this callable unit is executed in blocking manner or not.
     * 
     * @return Flag indicating whether the callable unit is executed in blocking manner or not.
     */
    public boolean isBlocking() {
        return false;
    }
    /**
     * Util method to construct BValue arrays.
     *
     * @param values BValues to construct the array
     * @return Array of BValues
     */
    public BValue[] getBValues(BValue... values) {
        return values;
    }
}