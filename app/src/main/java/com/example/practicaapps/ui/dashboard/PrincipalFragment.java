package com.example.practicaapps.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.practicaapps.data.Partida;
import com.example.practicaapps.data.PartidaDatabase;
import com.example.practicaapps.databinding.FragmentPrincipalBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrincipalFragment extends Fragment {

    private FragmentPrincipalBinding binding;
    private PartidaAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPrincipalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        adapter = new PartidaAdapter();
        binding.recyclerViewPartidas.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPartidas.setAdapter(adapter);

        // Observar LiveData de Room
        PartidaDatabase
                .obtenerInstancia(requireContext())
                .partidaDao()
                .getTodasPartidas()
                .observe(getViewLifecycleOwner(), partidas -> {
                    adapter.setListaPartidas(partidas);
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
