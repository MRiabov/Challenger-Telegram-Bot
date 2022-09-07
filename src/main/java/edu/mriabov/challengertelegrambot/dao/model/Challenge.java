package edu.mriabov.challengertelegrambot.dao.model;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Difficulty difficulty;

    @ManyToMany(mappedBy = "challenges",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Set<User> users=new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id",insertable = false,updatable = false,
            nullable = false, referencedColumnName = "id")
    private Chat chat;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

}
