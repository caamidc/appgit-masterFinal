package com.example.myapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText nombre2;
    private EditText correo;
    private EditText contrasenia2;
    private EditText contrasenia3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        nombre2 = findViewById(R.id.nombre2);
        correo = findViewById(R.id.correo);
        contrasenia2 = findViewById(R.id.contrasenia2);
        contrasenia3 = findViewById(R.id.contrasenia3);
    }

    public void onStart() {
        super.onStart();
    }

    public void registrarUsuario(View view) {
        final String email = correo.getText().toString();
        final String password = contrasenia2.getText().toString();
        final String nombreUsuario = nombre2.getText().toString();

        if (password.equals(contrasenia3.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            guardarRegistroEnRealtimeDatabase(user.getUid(), nombreUsuario, email); // Guardar nombre y correo
                            Toast.makeText(getApplicationContext(), "Usuario Creado", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarRegistroEnRealtimeDatabase(String userId, String nombreUsuario, String correo) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = databaseRef.child("registro").child(userId);
        userRef.child("nombre").setValue(nombreUsuario); // Guardar el nombre
        userRef.child("correo").setValue(correo); // Guardar el correo
    }}