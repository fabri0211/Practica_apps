package com.example.practicaapps.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Partida {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public Partida() {}

    public String eleccionUsuario;
    public String eleccionMaquina;
    public String resultado;
    public long tiempoPartida;
    public String estrategia;
    public long fecha;

}
