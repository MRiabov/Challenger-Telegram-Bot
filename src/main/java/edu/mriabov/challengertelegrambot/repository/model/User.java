package edu.mriabov.challengertelegrambot.repository.model;

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
    private int userID;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "coins",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userID")},
            inverseJoinColumns = {@JoinColumn(name = "chat_id",referencedColumnName = "chatID")})
    private Set<Chat> chatList;

    private int telegram_id;

    private String first_name;

    private String last_name;

    private String username;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = UserStats.class)
    @JoinColumn(name = "stats_id", referencedColumnName = "statsID")
    private UserStats userStats;

}
