package edu.mriabov.challengertelegrambot.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "chat")
public class Chat {

    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private int telegramChatID;

    @Column(name = "total_tasks_completed")
    private int totalTasks;

    @ManyToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Set<User> users;

    @OneToMany(mappedBy = "challenge",fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST, targetEntity = Challenge.class)
    private Set<Challenge> challenges;
}