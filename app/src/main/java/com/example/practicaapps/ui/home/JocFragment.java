package com.example.practicaapps.ui.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicaapps.databinding.FragmentJocBinding;

import java.util.Random;

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

        String eleccionFinal = eleccionUsuario.isEmpty()
                ? opciones[new Random().nextInt(opciones.length)]
                : eleccionUsuario;
        String eleccionMaquina = opciones[new Random().nextInt(opciones.length)];
        String resultado = viewModel.jugarRonda(eleccionFinal,eleccionMaquina);

        binding.textResultado.setText(resultado);
        actualizarUI();

        if (viewModel.isJuegoTerminado()) {
            mostrarDialogFinal();
        }
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
