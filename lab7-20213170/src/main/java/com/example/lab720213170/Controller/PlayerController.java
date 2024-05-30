package com.example.lab720213170.Controller;

import com.example.lab720213170.Entity.Player;
import com.example.lab720213170.Repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = {"", "/"})
    public ResponseEntity<HashMap<String, Object>> guardarProducto(
            @RequestBody Player player,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        //Calculamos la posicion
        List<Player> listaPlayersTotal = playerRepository.listaLeaderTotalporRegion(player.getRegion());

        Optional<Integer> optmaxPosicion = playerRepository.obtenerMaxposition(player.getRegion());
        int maxPosition = 1;
        if(optmaxPosicion.isPresent()){
            maxPosition = optmaxPosicion.get();
        }

        for(Player player1 : listaPlayersTotal){
            if(player.getMmr() > player1.getMmr()){
                player.setPosition(player1.getPosition());

                //Actualizamos position de los demas
                int positionPlayer1act = player1.getPosition();
                //Listamos a los de menor e igual mmr de player1
                List<Player> listaPlayer2 = playerRepository.listarPlayermenorMmr(player1.getRegion(), player1.getMmr());
                for(Player player2 : listaPlayer2){
                    player2.setPosition(player.getPosition() + 1);
                    playerRepository.save(player2);
                }


            }else {
                player.setPosition(maxPosition + 1); //Cuando el mmr es menor que todos
            }
        }
        playerRepository.save(player);
        if (fetchId) {
            responseJson.put("idPlayer", player.getPlayerId());
        }
        responseJson.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    //Eliminar Usuario

    @DeleteMapping("")
    public ResponseEntity<HashMap<String, Object>> borrar(@RequestParam("id") String idStr){

        try{
            int id = Integer.parseInt(idStr);

            HashMap<String, Object> respuesta = new HashMap<>();

            Optional<Player> byId = playerRepository.findById(id);
            if(byId.isPresent()){
                playerRepository.deleteById(id);
                respuesta.put("result","ok");
            }else{
                respuesta.put("result","no ok");
                respuesta.put("msg","el ID enviado no existe");
            }

            return ResponseEntity.ok(respuesta);
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(null);
        }
    }













}
