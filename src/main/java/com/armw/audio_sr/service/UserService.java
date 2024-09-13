package com.armw.audio_sr.service;

import com.armw.audio_sr.accessor.UserTableAccessor;
import com.armw.audio_sr.jooq.tables.records.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

	@Autowired private UserTableAccessor userTableAccessor;

	/**
	 * Definitely not an ideal authentication implementation, currently just putting this to validate user exists or not
	 * Since it's put on first line of each API request, we can be sure that the userId exists
	 * Ideally, this should also make sure that the user can only access resource under their userId
	 * by comparing the userId in their auth and the userId in their request
	 */
	public void authenticateUser(final long userId) {
		final UserRecord userRecord = userTableAccessor.getById(userId);
		if (userRecord == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}
}
