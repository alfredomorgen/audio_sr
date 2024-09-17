package com.armw.audio_sr.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AudioFormatEnum {
	M4A("audio/mp4"),
	WAV("audio/wav"),
	UNKNOWN(null);
	;

	private final String mimeType;

	static public AudioFormatEnum of(final String mimeType) {
		try {
			return AudioFormatEnum.valueOf(mimeType);
		} catch (final IllegalArgumentException e) {
			return UNKNOWN;
		}
	}
}
