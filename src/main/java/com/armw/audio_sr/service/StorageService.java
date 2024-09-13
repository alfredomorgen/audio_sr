package com.armw.audio_sr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StorageService {
	private final Path storagePath;
	private final Path tempStoragePath;

	public StorageService(@Value("${app.storage.path}") final String storagePath,
	                      @Value("${app.storage.tempDirPrefix}") final String tempDirPrefix) throws IOException {
		this.storagePath = Path.of(storagePath);
		if (!Files.exists(this.storagePath)) {
			Files.createDirectories(this.storagePath);
		}
		this.tempStoragePath = Files.createTempDirectory(tempDirPrefix);
	}

	public Path writeTempFileToPath(final MultipartFile file, final String filename) throws IOException {
		final Path path = tempStoragePath.resolve(filename);
		Files.write(path, file.getBytes());
		return path;
	}

	public Resource readFileFromPath(final Path path) {
		return new FileSystemResource(path.toFile());
	}

	public Path getPathToFile(final String filename) {
		return storagePath.resolve(filename);
	}

	public Path getPathToTempFile(final String filename) {
		return tempStoragePath.resolve(filename);
	}
}
