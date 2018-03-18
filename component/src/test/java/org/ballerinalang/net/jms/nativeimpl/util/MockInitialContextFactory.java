package org.ballerinalang.net.jms.nativeimpl.util;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;

import static org.mockito.Mockito.*;

public class MockInitialContextFactory implements InitialContextFactory {

	private static final Context context = mock(Context.class);

	public static Context getInitialContext() {
		return context;
	}

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) {
		return context;
	}

}