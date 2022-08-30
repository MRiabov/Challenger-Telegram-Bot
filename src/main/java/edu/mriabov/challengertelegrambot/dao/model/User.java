package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private int coins;

    private long telegramId;

    private String firstName;

    private String lastName;

    private String username;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "chat_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id")})
    private Set<Chat> chatList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "challenge_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "challenge_id", referencedColumnName = "id")})
    private Set<Challenge> challenges;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UserStats.class)
    @JoinColumn(name = "stats_id", referencedColumnName = "id")
    private UserStats userStats;

}
