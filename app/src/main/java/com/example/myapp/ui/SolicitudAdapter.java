package com.example.myapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.SolicitudViewHolder> {
    private List<Formulario> solicitudes;

    public SolicitudAdapter() {
        this.solicitudes = new ArrayList<>();
    }

    @NonNull
    @Override
    public SolicitudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new SolicitudViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudViewHolder holder, int position) {
        Formulario formulario = solicitudes.get(position);

        // Establece los valores en los elementos de la vista
        holder.productoraTextView.setText("Productora: " + formulario.getProductora());
        holder.nombreTextView.setText("Nombre: " + formulario.getNombre());
        holder.correoTextView.setText("Correo: " + formulario.getCorreo());
        holder.telefonoTextView.setText("Teléfono: " + formulario.getTelefono());
        holder.mensajeTextView.setText("Mensaje: " + formulario.getMensaje());
        holder.fechaEventoTextView.setText("Fecha del Evento: " + formulario.getFechaEvento());
        holder.presupuestoTextView.setText("Presupuesto: " + formulario.getPresupuesto());
        holder.tipoEventoTextView.setText("Tipo de Evento: " + formulario.getTipoEvento());
    }

    @Override
    public int getItemCount() {
        return solicitudes.size();
    }

    public void addSolicitud(Formulario formulario) {
        solicitudes.add(formulario);
        notifyItemInserted(solicitudes.size() - 1);
    }

    public void clear() {
        solicitudes.clear();
        notifyDataSetChanged();
    }

    static class SolicitudViewHolder extends RecyclerView.ViewHolder {
        TextView productoraTextView;
        TextView nombreTextView;
        TextView correoTextView;
        TextView telefonoTextView;
        TextView mensajeTextView;
        TextView fechaEventoTextView;
        TextView presupuestoTextView;
        TextView tipoEventoTextView;

        public SolicitudViewHolder(View itemView) {
            super(itemView);
            productoraTextView = itemView.findViewById(R.id.productoraTextView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            correoTextView = itemView.findViewById(R.id.correoTextView);
            telefonoTextView = itemView.findViewById(R.id.telefonoTextView);
            mensajeTextView = itemView.findViewById(R.id.mensajeTextView);
            fechaEventoTextView = itemView.findViewById(R.id.fechaEventoTextView);
            presupuestoTextView = itemView.findViewById(R.id.presupuestoTextView);
            tipoEventoTextView = itemView.findViewById(R.id.tipoEventoTextView);
        }
    }
}

