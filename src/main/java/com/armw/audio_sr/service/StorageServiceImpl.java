package com.armw.audio_sr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StorageServiceImpl implements StorageService {
	private final Path storagePath;
	private final Path tempStoragePath;

	public StorageServiceImpl(@Value("${app.storage.path}") final String storagePath,
	                          @Value("${app.storage.tempDirPrefix}") final String tempDirPrefix) throws IOException {
		this.storagePath = Path.of(storagePath);
		if (!Files.exists(this.storagePath)) {
			Files.createDirectories(this.storagePath);
		}
		this.tempStoragePath = Files.createTempDirectory(tempDirPrefix);
	}

	@Override
	public Path writeTempFile(final MultipartFile file, final String filename) throws IOException {
		final Path path = tempStoragePath.resolve(filename);
		Files.write(path, file.getBytes());
		return path;
	}

	@Override
	public Resource readFile(final Path path) {
		return new FileSystemResource(path.toFile());
	}

	@Override
	public boolean deleteFile(final Path path) throws IOException {
		return Files.deleteIfExists(path);
	}

	@Override
	public boolean isFileExists(final Path path) {
		return Files.exists(path);
	}

	@Override
	public Path getPathToFile(final String filename) {
		return storagePath.resolve(filename);
	}

	@Override
	public Path getPathToTempFile(final String filename) {
		return tempStoragePath.resolve(filename);
	}
}
