package com.ipctest.guiapp;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import com.ipctest.guiapp.utils.Clock;
import com.libapp.lib.IWildfireCentral;
import com.libapp.lib.WildfireCentral;

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
			WildfireCentral.resetProxy();
			WildfireCentral.getProxy();
		}

		clock.log();
	}

	@LargeTest
	public void test_AidlInvoke() throws Exception {
		IWildfireCentral wildfireCentral = WildfireCentral.getProxy();

		Clock clock = new Clock(TAG, getName(), ITERATIONS);

		for (int index = 0; index < ITERATIONS; ++index) {
			wildfireCentral.getRequestsCount();
		}

		clock.log();
	}

	@LargeTest
	public void test_getProxyInvoke() throws Exception {
		WildfireCentral.getProxy();

		Clock clock = new Clock(TAG, getName(), ITERATIONS);

		for (int index = 0; index < ITERATIONS; ++index) {
			WildfireCentral.getProxy().getRequestsCount();
		}

		clock.log();
	}

	@LargeTest
	public void test_contentProviderInvoke() throws Exception {
		Clock clock = new Clock(TAG, getName(), ITERATIONS);

		for (int index = 0; index < ITERATIONS; ++index) {
			WildfireCentral.getRequestsCount();
		}

		clock.log();
	}

}
