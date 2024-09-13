package com.armw.audio_sr.accessor;

import com.armw.audio_sr.jooq.tables.PhraseFile;
import com.armw.audio_sr.jooq.tables.records.PhraseFileRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class PhraseFileTableAccessor {
	private final DSLContext dslContext;
	private final PhraseFile phraseFile;

	public PhraseFileTableAccessor(final DSLContext dslContext) {
		this.dslContext = dslContext;
		this.phraseFile = PhraseFile.PHRASE_FILE;
	}

	public void insert(final long userId, final long phraseId, final String filePath) {
		dslContext.insertInto(phraseFile, phraseFile.PHRASE_ID, phraseFile.USER_ID, phraseFile.FILE_PATH)
				.values(phraseId, userId, filePath)
				.onDuplicateKeyUpdate()
				.set(phraseFile.FILE_PATH, filePath)
				.execute();
	}

	public PhraseFileRecord getById(final long userId, final long phraseId) {
		return dslContext.selectFrom(phraseFile)
				.where(phraseFile.USER_ID.eq(userId))
				.and(phraseFile.PHRASE_ID.eq(phraseId))
				.fetchOne();
	}
}
