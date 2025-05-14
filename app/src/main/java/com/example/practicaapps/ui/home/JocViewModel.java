package com.example.practicaapps.ui.home;

import androidx.lifecycle.ViewModel;

public class JocViewModel extends ViewModel {

    private int rondaActual = 1;
    private int victoriasUsuario = 0;
    private int victoriasMaquina = 0;

    public String jugarRonda(String usuario, String maquina) {
        String resultado;

        if (usuario.equals(maquina)) {
            resultado = "Empat!";
        } else if (
                (usuario.equals("Pedra") && (maquina.equals("Tisora") || maquina.equals("Llangardaix"))) ||
                        (usuario.equals("Paper") && (maquina.equals("Pedra") || maquina.equals("Spock"))) ||
                        (usuario.equals("Tisora") && (maquina.equals("Paper") || maquina.equals("Llangardaix"))) ||
                        (usuario.equals("Llangardaix") && (maquina.equals("Spock") || maquina.equals("Paper"))) ||
                        (usuario.equals("Spock") && (maquina.equals("Tisora") || maquina.equals("Pedra")))
        ) {
            victoriasUsuario++;
            resultado = "Has guanyat aquesta ronda!";
        } else {
            victoriasMaquina++;
            resultado = "Has perdut aquesta ronda!";
        }

        rondaActual++;
        return resultado + "\nTu: " + usuario + " | Màquina: " + maquina;
    }

    public int getRondaActual() {
        return rondaActual <= 5 ? rondaActual : 5;
    }

    public int getVictoriasUsuario() {
        return victoriasUsuario;
    }

    public int getVictoriasMaquina() {
        return victoriasMaquina;
    }

    public boolean isJuegoTerminado() {
        return rondaActual > 5;
    }
    public void reiniciar() {
        rondaActual = 1;
        victoriasUsuario = 0;
        victoriasMaquina = 0;
    }

}
