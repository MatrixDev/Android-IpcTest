package com.serviceapp.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author rostyslav.lesovyi
 */
public class WildfireCentralService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return WildfireCentralBinder.self;
	}
}
