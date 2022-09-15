package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "total_tasks_completed", nullable = false)
    private int totalTasksCompleted;

    @Column(name = "telegram_id", nullable = false)
    private long telegramId;

    @OneToMany(mappedBy = "group")
    private Set<Challenge> challenges = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new LinkedHashSet<>();

    @Size(max = 50)
    @NotNull
    @Column(name = "group_name", nullable = false, length = 50)
    private String groupName;

}