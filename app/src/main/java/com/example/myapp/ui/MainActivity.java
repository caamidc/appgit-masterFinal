package com.example.myapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Obtén el nombre del usuario solo si ya ha iniciado sesión
            obtenerNombreUsuario(currentUser.getUid());
        }

        // Llama a la función para asegurar la carpeta de fotos de perfil
        asegurarCarpetaFotosPerfil();
    }

    public void ingresar(View v) {
        EditText campo1 = this.findViewById(R.id.correoinicio);
        String correo = campo1.getText().toString();
        EditText campo2 = this.findViewById(R.id.contrasenia);
        String contrasenia = campo2.getText().toString();

        mAuth.signInWithEmailAndPassword(correo, contrasenia)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        guardarInicioSesionEnRealtimeDatabase(user.getUid(), correo);

                        if (nombreUsuario != null) {
                            // Si el nombre de usuario se obtuvo previamente, guárdalo en la base de datos.
                            databaseReference.child("usuarios").child(user.getUid()).child("nombre").setValue(nombreUsuario);
                        }

                        // Solo si el inicio de sesión es exitoso, redirige al usuario a la actividad 'Productora1'.
                        Intent i = new Intent(this, Productora1.class);
                        startActivity(i);
                    } else {
                        // Error en las credenciales o la autenticación falló.
                        Toast.makeText(this, "Error en las credenciales", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void registro(View v) {
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

    private void guardarInicioSesionEnRealtimeDatabase(String userId, String correo) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = databaseRef.child("inicio_sesion").child(userId);
        userRef.child("correo").setValue(correo);
    }

    private void obtenerNombreUsuario(String userId) {
        DatabaseReference userRef = databaseReference.child("usuarios").child(userId);
        userRef.child("nombre").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    nombreUsuario = dataSnapshot.getValue(String.class);

                    // Una vez que se ha obtenido el nombre del usuario, procede al inicio de sesión
                    iniciarSesion();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    private void iniciarSesion() {
        // Aquí puedes redirigir al usuario o realizar otras acciones después de cargar los datos necesarios.
        // Por ejemplo, puedes redirigirlo a la actividad Productora1.
        Intent i = new Intent(this, Productora1.class);
        startActivity(i);
    }

    private void asegurarCarpetaFotosPerfil() {
        String uid = mAuth.getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fotosPerfilRef = storageReference.child("FotosPerfil/" + uid + "/perfil.jpg");

        // Verificar si la carpeta de FotosPerfil ya existe
        fotosPerfilRef.getParent().listAll()
                .addOnSuccessListener(listResult -> {
                    // La carpeta FotosPerfil ya existe
                    // No necesitas hacer nada adicional aquí
                })
                .addOnFailureListener(e -> {
                    // La carpeta FotosPerfil no existe, así que debes crearla
                    StorageReference fotosPerfilFolderRef = storageReference.child("FotosPerfil/" + uid + "/");
                    fotosPerfilFolderRef.putBytes(new byte[0]) // Carga un archivo vacío para crear la carpeta
                            .addOnSuccessListener(taskSnapshot -> {
                                // La carpeta FotosPerfil se creó exitosamente
                                // Puedes cargar la foto de perfil aquí
                            })
                            .addOnFailureListener(exception -> {
                                // Manejar errores en caso de que la creación de la carpeta falle
                            });
                });
    }
}
