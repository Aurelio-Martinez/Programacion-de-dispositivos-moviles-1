package com.example.appcall;

import androidx.annotation.NonNull;

public class Contacto {

    public String url;
    public String nombre;
    private String numero;


    @NonNull
    @Override
    public String toString() {
        return "Contacto{" +
                "url=" + url +
                ", nombre='" + nombre + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }


    public Contacto(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
        this.url="";
    }
    public Contacto(String nombre, String numero, String url) {
        this.nombre = nombre;
        this.numero = numero;
        this.url = url;
    }
    public Contacto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


}
