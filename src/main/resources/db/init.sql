----------------
-- User table --
----------------
CREATE TABLE "user"(
    id          BIGSERIAL       PRIMARY KEY,
    name        TEXT            NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ     NOT NULL DEFAULT now()
);

INSERT INTO "user"(name) VALUES
    ('Alfredo Morgen'),
    ('Alice'),
    ('Bob'),
    ('John Doe'),
    ('Foo'),
    ('Bar');

------------------
-- Phrase table --
------------------
CREATE TABLE phrase(
    id          BIGSERIAL       PRIMARY KEY,
    description TEXT            NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ     NOT NULL DEFAULT now()
);

INSERT INTO phrase(description) VALUES
    ('I like to study in the morning. How about you?'),
    ('Ken, what are you up to today? I''m playing soccer!'),
    ('In Listening Mode, close your eyes for an extra challenge.'),
    ('Try to find some time to speak English every day, Ken.'),
    ('Protty, beam me up');

-----------------------
-- Phrase File table --
-----------------------
CREATE TABLE phrase_file(
    user_id     BIGINT          REFERENCES "user"(id),
    phrase_id   BIGINT          REFERENCES phrase(id),
    file_path   TEXT            NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),

    PRIMARY KEY (user_id, phrase_id)
);
