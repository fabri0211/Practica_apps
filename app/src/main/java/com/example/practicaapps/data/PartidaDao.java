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
}
