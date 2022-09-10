package edu.mriabov.challengertelegrambot.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "user_stats")
public class UserStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "mindfulness", nullable = false)
    private int mindfulness;

    @Column(name = "fitness", nullable = false)
    private int fitness;

    @Column(name = "relationships", nullable = false)
    private int relationships;

    @Column(name = "finances", nullable = false)
    private int finances;
}