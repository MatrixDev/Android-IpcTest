package com.ipctest.guiapp.utils;

import android.util.Log;

/**
 * @author rostyslav.lesovyi
 */
public class Clock {

	private String mTag;
	private String mName;
	private long mTime;
	private long mIterations;

	public Clock(String tag, String name, long iterations) {
		mTag = tag;
		mName = name;
		mIterations = iterations;
		mTime = System.nanoTime();
	}

	public void log() {
		long time = (System.nanoTime() - mTime) / mIterations;
		Log.i(mTag, String.format("%10s -> %.03fms", mName, time / 1000000f));
	}
}
