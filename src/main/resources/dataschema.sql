DROP SCHEMA IF EXISTS challengerdb;
CREATE SCHEMA IF NOT EXISTS challengerdb;

use ChallengerDB;

CREATE TABLE IF NOT EXISTS `user_stats`
(
    `id`            int AUTO_INCREMENT primary key,
    `mindfulness`   int NOT NULL,
    `fitness`       int NOT NULL,
    `relationships` int NOT NULL,
    `finances`      int NOT NULL
);

CREATE TABLE IF NOT EXISTS `user`
(
    `id`          int PRIMARY KEY AUTO_INCREMENT,
    `first_name`  varchar(50) NOT NULL,
    `telegram_id` int         NOT NULL,
    `last_name`   varchar(50),
    `username`    varchar(50),
    `coins`       int         NOT NULL,
    `stats_id`    int         NOT NULL,

    foreign key (stats_id) references user_stats (id)
);

CREATE TABLE IF NOT EXISTS `group`
(
    id                    int PRIMARY KEY AUTO_INCREMENT,
    total_tasks_completed int         NOT NULL,
    telegramId            int         NOT NULL,
    `name`                varchar(50) NOT NULL,
    added_at              TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS `challenge`
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    description  varchar(256)                                              NOT NULL,
    `difficulty` enum ('EASY','MEDIUM','DIFFICULT','GOAL')                 NOT NULL,
    `area`       enum ('RELATIONSHIPS','FITNESS','MINDFULNESS','FINANCES') NOT NULL,
    `created_at` TIMESTAMP                                                 NOT NULL,
    `expires_at` TIMESTAMP                                                 NOT NULL,
    `created_by` int                                                       NOT NULL,
    `group_id`   int                                                       NOT NULL,

    FOREIGN KEY (created_by) references user (id),
    FOREIGN KEY (group_id) references `group` (id)
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
    user_id  int NOT NULL,
    group_id int NOT NULL,
    foreign key (user_id) references user (id),
    foreign key (group_id) references `group` (id)
)
