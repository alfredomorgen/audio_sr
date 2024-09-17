package com.armw.audio_sr.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
	Path writeTempFile(final MultipartFile file, final String filename) throws IOException;
	Resource readFile(final Path path);
	boolean deleteFile(final Path path) throws IOException;
	boolean isFileExists(final Path path);

	Path getPathToFile(final String filename);
	Path getPathToTempFile(final String filename);
}
