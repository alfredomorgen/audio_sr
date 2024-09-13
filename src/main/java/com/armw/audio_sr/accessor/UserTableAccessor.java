package com.armw.audio_sr.accessor;

import com.armw.audio_sr.jooq.tables.User;
import com.armw.audio_sr.jooq.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserTableAccessor {
	private final DSLContext dslContext;
	private final User user;

	public UserTableAccessor(final DSLContext dslContext) {
		this.dslContext = dslContext;
		this.user = User.USER;
	}

	public UserRecord getById(final long userId) {
		return dslContext.selectFrom(user)
				.where(user.ID.eq(userId))
				.fetchOne();
	}
}
