package com.example.practicaapps.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Partida.class}, version = 2)
public abstract class PartidaDatabase extends RoomDatabase {

    private static PartidaDatabase instancia;

    public abstract PartidaDao partidaDao();

    public static synchronized PartidaDatabase obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PartidaDatabase.class,
                    "partida_db"
            ).fallbackToDestructiveMigration().build();
        }
        return instancia;
    }
}
