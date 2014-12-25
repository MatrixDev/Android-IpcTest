package dev.matrix.ipctest.service;

import android.os.RemoteException;
import dev.matrix.ipctest.lib.ICallback;
import dev.matrix.ipctest.lib.ICentral;

/**
 * @author rostyslav.lesovyi
 */
public class CentralBinder extends ICentral.Stub {

	public static final CentralBinder self = new CentralBinder();

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
