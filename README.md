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

## Completing challenges

![image](https://user-images.githubusercontent.com/108194191/192139252-9764b311-5447-4f64-99ec-1c8a6b640e3a.png)

This whole thing that you see: the numpad, it is created using DynamicButtonsService and handled by NumpadHandler class. Well, if you ever get lost, where they are created.

## Set a goal

You can set a goal, just like you would create a normal challenge

![image](https://user-images.githubusercontent.com/108194191/192134669-7624c61f-1c9f-416b-a1c4-ad16b18e239d.png)

It will prompt how many weeks until the goal expires.

![image](https://user-images.githubusercontent.com/108194191/192134754-ae81acfa-4dbb-45e6-8d2c-aa1e503bfd75.png)

And, look! As it is a goal, it was listed on the homepage!

![image](https://user-images.githubusercontent.com/108194191/192139563-51f84f4f-c08f-4036-8b0b-b6c94bbec6ff.png)

# Creating challenges in the chat
## Custom challenges

You can also create challenges by utilizing the power of commands. It's much faster, actually, once you get a hang of it.

![image](https://user-images.githubusercontent.com/108194191/192139806-4a20777a-6982-4f55-83e0-61a6f1b18a35.png)

To prevent errors, I've implemented a /confirm command, so noone has accidents. On /confirm, the challenge is saved and coins are billed.

![image](https://user-images.githubusercontent.com/108194191/192139992-d6906e04-a0be-44fd-9752-3090e09525fd.png)

## Global challenges

Same as custom, but only admins have an access to this type of command, because it sets the challenge for the entire group. For free.

![image](https://user-images.githubusercontent.com/108194191/192140124-05c48483-083e-4b38-ba31-cce08f8a5a91.png)

As you see, the there are all the *registered* users in a chat.

## Daily challenges

Same as Global, but with a cool new feature: it is recurring!

![image](https://user-images.githubusercontent.com/108194191/192141556-597ad1b2-f554-4731-a915-2731c9c17a60.png)

You might notice "⏰Recurring time: 14:00". Now, at 14:00 this challenge will be recurring. Cool, right?

#### There are still LOT of features in the bot, like skipping a challenge (for coins, that is), buying rest, for a specified number of time, which I need, but it showcasing them would take a looong time. The only thing you need is my code, after all ;)

** *Thank you for looking through this readme* ** 

![image](https://mir-s3-cdn-cf.behance.net/project_modules/disp/ce7dc693658133.5e98adbf743e2.gif)
