package com.armw.audio_sr.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/audio/user/{userId}/phrase/")
public interface PhraseController {
	@PostMapping("/{phraseId}")
	ResponseEntity<Void> postPhrase(@PathVariable final long userId,
	                                @PathVariable final long phraseId,
	                                @RequestParam("audio_file") final MultipartFile audioFile);

	@GetMapping("/{phraseId}/{audioFormat}")
	ResponseEntity<Resource> getPhrase(@PathVariable final long userId,
	                                   @PathVariable final long phraseId,
	                                   @PathVariable final String audioFormat);
}
