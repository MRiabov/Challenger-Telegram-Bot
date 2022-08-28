CREATE SCHEMA IF NOT EXISTS challengerdb;

use ChallengerDB;

CREATE TABLE IF NOT EXISTS `user`
(
    id           int PRIMARY KEY,
    `first_name` varchar(50) NOT NULL,
    `last_name`  varchar(50),
    `username`   varchar(50),
    `stats_id`   int,
    foreign key (stats_id) references user_stats (id)
);



CREATE TABLE IF NOT EXISTS `user_stats`
(
    id              int AUTO_INCREMENT primary key,
    `Mind`          int,
    `Fitness`       int,
    `Relationships` int
);

CREATE TABLE IF NOT EXISTS `chat`
(
    id                    int PRIMARY KEY,
    total_tasks_completed int
);

CREATE TABLE IF NOT EXISTS `challenge`
(
    id                  int PRIMARY KEY,
    `challenge_message` varchar(256)                                              NOT NULL,
    `difficulty`        enum ('EASY','MEDIUM','DIFFICULT','GOAL')                 NOT NULL,
    `area`              enum ('RELATIONSHIPS','FITNESS','MINDFULNESS','FINANCES') NOT NULL,
    `created_at`        TIMESTAMP                                                 NOT NULL,
    `expires_at`        TIMESTAMP                                                 NOT NULL,
    `created_by`        int                                                       NOT NULL,
    `assigned_to`       int,
    `chat_id`           int                                                       NOT NULL,

    FOREIGN KEY (created_by) references user (id),
    FOREIGN KEY (assigned_to) references user (id),
    FOREIGN KEY (chat_id) references chat (id)
);

CREATE TABLE IF NOT EXISTS `challenge_draft`
(
    `challenge_id`      int PRIMARY KEY,
    `challenge_message` varchar(256),
    `difficulty`        enum ('EASY','MEDIUM','DIFFICULT','GOAL'),
    `area`              enum ('RELATIONSHIPS','FITNESS','MINDFULNESS','FINANCES'),
    `created_at`        TIMESTAMP,
    `expires_at`        TIMESTAMP,
    `created_by`        int,
    `assigned_to`       int,
    `chat_id`           int,

    FOREIGN KEY (created_by) references user (id),
    FOREIGN KEY (assigned_to) references user (id),
    FOREIGN KEY (chat_id) references chat (id)
);
