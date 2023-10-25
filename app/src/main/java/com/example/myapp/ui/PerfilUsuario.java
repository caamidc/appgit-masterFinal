package com.example.myapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PerfilUsuario extends Fragment {

    private ImageView imageViewPerfil;
    private TextView nombreUsuarioTextView;
    private TextView correoUsuarioTextView;
    private Button editarFotoButton;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);

        // Inicializa los elementos de la interfaz
        imageViewPerfil = rootView.findViewById(R.id.imageButtonPerfil);
        nombreUsuarioTextView = rootView.findViewById(R.id.nombreUsuarioTextView);
        correoUsuarioTextView = rootView.findViewById(R.id.correoUsuarioTextView);
        editarFotoButton = rootView.findViewById(R.id.editarFotoButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Obtiene el nombre de usuario y el correo electrónico desde Firebase Authentication
            String nombreUsuario = obtenerNombreDeUsuario(currentUser.getEmail());
            String correoUsuario = currentUser.getEmail();

            // Muestra el nombre de usuario y el correo en la interfaz
            nombreUsuarioTextView.setText(nombreUsuario);
            correoUsuarioTextView.setText(correoUsuario);

            // Configura el botón para editar la foto
            editarFotoButton.setOnClickListener(v -> seleccionarImagen());

            // Inicializa la referencia al Realtime Database
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Perfiles").child(currentUser.getUid());

            // Inicializa la referencia al almacenamiento de Firebase
            storageReference = FirebaseStorage.getInstance().getReference().child("Perfiles").child(currentUser.getUid() + ".jpg");

            // Cargar la foto de perfil del usuario
            cargarFotoDePerfil();
        }

        return rootView;
    }

    private void seleccionarImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), filePath);
                imageViewPerfil.setImageBitmap(bitmap);

                // Sube la imagen a Firebase Storage y actualiza la referencia en el Realtime Database
                subirImagenAFirebase(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void subirImagenAFirebase(Bitmap imageBitmap) {
        if (filePath != null) {
            // Convierte el bitmap a un arreglo de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            // Sube la imagen a Firebase Storage
            UploadTask uploadTask = storageReference.putBytes(data);
            uploadTask.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // La imagen se subió con éxito
                    Toast.makeText(getActivity(), "Imagen subida correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Error al subir la imagen
                    Toast.makeText(getActivity(), "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String obtenerNombreDeUsuario(String correoElectronico) {
        int indexArroba = correoElectronico.indexOf("@");
        if (indexArroba > 0) {
            return correoElectronico.substring(0, indexArroba);
        } else {
            return correoElectronico;
        }
    }

    private void cargarFotoDePerfil() {
        // Obtén la URL de la imagen de Firebase Realtime Database y configura la imagen
        databaseReference.child("imagenPerfil").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imageUrl = dataSnapshot.getValue(String.class);
                if (imageUrl != null) {
                    Picasso.get().load(imageUrl).into(imageViewPerfil);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores aquí
            }
        });
    }
}








