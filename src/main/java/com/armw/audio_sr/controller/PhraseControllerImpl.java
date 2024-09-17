package com.armw.audio_sr.controller;

import com.armw.audio_sr.enums.AudioFormatEnum;
import com.armw.audio_sr.service.PhraseFileService;
import com.armw.audio_sr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ws.schild.jave.EncoderException;

import java.io.IOException;

@RestController
public class PhraseControllerImpl implements PhraseController {

	@Autowired private PhraseFileService phraseFileService;
	@Autowired private UserService userService;

	@Override
	public ResponseEntity<Void> postPhrase(@PathVariable final long userId,
	                                       @PathVariable final long phraseId,
	                                       @RequestParam("audio_file") final MultipartFile audioFile) {
		userService.authenticateUser(userId);

		try {
			phraseFileService.storePhraseFile(userId, phraseId, audioFile);
			return ResponseEntity.ok().build();
		} catch (final EncoderException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to process audio in the submitted file. " +
					"Please help to recheck if it's the correct file");
		} catch (final IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an issue in storing the audio file. " +
					"Please wait while we're checking the issue");
		}
	}

	@Override
	public ResponseEntity<Resource> getPhrase(@PathVariable final long userId,
	                                          @PathVariable final long phraseId,
	                                          @PathVariable final String audioFormat) {
		userService.authenticateUser(userId);

		try {
			final AudioFormatEnum audioFormatEnum = AudioFormatEnum.valueOf(audioFormat.toUpperCase());
			final Resource phraseFile = phraseFileService.getPhraseFile(userId, phraseId, audioFormatEnum);

			final HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + phraseFile.getFilename() + "\"");
			headers.add(HttpHeaders.CONTENT_TYPE, audioFormatEnum.getMimeType());

			return ResponseEntity.ok().headers(headers).body(phraseFile);
		} catch (final EncoderException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an issue in processing the audio file. " +
					"Please wait while we're checking the issue");
		}
	}
}
