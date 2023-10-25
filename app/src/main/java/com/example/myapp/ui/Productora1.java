package com.example.myapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapp.R;
import com.google.android.material.tabs.TabLayout;
//import android.widget.Toolbar;

public class Productora1 extends AppCompatActivity {

    private Toolbar supportActionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Incorporar el menu dentro de la activity
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); // Recuperar el id de la opción seleccionada
        if (id == R.id.opcion) {
            // Crear un Intent para iniciar la actividad Solicitudes
            Intent intent = new Intent(this, Solicitudes.class);
            startActivity(intent); // Iniciar la actividad Solicitudes
            return true; // Devuelve true para indicar que el evento ha sido manejado
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productora1);
        //Referencia al toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        TabLayout tl = (TabLayout) findViewById(R.id.tablayout);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //codificar cosas a ejecutar cuando le das tap a un tab
                int position = tab.getPosition();
                switch (position){
                    case 0:
                        //llamar al fragmento Home
                        Home h = new Home();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,h).commit();
                        break;
                    case 1:
                        //lamar al fragmento cualquiera
                        PerfilUsuario c = new PerfilUsuario();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,c).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //codificar cosas a ejecutar cuando un tab deja de estar seleccionado
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //codificar cosas a ejecutar cuando un tab se vuelve a seleccionar
            }
        });

    }
}

