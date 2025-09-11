package com.bancoapp.banco_api.dto;

import com.bancoapp.banco_api.model.TipoMovimiento;
import lombok.Builder;

import java.math.BigDecimal;
@Builder

public class MovimientoRequestDTO {
    private String numeroCuenta;
    private TipoMovimiento tipoMovimiento; // "RETIRO" o "DEPOSITO"
    private BigDecimal valor;


    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
