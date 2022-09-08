package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "total_tasks_completed")
    private int totalTasksCompleted;

    private long telegramId;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @OneToMany(mappedBy = "group")
    private Set<Challenge> challenges = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new LinkedHashSet<>();

}