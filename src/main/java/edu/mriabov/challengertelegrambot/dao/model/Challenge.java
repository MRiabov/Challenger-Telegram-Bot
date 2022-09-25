package edu.mriabov.challengertelegrambot.dao.model;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "challenge")
@ToString
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Size(min = 40)
    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "difficulty", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "area", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Area area;

    @Column(name = "recurring_time")
    private LocalTime recurringTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    @ToString.Exclude
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @ToString.Exclude
    private Group group;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "challenge_user",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private Set<User> users = new LinkedHashSet<>();

    @Transient
    private boolean isFree;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
}