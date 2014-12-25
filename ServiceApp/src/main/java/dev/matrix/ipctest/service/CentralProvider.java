package dev.matrix.ipctest.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import dev.matrix.ipctest.lib.Central;
import dev.matrix.ipctest.lib.ICallback;
import dev.matrix.ipctest.lib.utils.ParcelableBinder;
import dev.matrix.ipctest.service.utils.SimpleContentProvider;

/**
 * @author rostyslav.lesovyi
 */
public class CentralProvider extends SimpleContentProvider {
	@Override
	public boolean onCreate() {
		getContext().startService(new Intent(getContext(), CentralService.class));
		return true;
	}

	@Override
	public Bundle call(String method, String arg, Bundle extras) {
		if (extras != null) {
			extras.setClassLoader(ParcelableBinder.class.getClassLoader());
		}
		switch (Central.Method.valueOf(method)) {
			case GetServiceBinder:
				Bundle bundle = new Bundle();
				bundle.putParcelable("binder", new ParcelableBinder((IBinder) CentralBinder.self));
				return bundle;

			case GetRequestsCount:
				bundle = new Bundle();
				bundle.putInt("count", CentralBinder.self.getRequestsCount());
				return bundle;

			case SendRandomRequest:
				long userTime = extras.getLong("userTime");
				ICallback callback = ICallback.Stub.asInterface(ParcelableBinder.getBinder(extras, "callback"));
				CentralBinder.self.sendRandomRequest(userTime, arg, callback);
				return null;

			case ForceCrash:
				App.handler.post(new Runnable() {
					@Override
					public void run() {
						throw new RuntimeException("CRASH =)");
					}
				});
				return null;
		}
		return super.call(method, arg, extras);
	}
}
