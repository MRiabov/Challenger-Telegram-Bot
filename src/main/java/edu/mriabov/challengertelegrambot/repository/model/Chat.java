package edu.mriabov.challengertelegrambot.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Entity
@Getter
@Setter

public class Chat {

    @Id
    @Column(name = "chat_id")
    int chatID;

    Set<User>
}
