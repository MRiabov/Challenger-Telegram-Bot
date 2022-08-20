CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`    int PRIMARY KEY,
    `first_name` varchar(50) NOT NULL,
    `last_name`  varchar(50),
    `username`   varchar(50),
    `stats_id`   int,
    foreign key (stats_id) references user_stats (stats_id)
);

use ChallengerDB;

CREATE TABLE IF NOT EXISTS `user_stats`
(
    `stats_id`      int AUTO_INCREMENT primary key,
    `Mind`          int,
    `Fitness`       int,
    `Relationships` int
);

CREATE TABLE IF NOT EXISTS `chat`
(
    chat_id               int PRIMARY KEY,
    total_tasks_completed int
);

CREATE TABLE IF NOT EXISTS `challenge`
(
    `challenge_id`      int PRIMARY KEY,
    `challenge_message` varchar(256) NOT NULL,
    `difficulty_id`     int          NOT NULL,
    `area_id`           int          NOT NULL,
    `created_at`        TIMESTAMP    NOT NULL,
    `expires_at`        TIMESTAMP    NOT NULL,
    `created_by`        int          NOT NULL,
    `assigned_to`       int,
    `chat_id`           int          NOT NULL,

    FOREIGN KEY (difficulty_id) references difficulty (difficulty_id),
    FOREIGN KEY (created_by) references user (user_id),
    FOREIGN KEY (assigned_to) references user (user_id),
    FOREIGN KEY (chat_id) references chat (id)
);

CREATE TABLE IF NOT EXISTS areas
(
    area_id   int PRIMARY KEY AUTO_INCREMENT,
    area_name varchar(10)
);

CREATE TABLE IF NOT EXISTS difficulty
(
    difficulty_id   int AUTO_INCREMENT PRIMARY KEY,
    difficulty_name varchar(10)
);
