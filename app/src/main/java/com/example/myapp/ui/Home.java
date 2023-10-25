package com.example.myapp.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapp.R;
import com.example.myapp.ui.EloquentPerfil;
import com.example.myapp.ui.InvasionPerfil;
import com.example.myapp.ui.KarmaPerfil;

public class Home extends Fragment {

    public Home() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Obtén referencias a los botones correspondientes a los perfiles
        Button btnEloquentPerfil = view.findViewById(R.id.buttonEloquentPerfil);
        Button btnInvasionPerfil = view.findViewById(R.id.buttonInvasionPerfil);
        Button btnKarmaPerfil = view.findViewById(R.id.buttonKarmaPerfil);

        // Configura los listeners de clic para los botones
        btnEloquentPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfilEloquent();
            }
        });

        btnInvasionPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfilInvasion();
            }
        });

        btnKarmaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfilKarma();
            }
        });

        return view;
    }

    private void abrirPerfilEloquent() {
        // Iniciar la actividad EloquentPerfil
        Intent intent = new Intent(getActivity(), EloquentPerfil.class);
        startActivity(intent);
    }

    private void abrirPerfilInvasion() {
        // Iniciar la actividad InvasionPerfil
        Intent intent = new Intent(getActivity(), InvasionPerfil.class);
        startActivity(intent);
    }

    private void abrirPerfilKarma() {
        // Iniciar la actividad KarmaPerfil
        Intent intent = new Intent(getActivity(), KarmaPerfil.class);
        startActivity(intent);
    }
}
