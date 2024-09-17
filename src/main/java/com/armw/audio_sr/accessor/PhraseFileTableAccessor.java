package com.armw.audio_sr.accessor;

import com.armw.audio_sr.jooq.tables.records.PhraseFileRecord;

public interface PhraseFileTableAccessor {
	void insert(long userId, long phraseId, String filePath);
	PhraseFileRecord getById(long userId, long phraseId);
}
