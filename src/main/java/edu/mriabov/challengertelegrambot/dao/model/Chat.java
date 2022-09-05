package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    private long telegramID;

    @Column(name = "total_tasks_completed")
    private int totalTasks;

    @ManyToMany(mappedBy = "chatList",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<User> users;

    public Chat() {
        users = new ArrayList<>();
    }
}