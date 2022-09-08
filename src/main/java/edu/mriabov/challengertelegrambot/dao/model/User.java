package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @Column(name = "telegram_id", nullable = false)
    private long telegramId;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;

    @NotNull
    @Column(name = "coins", nullable = false)
    private Integer coins;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stats_id", nullable = false)
    private UserStats userStats;

    @OneToMany(mappedBy = "createdBy")
    private Set<Challenge> createdChallenges = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Challenge> challenges = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new LinkedHashSet<>();

}
