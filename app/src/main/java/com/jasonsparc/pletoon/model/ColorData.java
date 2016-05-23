package com.jasonsparc.pletoon.model;

import android.os.Parcelable;
import android.support.annotation.ColorInt;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by jasonsparc on 5/21/2016.
 */
@AutoValue
public abstract class ColorData implements Parcelable {
	private int mAsInt;

	@ColorInt
	public int asInt() {
		return mAsInt;
	}

	public abstract String asString();

	/**
	 * Parse the color string. If the string cannot be parsed, returns {@code null}.
	 * Supported formats are:
	 * <p>
	 * RGB ARGB RRGGBB AARRGGBB<BR>
	 * #RGB #ARGB #RRGGBB #AARRGGBB
	 * <p>
	 * Note: This is different from {@link android.graphics.Color#parseColor(String)} because it
	 * does not parse HTML-equivalent color names.
	 */
	public static ColorData parse(String colorString) {
		int length = colorString.length();

		if (length != 0 && colorString.charAt(0) == '#') {
			colorString = colorString.substring(1);
			length--;
		}

		int color;
		try {
			// Use a long to avoid rollovers on #ffXXXXXX
			color = (int) Long.parseLong(colorString, 16);
		} catch (IllegalArgumentException e) {
			return null;
		}
		switch (length) {
			default:
				return null;
			case 3:
				// Set the default alpha value
				color |= 0xF000;
			case 4:
				// nibble colors - to hex colors
				// 0x0000argb -> 0x0a0r0g0b
				// 0x0a0r0g0b | 0xa0r0g0b0 -> 0xaarrggbb
				color = ((color & 0xF000) << 12) | ((color & 0xF00) << 8) | ((color & 0xF0) << 4) | (color & 0xF);
				color = color | (color << 4);
				break;
			case 6:
				// Set the default alpha value
				color |= 0xFF000000;
			case 8:
		}

		ColorData colorData = new AutoValue_ColorData(colorString);
		colorData.mAsInt = color;
		return colorData;
	}

	public static TypeAdapter<ColorData> typeAdapter(Gson gson) {
		return new TypeAdapter<ColorData>() {
			@Override
			public void write(JsonWriter out, ColorData value) throws IOException {
				out.value(value.asString());
			}

			@Override
			public ColorData read(JsonReader in) throws IOException {
				return parse(in.nextString());
			}
		};
	}
}
