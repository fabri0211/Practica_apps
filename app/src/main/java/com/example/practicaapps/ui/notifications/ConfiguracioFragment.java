package com.example.practicaapps.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.practicaapps.R;

public class ConfiguracioFragment extends Fragment {

    private static final String PREFS_NAME = "GamePrefs";
    private static final String KEY_STRATEGY = "machine_strategy";

    private RadioGroup radioGroup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_configuracio, container, false);

        radioGroup = root.findViewById(R.id.radioGroupStrategy);

        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String strategy = prefs.getString(KEY_STRATEGY, "aleatoria");

        if ("aleatoria".equals(strategy)) {
            ((RadioButton) root.findViewById(R.id.radioAleatoria)).setChecked(true);
        } else {
            ((RadioButton) root.findViewById(R.id.radioExploracio)).setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = prefs.edit();
            if (checkedId == R.id.radioAleatoria) {
                editor.putString(KEY_STRATEGY, "aleatoria");
            } else if (checkedId == R.id.radioExploracio) {
                editor.putString(KEY_STRATEGY, "exploracio");
            }
            editor.apply();
        });

        return root;
    }
}
