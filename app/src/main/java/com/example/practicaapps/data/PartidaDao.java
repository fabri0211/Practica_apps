package com.example.practicaapps.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PartidaDao {

    @Insert
    void insertarPartida(Partida partida);

    @Query("SELECT * FROM Partida ORDER BY id DESC")
    LiveData<List<Partida>> getTodasPartidas();

    @Query("SELECT * FROM Partida ORDER BY fecha DESC")
    List<Partida> getPartidasSinLiveData(); // sin LiveData para usar directamente

    @Query("SELECT eleccionUsuario, COUNT(*) AS total FROM Partida GROUP BY eleccionUsuario ORDER BY total DESC")
    List<FrecuenciaEleccion> obtenerFrecuenciaElecciones();

}
