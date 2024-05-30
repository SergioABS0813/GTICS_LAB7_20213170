package com.example.lab720213170.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerId")
    private Integer playerId;

    @Column(name = "name")
    private String name;

    @Column(name = "mmr")
    private int mmr;

    @Column(name = "position")
    private int position;

    @Column(name = "region")
    private String region;












}
