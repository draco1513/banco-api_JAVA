package com.bancoapp.banco_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "movimientos")
@Data
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
    private double saldoInicial;


    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)

    private Cuenta cuenta;


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }



    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }


}
