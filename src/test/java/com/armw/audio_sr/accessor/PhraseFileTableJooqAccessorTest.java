package com.armw.audio_sr.accessor;

import com.armw.audio_sr.jooq.tables.records.PhraseFileRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/db/init.sql")
@Testcontainers
public class PhraseFileTableJooqAccessorTest {
	private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:16.4-alpine3.20");

	@Container
	@ServiceConnection
	private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(POSTGRES_IMAGE);

	@Autowired
	private PhraseFileTableJooqAccessor phraseFileTableJooqAccessor;

	@Test
	public void testInsertAndGet() {
		final PhraseFileRecord row = new PhraseFileRecord();
		row.setUserId(1L);
		row.setPhraseId(1L);
		row.setFilePath("1-1.wav");

		phraseFileTableJooqAccessor.insert(row.getUserId(), row.getPhraseId(), row.getFilePath());

		final PhraseFileRecord result = phraseFileTableJooqAccessor.getById(row.getUserId(), row.getPhraseId());
		assertEquals(row.getUserId(), result.getUserId());
		assertEquals(row.getPhraseId(), result.getPhraseId());
		assertEquals(row.getFilePath(), result.getFilePath());
	}
}
