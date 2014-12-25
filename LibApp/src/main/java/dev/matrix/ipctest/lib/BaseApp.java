package dev.matrix.ipctest.lib;

import android.app.Application;
import android.os.Handler;

/**
 * @author rostyslav.lesovyi
 */
public class BaseApp extends Application {

	private static BaseApp sSelf;

	public static final Handler handler = new Handler();

	public BaseApp() {
		sSelf = this;
	}

	public static BaseApp self() {
		return sSelf;
	}
}
