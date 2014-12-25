package dev.matrix.ipctest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author rostyslav.lesovyi
 */
public class CentralService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return CentralBinder.self;
	}
}
