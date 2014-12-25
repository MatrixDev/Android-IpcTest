package com.libapp.lib.utils;

import android.os.*;

/**
 * @author rostyslav.lesovyi
 */
public class ParcelableBinder implements Parcelable {
	private IBinder mBinder;

	public ParcelableBinder(IBinder binder) {
		mBinder = binder;
	}

	public ParcelableBinder(IInterface binder) {
		this(binder != null ? binder.asBinder() : null);
	}

	public static IBinder getBinder(Bundle bundle, String key) {
		return ((ParcelableBinder) bundle.getParcelable(key)).mBinder;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeStrongBinder(mBinder);
	}

	public static final Creator<ParcelableBinder> CREATOR = new Creator<ParcelableBinder>() {
		@Override
		public ParcelableBinder createFromParcel(Parcel source) {
			return new ParcelableBinder(source.readStrongBinder());
		}

		@Override
		public ParcelableBinder[] newArray(int size) {
			return new ParcelableBinder[size];
		}
	};
}
