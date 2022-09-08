package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "chat")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private String name;

    @Column(updatable = false)
    private long telegramID;

    @Column(name = "total_tasks_completed")
    private int totalTasks;

    @ManyToMany(mappedBy = "groups",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<User> users;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime addedAt;

    @OneToMany(mappedBy = "group")
    private Set<Challenge> challenges = new LinkedHashSet<>();

    public Group() {
        users = new ArrayList<>();
    }
}