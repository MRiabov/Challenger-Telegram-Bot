# Challenger-Telegram-Bot
This project is meant to be a stimulator to work hard, to grind the success. To ultimately be the best version of themselves. Through challenge, pain and risk to the strongest, smartest version of men, who value other's lives just as they do with their goals.
## Brief introduction to Telegram and its bots
In Telegram, bots are *powerful*. Users can interact with them directly in a private chat, or they can use them in a group using commands. This bot has a privacy mode ON, which means, that it can't read users' messages if they are not a command or a reply to bot's previous message.

In a private chat, where most action happens, users interact with bot using such buttons:

![image](https://user-images.githubusercontent.com/108194191/192120732-9d2d0b70-8bd0-45cc-b9e7-22e557268d2a.png)

Buttons on press send a message of according content. This enables to have a nice GUI.
If you'd like to know more about telegram bots and it's enourmous potential, [Telegram has a great explanation for it.](https://core.telegram.org/bots "Telegram has a great explanation for it.")

## Why this project?
Me and my friends are on a path of self-improvement. So, to make things more competitive, we decided to challenge one another with sports, reading, meditation, etc.
And so, we saw that it was difficult to track our challenges. Were they completed? Were they completed in time? Did the person even noticed that they were challenged?

So, as I am an aspiring programmer, i decided that it will be a great resume project, as well as a potential business idea.

# Stack

 - *Spring Boot* - the core of the project.
 - **Spring JPA**:
   - Repositories management (CRUD and others)
    - Hibernate 
    - Derived Query Methods
    - Relationships(1-1, 1-M, M-M)
    - Sorting and paging.
- **MySQL**, later migrated to **PostgresSQL**
- JPA buddy:
  - Flyway database migration 
  - Liquibase Differential Changelogs (primarily database-first approach)
  - DDL and SQL statements
- Lombok 
- Originally hosted on **AWS RDS**, scrapped for faster load times.
- [rubenlagus/TelegramBots](https://github.com/rubenlagus/TelegramBots), the library, on which the whole project is based.
- Docker, *not created by me*
- Guava, google's Java util library. Used for caching.
