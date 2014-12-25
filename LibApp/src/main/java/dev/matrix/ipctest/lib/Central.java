package dev.matrix.ipctest.lib;

import android.net.Uri;
import android.os.Bundle;
import dev.matrix.ipctest.lib.utils.ParcelableBinder;

/**
 * @author rostyslav.lesovyi
 */
public class Central {

	public static final Uri AUTHORITY = Uri.parse("content://dev.matrix.central/");
	public enum Method {
		GetServiceBinder,
		GetRequestsCount,
		SendRandomRequest,
		ForceCrash
	}

	// sync service connection

	private static ICentral sCentral;

	public static ICentral getProxy() {
		if (sCentral == null || !sCentral.asBinder().pingBinder()) {
			Bundle bundle = BaseApp.self().getContentResolver().call(AUTHORITY, Method.GetServiceBinder.name(), null, null);
			bundle.setClassLoader(ParcelableBinder.class.getClassLoader());
			sCentral = ICentral.Stub.asInterface(ParcelableBinder.getBinder(bundle, "binder"));
		}
		return sCentral;
	}

	public static void resetProxy() {
		sCentral = null;
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
