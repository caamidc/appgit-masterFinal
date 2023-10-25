package com.example.myapp.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Solicitudes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SolicitudAdapter solicitudAdapter;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa la base de datos de Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("formularios");
        firebaseAuth = FirebaseAuth.getInstance();

        // Configura el adaptador del RecyclerView
        solicitudAdapter = new SolicitudAdapter();
        recyclerView.setAdapter(solicitudAdapter);

        // Obtiene el usuario actual
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userIdentifier = user.getUid(); // Utiliza el UID del usuario como identificador único

            // Escucha los cambios en la base de datos para el usuario actual
            databaseReference.child(userIdentifier).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    solicitudAdapter.clear(); // Limpia la lista de solicitudes
                    for (DataSnapshot solicitudSnapshot : dataSnapshot.getChildren()) {
                        Formulario formulario = solicitudSnapshot.getValue(Formulario.class);
                        solicitudAdapter.addSolicitud(formulario); // Agrega el formulario al adaptador
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejo de errores
                }
            });
        }
    }
}
