package com.example.myapp.ui;

public class Formulario {
    private String productora;
    private String nombre;
    private String correo;
    private String telefono;
    private String mensaje;
    private String fechaEvento;
    private String presupuesto;
    private String tipoEvento;

    public Formulario() {
        // Constructor vacío requerido para Firebase
    }

    public Formulario(String productora, String nombre, String correo, String telefono, String mensaje, String fechaEvento, String presupuesto, String tipoEvento) {
        this.productora = productora;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.mensaje = mensaje;
        this.fechaEvento = fechaEvento;
        this.presupuesto = presupuesto;
        this.tipoEvento = tipoEvento;
    }

    // Métodos getter y setter para acceder y modificar los atributos

    public String getProductora() {
        return productora;
    }

    public void setProductora(String productora) {
        this.productora = productora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getFechaEvento() {return fechaEvento;}
    public void setFechaEvento(String fechaEvento) {this.fechaEvento = fechaEvento;}

    public String getPresupuesto() {return presupuesto;}

    public void setPresupuesto(String presupuesto) {this.presupuesto = presupuesto;}

    public String getTipoEvento() {return tipoEvento;}

    public void setTipoEvento(String tipoEvento) {this.tipoEvento = tipoEvento;}
}

