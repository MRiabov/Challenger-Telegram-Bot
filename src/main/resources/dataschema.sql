DROP SCHEMA IF EXISTS challengerdb;
CREATE SCHEMA IF NOT EXISTS challengerdb;

use ChallengerDB;

CREATE TABLE IF NOT EXISTS `user_stats`
(
    `id`            int AUTO_INCREMENT primary key,
    `mindfulness`   int,
    `fitness`       int,
    `relationships` int,
    `finances`      int
);

CREATE TABLE IF NOT EXISTS `user`
(
    `id`          int PRIMARY KEY AUTO_INCREMENT,
    `first_name`  varchar(50) NOT NULL,
    `telegram_id` long,
    `last_name`   varchar(50),
    `username`    varchar(50),
    `coins`       int,
    `stats_id`    int,

    foreign key (stats_id) references user_stats (id)
);

CREATE TABLE IF NOT EXISTS `chat`
(
    id                    int PRIMARY KEY AUTO_INCREMENT,
    total_tasks_completed int,
    telegramId            long,
    name                  varchar(50),
    added_at              TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `challenge`
(
    id                  int PRIMARY KEY AUTO_INCREMENT,
    `challenge_message` varchar(256)                                              NOT NULL,
    `difficulty`        enum ('EASY','MEDIUM','DIFFICULT','GOAL')                 NOT NULL,
    `area`              enum ('RELATIONSHIPS','FITNESS','MINDFULNESS','FINANCES') NOT NULL,
    `created_at`        TIMESTAMP                                                 NOT NULL,
    `expires_at`        TIMESTAMP                                                 NOT NULL,
    `created_by`        int                                                       NOT NULL,
    `chat_id`           int                                                       NOT NULL,

    FOREIGN KEY (created_by) references user (id),
    FOREIGN KEY (chat_id) references chat (id)
);

CREATE TABLE IF NOT EXISTS challenge_user
(
    user_id      int NOT NULL,
    challenge_id int NOT NULL,
    foreign key (user_id) references user (id),
    foreign key (challenge_id) references challenge (id)
);

CREATE TABLE IF NOT EXISTS chat_user
(
    user_id int NOT NULL,
    chat_id int NOT NULL,
    foreign key (user_id) references user (id),
    foreign key (chat_id) references chat (id)
)
