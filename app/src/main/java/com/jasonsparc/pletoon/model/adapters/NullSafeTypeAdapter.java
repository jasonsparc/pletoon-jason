package com.jasonsparc.pletoon.model.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Wraps a {@link TypeAdapter} and ensures that all its operations are null safe.
 * <p>
 * Created by jasonsparc on 5/22/2016.
 */
public class NullSafeTypeAdapter<T> extends TypeAdapter<T> {
	final TypeAdapter<T> base;

	public static <T> NullSafeTypeAdapter<T> wrap(TypeAdapter<T> base) {
		return base == null ? null : new NullSafeTypeAdapter<>(base);
	}

	protected NullSafeTypeAdapter(TypeAdapter<T> base) {
		if (base instanceof NullSafeTypeAdapter)
			base = ((NullSafeTypeAdapter<T>) base).base;
		this.base = base;
	}

	@Override
	public void write(JsonWriter out, T value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		base.write(out, value);
	}

	@Override
	public T read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		return base.read(in);
	}
}
