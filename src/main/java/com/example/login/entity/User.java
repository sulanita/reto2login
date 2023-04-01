package com.example.login.entity;

import javax.persistence.*;

@Entity
@Table(name = "usuario2")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "intentos_fallidos")
    private int intentosFallidos;

    @Column(name = "bloqueado")
    private int bloqueado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void incrementarIntentosFallidos() {
        this.intentosFallidos++;
    }

    public void resetIntentosFallidos() {
        this.intentosFallidos = 0;
       // this.bloqueado = false;
    }

    public void resetBloqueo() {
        this.bloqueado = 0;
        // this.bloqueado = false;
    }

    public void isBloqueado() {
        this.bloqueado = 1;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }



    public void setIntentosFallidos(int intentos_fallidos) {
        this.intentosFallidos=intentos_fallidos;
    }


}
