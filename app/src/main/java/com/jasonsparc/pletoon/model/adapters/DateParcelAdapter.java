package com.jasonsparc.pletoon.model.adapters;

import android.os.Parcel;

import com.ryanharter.auto.value.parcel.TypeAdapter;

import java.util.Date;

public class DateParcelAdapter implements TypeAdapter<Date> {

	public Date fromParcel(Parcel in) {
		final long time = in.readLong();
		return time >= 0 ? new Date(time) : null;
	}

	public void toParcel(Date value, Parcel dest) {
		dest.writeLong(value != null ? value.getTime() : -1);
	}
}
