package dev.matrix.ipctest.gui;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import dev.matrix.ipctest.gui.utils.Clock;
import dev.matrix.ipctest.lib.Central;
import dev.matrix.ipctest.lib.ICentral;

/**
 * @author rostyslav.lesovyi
 */
public class IpcTest extends AndroidTestCase {

	private static final int ITERATIONS = 10000;
	private static final String TAG = IpcTest.class.getSimpleName() + "/TAG";

	@LargeTest
	public void test_createProxy() throws Exception {
		Clock clock = new Clock(TAG, getName(), ITERATIONS);

		for (int index = 0; index < ITERATIONS; ++index) {
			Central.resetProxy();
			Central.getProxy();
		}

		clock.log();
	}

	@LargeTest
	public void test_AidlInvoke() throws Exception {
		ICentral wildfireCentral = Central.getProxy();

		Clock clock = new Clock(TAG, getName(), ITERATIONS);

		for (int index = 0; index < ITERATIONS; ++index) {
			wildfireCentral.getRequestsCount();
		}

		clock.log();
	}

	@LargeTest
	public void test_getProxyInvoke() throws Exception {
		Central.getProxy();

		Clock clock = new Clock(TAG, getName(), ITERATIONS);

		for (int index = 0; index < ITERATIONS; ++index) {
			Central.getProxy().getRequestsCount();
		}

		clock.log();
	}

	@LargeTest
	public void test_contentProviderInvoke() throws Exception {
		Clock clock = new Clock(TAG, getName(), ITERATIONS);

		for (int index = 0; index < ITERATIONS; ++index) {
			Central.getRequestsCount();
		}

		clock.log();
	}

}
