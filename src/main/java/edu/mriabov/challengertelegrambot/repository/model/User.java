package edu.mriabov.challengertelegrambot.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "user")
public class User {

    @Id
    @Column
    int userID;

    @ManyToMany
            @JoinTable(name = chat_users
        @JoinColumn(name = )

            )
    Set<User> chatList;



}
