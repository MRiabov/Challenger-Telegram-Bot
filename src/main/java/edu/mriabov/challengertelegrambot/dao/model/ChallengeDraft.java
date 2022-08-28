package edu.mriabov.challengertelegrambot.dao.model;

import edu.mriabov.challengertelegrambot.dao.enums.Area;
import edu.mriabov.challengertelegrambot.dao.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChallengeDraft {

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = UserStats.class)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;

    @CreatedBy
    private String createdBy;

    private LocalDateTime expiresAt;

    //todo usersList
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

}
