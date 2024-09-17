package com.armw.audio_sr.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.Resource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PhraseControllerImplTest {
	private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:16.4-alpine3.20");

	@Value("classpath:/bell_sound.mp3")
	private Resource resource;

	@LocalServerPort
	private int port;

	@Container
	@ServiceConnection
	private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(POSTGRES_IMAGE).withInitScript("db/init.sql");

	@BeforeEach
	void beforeEach() {
		RestAssured.port = port;
	}

	@Test
	public void testPostPhrase_success() throws IOException {
		given()
				.contentType(ContentType.MULTIPART)
				.multiPart("audio_file", resource.getFile())
		.when()
				.post("/audio/user/1/phrase/1")
		.then()
				.statusCode(200);
	}

	@Test
	public void testPostPhrase_failed_noAudioFile() {
		given()
				.contentType(ContentType.MULTIPART)
		.when()
				.post("/audio/user/1/phrase/1")
		.then()
				.statusCode(500);
	}

	@Test
	public void testPostPhrase_failed_invalidUserId() throws IOException {
		given()
				.contentType(ContentType.MULTIPART)
				.multiPart("audio_file", resource.getFile())
		.when()
				.post("/audio/user/1000/phrase/1")
		.then()
				.statusCode(401);
	}

	@Test
	public void testPostPhrase_failed_invalidPhraseId() throws IOException {
		given()
				.contentType(ContentType.MULTIPART)
				.multiPart("audio_file", resource.getFile())
		.when()
				.post("/audio/user/1/phrase/1000")
		.then()
				.statusCode(404);
	}

	@Test
	public void testGetPhrase_success() throws IOException {
		testPostPhrase_success();

		when()
				.get("/audio/user/1/phrase/1/m4a")
		.then()
				.statusCode(200);
	}

	@Test
	public void testGetPhrase_failed_notFound() {
		when()
				.get("/audio/user/1/phrase/1/m4a")
		.then()
				.statusCode(404);
	}
}
