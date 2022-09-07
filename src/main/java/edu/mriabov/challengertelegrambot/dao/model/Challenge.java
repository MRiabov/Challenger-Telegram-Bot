package edu.mriabov.challengertelegrambot.dao.model;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Challenge extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id",insertable = false,updatable = false)
    private Chat chat;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Difficulty difficulty;

    private LocalDateTime expiresAt;

    @ManyToMany(mappedBy = "challenges",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Set<User> users=new HashSet<>();
}
