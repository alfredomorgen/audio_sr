package com.armw.audio_sr.service;

import com.armw.audio_sr.accessor.PhraseTableAccessor;
import com.armw.audio_sr.jooq.tables.records.PhraseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhraseService {

	@Autowired private PhraseTableAccessor phraseTableAccessor;

	public PhraseRecord getById(final long phraseId) {
		return phraseTableAccessor.getById(phraseId);
	}
}
