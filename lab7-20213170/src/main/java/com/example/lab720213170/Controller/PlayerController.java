package com.example.lab720213170.Controller;

import com.example.lab720213170.Entity.Player;
import com.example.lab720213170.Repository.PlayerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/player")
public class PlayerController {

    final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    //OBTENER con ResponseEntity que cambia la cabecera en la respuesta
    @GetMapping(value = "/{region}")
    public ResponseEntity<List<Player>> listarLeaderBoard(@PathVariable("region") String region) {

        //Listamos

        List<Player> listarporRegion = playerRepository.listarLeaderBoard(region);

        if (listarporRegion.size() != 0) { //Si la lista se encuentra
            return ResponseEntity.ok(listarporRegion); //200 HTTP
        } else {
            return ResponseEntity.badRequest().body(null); //400 HTTP
        }


    }

    //AGREGAR USUARIO









}
