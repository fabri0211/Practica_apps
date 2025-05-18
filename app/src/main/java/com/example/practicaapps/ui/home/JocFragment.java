package com.example.practicaapps.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicaapps.databinding.FragmentJocBinding;

import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import com.example.practicaapps.data.Partida;
import com.example.practicaapps.data.PartidaDatabase;

public class JocFragment extends Fragment {

    private FragmentJocBinding binding;
    private JocViewModel viewModel;

    private CountDownTimer timer;
    private static final long TIEMPO_RONDA_MS = 5000;

    private final String[] opciones = {"Pedra", "Paper", "Tisora", "Llangardaix", "Spock"};

    private int rondaActual = 1;
    private int victoriasUsuario = 0;
    private int victoriasMaquina = 0;
    private boolean juegoTerminado = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(JocViewModel.class);
        binding = FragmentJocBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnPedra.setOnClickListener(v -> jugar("Pedra"));
        binding.btnPaper.setOnClickListener(v -> jugar("Paper"));
        binding.btnTisora.setOnClickListener(v -> jugar("Tisora"));
        binding.btnLlangardaix.setOnClickListener(v -> jugar("Llangardaix"));
        binding.btnSpock.setOnClickListener(v -> jugar("Spock"));

        actualizarUI();
        iniciarTemporizador();
        return root;
    }

    private void jugar(String eleccionUsuario) {
        if (timer != null) timer.cancel();

        if (viewModel.isJuegoTerminado()) return;

        long tiempoInicio = System.currentTimeMillis(); // inicio de la ronda

        String eleccionFinal = eleccionUsuario.isEmpty()
                ? opciones[new Random().nextInt(opciones.length)]
                : eleccionUsuario;

        String estrategia = requireContext()
                .getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
                .getString("machine_strategy", "aleatoria");

        String eleccionMaquina;

        if ("exploracio".equals(estrategia)) {
            eleccionMaquina = viewModel.obtenerEleccionIAConHistorial();
        } else {
            eleccionMaquina = opciones[new Random().nextInt(opciones.length)];
        }

        String resultado = viewModel.jugarRonda(eleccionFinal, eleccionMaquina);

        long tiempoFin = System.currentTimeMillis(); // fin de la ronda

        Partida nuevaPartida = new Partida();
        nuevaPartida.eleccionUsuario = eleccionFinal;
        nuevaPartida.eleccionMaquina = eleccionMaquina;
        nuevaPartida.resultado = resultado;
        nuevaPartida.tiempoPartida = tiempoFin - tiempoInicio;
        nuevaPartida.estrategia = estrategia;
        nuevaPartida.fecha = System.currentTimeMillis();

        PartidaDatabase db = PartidaDatabase.obtenerInstancia(requireContext());
        new Thread(() -> db.partidaDao().insertarPartida(nuevaPartida)).start();

        binding.textResultado.setText(resultado);
        actualizarUI();

        if (viewModel.isJuegoTerminado()) {
            mostrarDialogFinal();
        }
        Log.d("IA", "Estrategia seleccionada: " + estrategia);
        Log.d("IA", "Elecció de la màquina: " + eleccionMaquina);

    }

    private void actualizarUI() {
        binding.textRonda.setText("Ronda: " + viewModel.getRondaActual() + " / 5");
        binding.textVictorias.setText("Tu: " + viewModel.getVictoriasUsuario() + " - Màquina: " + viewModel.getVictoriasMaquina());
    }

    private void iniciarTemporizador(){
        binding.textTiempo.setText("Temps: 5 segons");

        timer = new CountDownTimer(TIEMPO_RONDA_MS, 1000){
            public void onTick(long millisUntilFinished){
                binding.textTiempo.setText("Temps: " + millisUntilFinished/1000 + "segons");
            }
            public void onFinish(){
                if (!viewModel.isJuegoTerminado()){
                    jugar("");
                }
            }
        }.start();
    }

    private void mostrarDialogFinal() {
        String mensajeFinal;

        int victoriasUsuario = viewModel.getVictoriasUsuario();
        int victoriasMaquina = viewModel.getVictoriasMaquina();

        if (victoriasUsuario > victoriasMaquina) {
            mensajeFinal = "Has guanyat la partida! 🎉";
        } else if (victoriasUsuario < victoriasMaquina) {
            mensajeFinal = "Has perdut la partida. 😢";
        } else {
            mensajeFinal = "Empat! 🤝";
        }

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Resultat final")
                .setMessage(mensajeFinal)
                .setCancelable(false)
                .setPositiveButton("Tornar a jugar", (dialog, which) -> reiniciarPartida())
                .setNegativeButton("Sortir", (dialog, which) -> requireActivity().finish())
                .show();
    }



    private void reiniciarPartida() {
        viewModel.reiniciar(); // método que reinicia rondas y puntuaciones
        actualizarUI();
        binding.textResultado.setText("Esperant elecció...");
        iniciarTemporizador();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) timer.cancel();
        binding = null;
    }
}
