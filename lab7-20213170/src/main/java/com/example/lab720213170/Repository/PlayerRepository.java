package com.example.lab720213170.Repository;

import com.example.lab720213170.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    //Se seleccionan solo los 10 primeros
    @Query(nativeQuery = true, value = "SELECT * FROM players where region = ? order by position limit 0, 10")
    List<Player> listarLeaderBoard(String region);

    @Query(nativeQuery = true, value = "SELECT * FROM players where region = ? order by position")
    List<Player> listaLeaderTotalporRegion(String region);

    @Query(nativeQuery = true, value = "SELECT MAX(position) FROM players where region = ?")
    Optional<Integer> obtenerMaxposition(String region);

    @Query(nativeQuery = true, value = "SELECT * FROM players where region = ?1 and mmr <= ?2 order by position;")
    List<Player> listarPlayermenorMmr(String region,int mmr);


}
