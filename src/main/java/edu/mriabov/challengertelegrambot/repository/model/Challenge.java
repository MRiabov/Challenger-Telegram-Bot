package edu.mriabov.challengertelegrambot.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    int challengeID;

    @Column(updatable = false)
    private String area;

    @Column(updatable = false)
    private String difficulty;
}
