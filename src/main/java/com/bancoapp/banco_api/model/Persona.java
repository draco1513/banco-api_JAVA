package com.bancoapp.banco_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public  abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String genero;
    private int edad;

    @Column(unique = true, nullable = false)
    private String identificacion;

    private String direccion;
    private String telefono;
}
