package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "user_stats")
public class UserStats {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "mindfulness", nullable = false)
    private Integer mindfulness;

    @NotNull
    @Column(name = "fitness", nullable = false)
    private Integer fitness;

    @NotNull
    @Column(name = "relationships", nullable = false)
    private Integer relationships;

    @NotNull
    @Column(name = "finances", nullable = false)
    private Integer finances;

}
