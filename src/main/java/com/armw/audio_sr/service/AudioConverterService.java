package com.armw.audio_sr.service;

import com.armw.audio_sr.enums.AudioFormatEnum;
import org.springframework.stereotype.Service;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

@Service
public class AudioConverterService {

	public void convertAudioByEnum(final AudioFormatEnum audioFormatEnum, final String sourcePath, final String targetPath) throws EncoderException {
		switch (audioFormatEnum) {
			case WAV:
				convertAudioToWAV(sourcePath, targetPath);
				break;
			case M4A:
				convertAudioToM4A(sourcePath, targetPath);
				break;
		}
	}

	public void convertAudioToWAV(final String sourcePath, final String targetPath) throws EncoderException {
		final AudioAttributes audioAttributes = new AudioAttributes();
		audioAttributes.setCodec("pcm_s16le");
		audioAttributes.setBitRate(1411000);
		audioAttributes.setChannels(2);
		audioAttributes.setSamplingRate(44100);

		final EncodingAttributes encodingAttributes = new EncodingAttributes();
		encodingAttributes.setOutputFormat("wav");
		encodingAttributes.setAudioAttributes(audioAttributes);

		convertAudio(sourcePath, targetPath, encodingAttributes);
	}

	public void convertAudioToM4A(final String sourcePath, final String targetPath) throws EncoderException {
		final AudioAttributes audioAttributes = new AudioAttributes();
		audioAttributes.setCodec("aac");
		audioAttributes.setBitRate(128000);
		audioAttributes.setChannels(2);
		audioAttributes.setSamplingRate(44100);

		final EncodingAttributes encodingAttributes = new EncodingAttributes();
		encodingAttributes.setOutputFormat("mp4");
		encodingAttributes.setAudioAttributes(audioAttributes);

		convertAudio(sourcePath, targetPath, encodingAttributes);
	}

	private void convertAudio(final String sourcePath, final String targetPath, final EncodingAttributes encodingAttributes) throws EncoderException {
		final MultimediaObject sourceFile = new MultimediaObject(new File(sourcePath));
		final File targetFile = new File(targetPath);

		final Encoder encoder = new Encoder();
		encoder.encode(sourceFile, targetFile, encodingAttributes);
	}
}
