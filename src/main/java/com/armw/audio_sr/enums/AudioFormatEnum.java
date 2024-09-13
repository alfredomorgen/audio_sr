package com.armw.audio_sr.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AudioFormatEnum {
	M4A("audio/mp4"),
	WAV("audio/wav"),
	;

	private final String mimeType;
}
