package com.serviceapp.app;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import com.libapp.lib.ICallback;
import com.libapp.lib.WildfireCentral;
import com.libapp.lib.utils.ParcelableBinder;
import com.serviceapp.app.utils.SimpleContentProvider;

/**
 * @author rostyslav.lesovyi
 */
public class WildfireCentralProvider extends SimpleContentProvider {
	@Override
	public boolean onCreate() {
		getContext().startService(new Intent(getContext(), WildfireCentralService.class));
		return true;
	}

	@Override
	public Bundle call(String method, String arg, Bundle extras) {
		if (extras != null) {
			extras.setClassLoader(ParcelableBinder.class.getClassLoader());
		}
		switch (WildfireCentral.Method.valueOf(method)) {
			case GetServiceBinder:
				Bundle bundle = new Bundle();
				bundle.putParcelable("binder", new ParcelableBinder((IBinder) WildfireCentralBinder.self));
				return bundle;

			case GetRequestsCount:
				bundle = new Bundle();
				bundle.putInt("count", WildfireCentralBinder.self.getRequestsCount());
				return bundle;

			case SendRandomRequest:
				long userTime = extras.getLong("userTime");
				ICallback callback = ICallback.Stub.asInterface(ParcelableBinder.getBinder(extras, "callback"));
				WildfireCentralBinder.self.sendRandomRequest(userTime, arg, callback);
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
