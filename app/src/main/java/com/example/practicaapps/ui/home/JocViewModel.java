package com.example.practicaapps.ui.home;

import androidx.lifecycle.ViewModel;

import java.util.*;

public class JocViewModel extends ViewModel {

    private int rondaActual = 1;
    private int victoriasUsuario = 0;
    private int victoriasMaquina = 0;

    private final Map<String, Integer> contadorUsuario = new HashMap<>();
    private final String[] opciones = {"Pedra", "Paper", "Tisora", "Llangardaix", "Spock"};

    public JocViewModel() {
        for (String opcion : opciones) {
            contadorUsuario.put(opcion, 0);
        }
    }

    public String jugarRonda(String usuario, String maquina) {
        if (contadorUsuario.containsKey(usuario)) {
            contadorUsuario.put(usuario, contadorUsuario.get(usuario) + 1);
        }

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
        return Math.min(rondaActual, 5);
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
        for (String opcion : opciones) {
            contadorUsuario.put(opcion, 0);
        }
    }

    public String obtenerEleccionIAConHistorial() {
        String opcionMasUsada = Collections.max(contadorUsuario.entrySet(), Map.Entry.comparingByValue()).getKey();

        List<String> opcionesQueGanan = new ArrayList<>();
        switch (opcionMasUsada) {
            case "Pedra":
                opcionesQueGanan = Arrays.asList("Paper", "Spock");
                break;
            case "Paper":
                opcionesQueGanan = Arrays.asList("Tisora", "Llangardaix");
                break;
            case "Tisora":
                opcionesQueGanan = Arrays.asList("Pedra", "Spock");
                break;
            case "Llangardaix":
                opcionesQueGanan = Arrays.asList("Pedra", "Tisora");
                break;
            case "Spock":
                opcionesQueGanan = Arrays.asList("Paper", "Llangardaix");
                break;
        }

        return opcionesQueGanan.get(new Random().nextInt(opcionesQueGanan.size()));
    }
}
