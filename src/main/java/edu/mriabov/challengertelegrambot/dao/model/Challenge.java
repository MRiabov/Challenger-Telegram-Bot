package edu.mriabov.challengertelegrambot.dao.model;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    int id;

    String description;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Difficulty difficulty;

    @CreatedDate
    private LocalDateTime createdByt;

    @CreatedBy
    private String createdBy;

    private LocalDateTime expiresAt;
    //todo usersList

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;
}