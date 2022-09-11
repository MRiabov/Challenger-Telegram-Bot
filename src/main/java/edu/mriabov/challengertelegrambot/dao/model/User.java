package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "telegram_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stats_id")
    private UserStats userStats;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "telegram_id", nullable = false)
    private long telegramId;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "coins", nullable = false)
    private int coins;

    @OneToMany(mappedBy = "createdBy")
    private Set<Challenge> challenges = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "chat_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new LinkedHashSet<>();


}