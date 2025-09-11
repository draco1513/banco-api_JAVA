package com.bancoapp.banco_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "cliente")
@Data
@PrimaryKeyJoinColumn(name = "cliente_id")
public class Cliente extends Persona {

    @Column(nullable = false)
    private String contrasena;

    private boolean estado;
}
