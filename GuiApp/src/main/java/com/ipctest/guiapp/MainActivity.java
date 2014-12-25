package com.ipctest.guiapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.view.View;
import android.widget.Toast;
import com.libapp.lib.ICallback;
import com.libapp.lib.IWildfireCentral;
import com.libapp.lib.WildfireCentral;

public class MainActivity extends Activity implements View.OnClickListener, Handler.Callback {

	// Type1 -> bindService
	// Type2 -> CP proxy
	// Type3 -> CP invokes

	private Toast mToast;
	private Handler mHandler;

	private View mSendAsyncType1;
	private View mSendAsyncType2;
	private View mSendAsyncType3;

	private IWildfireCentral mWildfireCentral;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHandler = new Handler(this);
		mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);

		setContentView(R.layout.a_main);

		mSendAsyncType1 = findViewById(R.id.send_async_1);
		mSendAsyncType1.setOnClickListener(this);

		mSendAsyncType2 = findViewById(R.id.send_async_2);
		mSendAsyncType2.setOnClickListener(this);

		mSendAsyncType3 = findViewById(R.id.send_async_3);
		mSendAsyncType3.setOnClickListener(this);

		findViewById(R.id.requests_count_1).setOnClickListener(this);
		findViewById(R.id.requests_count_2).setOnClickListener(this);
		findViewById(R.id.requests_count_3).setOnClickListener(this);
		findViewById(R.id.crash).setOnClickListener(this);

		bindService(new Intent().setClassName("com.serviceapp.app", "com.serviceapp.app.WildfireCentralService"), new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mWildfireCentral = IWildfireCentral.Stub.asInterface(service);
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
			}
		}, BIND_AUTO_CREATE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.send_async_1:
				try {
					mWildfireCentral.sendRandomRequest(System.nanoTime(), "Type1 -> ", mAsyncCallbackType1);
					mSendAsyncType1.setEnabled(false);
				} catch (Exception ex) {
					mToast.setText(ex.getMessage());
					mToast.show();
				}
				break;

			case R.id.send_async_2:
				try {
					WildfireCentral.getProxy().sendRandomRequest(System.nanoTime(), "Type2 -> ", mAsyncCallbackType2);
					mSendAsyncType2.setEnabled(false);
				} catch (Exception ex) {
					mToast.setText(ex.getMessage());
					mToast.show();
				}
				break;

			case R.id.send_async_3:
				mSendAsyncType3.setEnabled(false);
				WildfireCentral.sendRandomRequest(System.nanoTime(), "Type3 -> ", mAsyncCallbackType3);
				break;

			case R.id.requests_count_1:
				try {
					long time = System.nanoTime();
					int count = mWildfireCentral.getRequestsCount();
					showToast(time, "Type1 -> Requests count: " + count);
				} catch (Exception ex) {
					mToast.setText(ex.getMessage());
					mToast.show();
				}
				break;

			case R.id.requests_count_2:
				try {
					long time = System.nanoTime();
					int count = WildfireCentral.getProxy().getRequestsCount();
					showToast(time, "Type2 -> Requests count: " + count);
				} catch (Exception ex) {
					mToast.setText(ex.getMessage());
					mToast.show();
				}
				break;

			case R.id.requests_count_3:
				long time = System.nanoTime();
				int count = WildfireCentral.getRequestsCount();
				showToast(time, "Type3 -> Requests count: " + count);
				break;

			case R.id.crash:
				WildfireCentral.forceCrash();
				break;
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case 1:
				mSendAsyncType1.setEnabled(true);
				break;

			case 2:
				mSendAsyncType2.setEnabled(true);
				break;

			case 3:
				mSendAsyncType3.setEnabled(true);
				break;
		}
		Object[] objects = (Object[]) msg.obj;
		showToast((Long) objects[0], String.valueOf(objects[1]));
		return true;
	}

	private void showToast(long startTime, String text) {
		float diff = (System.nanoTime() - startTime) / 1000000f;

		mToast.setText(String.format("%s | %.3fms", text, diff));
		mToast.show();
	}

	private ICallback mAsyncCallbackType1 = new ICallback.Stub() {
		@Override
		public void onCallbackResult(long userTime, String someResult) throws RemoteException {
			Object[] objects = { userTime, someResult };
			mHandler.obtainMessage(1, objects).sendToTarget();
		}
	};

	private ICallback mAsyncCallbackType2 = new ICallback.Stub() {
		@Override
		public void onCallbackResult(long userTime, String someResult) throws RemoteException {
			Object[] objects = { userTime, someResult };
			mHandler.obtainMessage(2, objects).sendToTarget();
		}
	};

	private ICallback mAsyncCallbackType3 = new ICallback.Stub() {
		@Override
		public void onCallbackResult(long userTime, String someResult) throws RemoteException {
			Object[] objects = { userTime, someResult };
			mHandler.obtainMessage(3, objects).sendToTarget();
		}
	};
}
