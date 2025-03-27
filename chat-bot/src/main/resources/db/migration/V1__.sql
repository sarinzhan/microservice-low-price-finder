CREATE TABLE chat
(
    id      bigserial NOT NULL,
    chat_id BIGINT,
    CONSTRAINT pk_chat PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id              bigserial NOT NULL,
    telegram_id     BIGINT,
    username        VARCHAR(255),
    phone_number    VARCHAR(255),
    name            VARCHAR(255),
    private_chat_id BIGINT,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "user"
    ADD CONSTRAINT FK_USER_ON_PRIVATECHAT FOREIGN KEY (private_chat_id) REFERENCES chat (id);