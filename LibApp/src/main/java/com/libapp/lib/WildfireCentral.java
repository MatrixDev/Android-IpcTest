package com.libapp.lib;

import android.net.Uri;
import android.os.Bundle;
import com.libapp.lib.utils.ParcelableBinder;

/**
 * @author rostyslav.lesovyi
 */
public class WildfireCentral {

	public static final Uri AUTHORITY = Uri.parse("content://com.wildfire.central/");
	public enum Method {
		GetServiceBinder,
		GetRequestsCount,
		SendRandomRequest,
		ForceCrash
	}

	// sync service connection

	private static IWildfireCentral sWildfireCentral;

	public static IWildfireCentral getProxy() {
		if (sWildfireCentral == null || !sWildfireCentral.asBinder().pingBinder()) {
			Bundle bundle = BaseApp.self().getContentResolver().call(AUTHORITY, Method.GetServiceBinder.name(), null, null);
			bundle.setClassLoader(ParcelableBinder.class.getClassLoader());
			sWildfireCentral = IWildfireCentral.Stub.asInterface(ParcelableBinder.getBinder(bundle, "binder"));
		}
		return sWildfireCentral;
	}

	public static void resetProxy() {
		sWildfireCentral = null;
	}

	// sync content provider invokes

	public static int getRequestsCount() {
		return BaseApp.self().getContentResolver().call(AUTHORITY, Method.GetRequestsCount.name(), null, null)
				.getInt("count");
	}

	public static void sendRandomRequest(long userTime, String inputArg, ICallback callback) {
		Bundle bundle = new Bundle();
		bundle.putLong("userTime", userTime);
		bundle.putParcelable("callback", new ParcelableBinder(callback));

		BaseApp.self().getContentResolver().call(AUTHORITY, Method.SendRandomRequest.name(), inputArg, bundle);
	}

	// helpers

	public static void forceCrash() {
		BaseApp.self().getContentResolver().call(AUTHORITY, Method.ForceCrash.name(), null, null);
	}

}
