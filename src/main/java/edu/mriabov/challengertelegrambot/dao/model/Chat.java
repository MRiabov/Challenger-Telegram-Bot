package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private String name;

    @Column(updatable = false)
    private long telegramID;

    @Column(name = "total_tasks_completed")
    private int totalTasks;

    @ManyToMany(mappedBy = "chatList",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<User> users;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime addedAt;

    public Chat() {
        users = new ArrayList<>();
    }
}