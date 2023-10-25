package com.example.myapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.myapp.R;
import com.example.myapp.ui.FormulariosSolicitudes;

public class InvasionPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invasion_perfil);

        // Botón flotante para ir al formulario en el perfil de Invasion
        FloatingActionButton botonIrAFormularioInvasion = findViewById(R.id.botonIrAFormulario);

        botonIrAFormularioInvasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea una intención para abrir la actividad FormulariosSolicitudes
                Intent intent = new Intent(getApplicationContext(), FormulariosSolicitudes.class);
                startActivity(intent);
            }
        });
    }
}
