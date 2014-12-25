package com.serviceapp.app;

import android.os.RemoteException;
import com.libapp.lib.ICallback;
import com.libapp.lib.IWildfireCentral;

/**
 * @author rostyslav.lesovyi
 */
public class WildfireCentralBinder extends IWildfireCentral.Stub {

	public static final WildfireCentralBinder self = new WildfireCentralBinder();

	private int mRequestsCount = 0;

	@Override
	public int getRequestsCount() {
		return mRequestsCount;
	}

	@Override
	public void sendRandomRequest(final long userTime, final String inputArg, final ICallback callback) {
		App.handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					callback.onCallbackResult(userTime, inputArg + " handled by WildfireCentralBinder");
				} catch (RemoteException ignored) {
				}
				++mRequestsCount;
			}
		}, 1000);
	}
}
