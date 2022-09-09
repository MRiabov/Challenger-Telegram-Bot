DROP SCHEMA IF EXISTS challengerdb;
CREATE SCHEMA IF NOT EXISTS challengerdb;

use ChallengerDB;


CREATE TABLE challenge
(
    id            INT AUTO_INCREMENT NOT NULL,
    `description` VARCHAR(256)       NOT NULL,
    difficulty    VARCHAR(255)       NOT NULL,
    area          VARCHAR(255)       NOT NULL,
    created_at    datetime           NOT NULL,
    expires_at    datetime           NOT NULL,
    created_by    int                NOT NULL,
    group_id      int                NOT NULL,
    CONSTRAINT pk_challenge PRIMARY KEY (id)
);

CREATE TABLE challenge_user
(
    challenge_id INT NOT NULL,
    user_id      INT NOT NULL,
    CONSTRAINT pk_challenge_user PRIMARY KEY (challenge_id, user_id)
);

CREATE TABLE chat_user
(
    group_id INT NOT NULL,
    user_id  INT NOT NULL,
    CONSTRAINT pk_chat_user PRIMARY KEY (group_id, user_id)
);

CREATE TABLE `groups`
(
    id                    INT AUTO_INCREMENT NOT NULL,
    total_tasks_completed INT                NOT NULL,
    telegram_id           BIGINT             NOT NULL UNIQUE,
    group_name            VARCHAR(50)        NOT NULL,
    CONSTRAINT pk_group PRIMARY KEY (id)
);

CREATE TABLE user
(
    id          INT AUTO_INCREMENT NOT NULL,
    first_name  VARCHAR(50)        NOT NULL,
    telegram_id BIGINT             NOT NULL UNIQUE,
    last_name   VARCHAR(50)        NULL,
    username    VARCHAR(50)        NULL,
    coins       INT                NOT NULL,
    stats_id    INT                NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_stats
(
    id            INT AUTO_INCREMENT NOT NULL,
    mindfulness   INT                NOT NULL,
    fitness       INT                NOT NULL,
    relationships INT                NOT NULL,
    finances      INT                NOT NULL,
    CONSTRAINT pk_user_stats PRIMARY KEY (id)
);

ALTER TABLE challenge
    ADD CONSTRAINT FK_CHALLENGE_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES user (id);

ALTER TABLE challenge
    ADD CONSTRAINT FK_CHALLENGE_ON_GROUP FOREIGN KEY (group_id) REFERENCES `groups` (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_STATS FOREIGN KEY (stats_id) REFERENCES user_stats (id);

ALTER TABLE chat_user
    ADD CONSTRAINT fk_chat_user_on_group FOREIGN KEY (group_id) REFERENCES `groups` (id);

ALTER TABLE chat_user
    ADD CONSTRAINT fk_chat_user_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE challenge_user
    ADD CONSTRAINT fk_chause_on_challenge FOREIGN KEY (challenge_id) REFERENCES challenge (id);

ALTER TABLE challenge_user
    ADD CONSTRAINT fk_chause_on_user FOREIGN KEY (user_id) REFERENCES user (id);