package com.example.practicaapps.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaapps.R;
import com.example.practicaapps.data.Partida;

import java.util.ArrayList;
import java.util.List;

public class PartidaAdapter extends RecyclerView.Adapter<PartidaAdapter.ViewHolder> {

    private List<Partida> listaPartidas = new ArrayList<>();

    public void setListaPartidas(List<Partida> partidas) {
        this.listaPartidas = partidas;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textElecciones, textResultado, textTiempo;

        public ViewHolder(View itemView) {
            super(itemView);
            textElecciones = itemView.findViewById(R.id.textElecciones);
            textResultado = itemView.findViewById(R.id.textResultado);
            textTiempo = itemView.findViewById(R.id.textTiempo);
        }
    }

    @NonNull
    @Override
    public PartidaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_partida, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Partida partida = listaPartidas.get(position);

        holder.textElecciones.setText("Tu: " + partida.eleccionUsuario +
                " | Màquina: " + partida.eleccionMaquina);

        holder.textResultado.setText("Resultat: " + partida.resultado);

        holder.textTiempo.setText("Temps: " + partida.tiempoPartida + " ms");
    }

    @Override
    public int getItemCount() {
        return listaPartidas.size();
    }
}
