package com.armw.audio_sr.accessor;

import com.armw.audio_sr.jooq.tables.Phrase;
import com.armw.audio_sr.jooq.tables.records.PhraseRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class PhraseTableAccessor {
	private final DSLContext dslContext;
	private final Phrase phrase;

	public PhraseTableAccessor(final DSLContext dslContext) {
		this.dslContext = dslContext;
		this.phrase = Phrase.PHRASE;
	}

	public PhraseRecord getById(final long phraseId) {
		return dslContext.selectFrom(phrase)
				.where(phrase.ID.eq(phraseId))
				.fetchOne();
	}
}
