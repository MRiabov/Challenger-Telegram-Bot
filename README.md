# Challenger-Telegram-Bot
This project is meant to be a stimulator to work hard, to grind the success. To ultimately be the best version of themselves. Through challenge, pain and risk to the strongest, smartest version of men, who value other's lives just as they do with their goals.
## Brief introduction to Telegram and its bots
In Telegram, bots are *powerful*. Users can interact with them directly in a private chat, or they can use them in a group using commands. This bot has a privacy mode ON, which means, that it can't read users' messages if they are not a command or a reply to bot's previous message.

In a private chat, where most action happens, users interact with bot using such buttons:

![image](https://user-images.githubusercontent.com/108194191/192120732-9d2d0b70-8bd0-45cc-b9e7-22e557268d2a.png)

Buttons on press send a message of according content. This enables to have a nice GUI.
If you are a code reviewer, this stands as Telegrams's ReplyKeyboardMarkup class.
If you'd like to know more about telegram bots and it's enourmous potential, [Telegram has a great explanation for it.](https://core.telegram.org/bots "Telegram has a great explanation for it.")

## Why this project?
Me and my friends are on a path of self-improvement. So, to make things more competitive, we decided to challenge one another with sports, reading, meditation, and relationships. We had a number challenges every day, which were: workout every day, meditation, [gratification journaling](https://blog.mindvalley.com/gratitude-journal/), and committing to code every day. We have been working like that for months.
And so, we saw that it was difficult to track our challenges. Were they completed? Were they completed in time? Did the person even noticed that they were challenged?

So, as an aspiring programmer, I decided that it will be a great resume project, as well as a potential business idea.

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
- Guava, Google's Java util library. Used for caching.

# Images and rundown of bot's functions.
You can, for example, set a goal for yourself.

![image](https://user-images.githubusercontent.com/108194191/192134669-7624c61f-1c9f-416b-a1c4-ad16b18e239d.png)

It will prompt how many weeks until the goal expires.

![image](https://user-images.githubusercontent.com/108194191/192134754-ae81acfa-4dbb-45e6-8d2c-aa1e503bfd75.png)

## Challenge your friends

It will show your chats, you can select from them.

![image](https://user-images.githubusercontent.com/108194191/192134975-f5d0cff3-ad08-46bc-87cc-347a5e321c31.png)

It will show users in your chat.

![image](https://user-images.githubusercontent.com/108194191/192135207-51d9e191-e9ea-4b74-9d99-469212b1c0bc.png)

Choosing a user you would like to challenge will show a difficulty selection page.

![image](https://user-images.githubusercontent.com/108194191/192135499-cbf010a3-0497-4aae-a1e6-75f602678dee.png)

So we select Area the same way, and now we get to the overview of the challenge itself. 

![image](https://user-images.githubusercontent.com/108194191/192135801-a1ffe31f-96aa-4037-8fa2-48621b030643.png)

Let's confirm it! 

![image](https://user-images.githubusercontent.com/108194191/192135826-0f176a76-2e39-4f82-bd3a-8b807364812d.png)

Oops, there wasn't enough coins. Well, time to grind them!
