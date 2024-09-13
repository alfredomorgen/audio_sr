package com.armw.audio_sr.service;

import com.armw.audio_sr.accessor.PhraseFileTableAccessor;
import com.armw.audio_sr.enums.AudioFormatEnum;
import com.armw.audio_sr.jooq.tables.records.PhraseFileRecord;
import com.armw.audio_sr.jooq.tables.records.PhraseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ws.schild.jave.EncoderException;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class PhraseFileService {

	@Autowired private AudioConverterService audioConverterService;
	@Autowired private PhraseService phraseService;
	@Autowired private StorageService storageService;

	@Autowired private PhraseFileTableAccessor phraseFileTableAccessor;

	public void storePhraseFile(final long userId, final long phraseId, final MultipartFile multipartFile) throws IOException, EncoderException {
		final PhraseRecord phraseRecord = phraseService.getById(phraseId);
		if (phraseRecord == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Phrase ID %d not found", phraseId));
		}

		final String filename = String.format("%s-%s.wav", userId, phraseId);
		final Path sourcePath = storageService.writeTempFileToPath(multipartFile, multipartFile.getOriginalFilename());
		final Path targetPath = storageService.getPathToFile(filename);

		audioConverterService.convertAudioToWAV(sourcePath.toString(), targetPath.toString());
		phraseFileTableAccessor.insert(userId, phraseId, filename);
	}

	public Resource getPhraseFile(final long userId, final long phraseId, final AudioFormatEnum audioFormat) throws EncoderException {
		final PhraseRecord phraseRecord = phraseService.getById(phraseId);
		if (phraseRecord == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Phrase ID %d not found", phraseId));
		}

		final PhraseFileRecord phraseFileRecord = phraseFileTableAccessor.getById(userId, phraseId);
		if (phraseFileRecord == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Phrase file %d not found", phraseId));
		}

		final String fileUrl = phraseFileRecord.getFilePath();
		final Path sourcePath = storageService.getPathToFile(fileUrl);
		final Resource sourceFile = storageService.readFileFromPath(sourcePath);
		if (sourceFile == null || !sourceFile.exists()) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Failed to locate phrase file %d", phraseId));
		}

		final Path targetPath = storageService.getPathToTempFile(fileUrl);
		audioConverterService.convertAudioByEnum(audioFormat, sourcePath.toString(), targetPath.toString());
		return storageService.readFileFromPath(targetPath);
	}
}
